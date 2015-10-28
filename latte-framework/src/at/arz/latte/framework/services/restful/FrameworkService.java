package at.arz.latte.framework.services.restful;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.FrameworkConstants;
import at.arz.latte.framework.admin.AdminQuery;
import at.arz.latte.framework.admin.restapi.AdminMapper;
import at.arz.latte.framework.authorization.LatteAuthorization;
import at.arz.latte.framework.exceptions.UserNotFound;
import at.arz.latte.framework.module.services.FrameworkManagementBean;
import at.arz.latte.framework.restapi.ModuleData;
import at.arz.latte.framework.restapi.UserData;
import at.arz.latte.framework.util.Functions;
import at.arz.latte.framework.websockets.WebsocketEndpoint;
import at.arz.latte.framework.websockets.WebsocketMessage;

/**
 * RESTful service for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Path("framework")
public class FrameworkService {

	@Inject
	private LatteAuthorization authorization;

	@PersistenceContext(unitName = FrameworkConstants.JPA_UNIT)
	private EntityManager em;

	@EJB
	private FrameworkManagementBean frameworkBean;

	// @EJB
	// private UserRepository userBean;

	@EJB
	private WebsocketEndpoint websocket;

	@Context
	private HttpServletRequest request;

	/**
	 * get informations of currently logged in user
	 * 
	 * @return
	 */
	@GET
	@Path("user.json")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData getUserData() {
		String userName = authorization.getPrincipal().getName();
		List<UserData> list = Functions.map(	AdminMapper.MAP_TO_USERDATA,
													new AdminQuery(em).userByEmail(userName));
		if (list.isEmpty()) {
			throw new UserNotFound(userName);
		}
		return list.get(0);
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
		return frameworkBean.getAll(authorization.getPermissions());
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