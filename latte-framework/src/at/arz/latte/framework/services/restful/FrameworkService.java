package at.arz.latte.framework.services.restful;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import at.arz.latte.framework.persistence.beans.FrameworkManagementBean;
import at.arz.latte.framework.persistence.beans.PermissionManagementBean;
import at.arz.latte.framework.persistence.models.User;
import at.arz.latte.framework.restful.dta.ModuleData;
import at.arz.latte.framework.restful.dta.UserData;
import at.arz.latte.framework.websockets.WebsocketEndpoint;
import at.arz.latte.framework.websockets.models.WebsocketMessage;

/**
 * RESTful service for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Path("framework")
public class FrameworkService {

	@EJB
	private FrameworkManagementBean frameworkBean;

	@EJB
	private PermissionManagementBean permissionBean;

	@EJB
	private WebsocketEndpoint websocket;

	@Context
	private HttpServletRequest request;

	@Context
	protected SecurityContext sc;

	/**
	 * get informations of currently logged in user
	 * 
	 * @return
	 */
	@GET
	@Path("user.json")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData getUserData() {
		User user = initSessionData();
		return new UserData(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
	}

	/**
	 * get initialization data of all modules
	 * 
	 * @return
	 */
	@GET
	@Path("init.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleData> getInitData() {
		initSessionData();
		List<String> permissions = (List<String>) request.getSession().getAttribute("permissions");
		return frameworkBean.getAll(permissions);
	}

	/**
	 * get user data and store them in a session for later reuse
	 * 
	 * @return
	 */
	private User initSessionData() {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			
			user = permissionBean.getUser(sc.getUserPrincipal().getName());
			session.setAttribute("user", user);

			List<String> permissions = permissionBean.getUserPermissions(sc.getUserPrincipal().getName());
			session.setAttribute("permissions", permissions);
		}

		return user;
	}

	/**
	 * notify all clients about something via websocket
	 * 
	 * @return
	 */
	@POST
	@Path("notify.json")
	public void notifyClients(String moduleId) {
		websocket.chat(new WebsocketMessage("notify", moduleId));
	}

}