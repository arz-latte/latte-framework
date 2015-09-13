package at.arz.latte.framework.services.restful;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import at.arz.latte.framework.persistence.beans.FrameworkManagementBean;
import at.arz.latte.framework.persistence.beans.UserManagementBean;
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
@RequestScoped
@Path("framework")
public class FrameworkService {

	@EJB
	private FrameworkManagementBean frameworkBean;

	@EJB
	private UserManagementBean userBean;
	
	@EJB
	private WebsocketEndpoint websocket;

	@Context
	protected SecurityContext sc;

	/**
	 * get informations from currently logged in user
	 * @return
	 */
	@GET
	@Path("user.json")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData getUserData() {
		User user = userBean.getUser(sc.getUserPrincipal().getName());
		return new UserData(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
	}
	
	/**
	 * get initialization data of all modules
	 * @return
	 */
	@GET
	@Path("init.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleData> getInitData() {
		String email = sc.getUserPrincipal().getName();
		return frameworkBean.getAll(email);
	}

	/**
	 * notify all clients about something via websocket
	 * @return
	 */
	@POST
	@Path("notify.json")
	public void notifyClients(String moduleId) {
		websocket.chat(new WebsocketMessage("notify", moduleId));
	}

}