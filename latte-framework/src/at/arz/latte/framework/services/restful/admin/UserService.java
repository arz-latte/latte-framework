package at.arz.latte.framework.services.restful.admin;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.admin.restapi.GroupAdministration;
import at.arz.latte.framework.admin.restapi.UserAdministration;
import at.arz.latte.framework.restapi.GroupData;
import at.arz.latte.framework.restapi.UserData;

/**
 * RESTful service for user management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
@Path("users")
public class UserService {

	@Inject
	private UserAdministration userAdministration;

	@Inject
	private GroupAdministration groupAdministration;

	@GET
	@Path("all.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserData> getAllUsers() {
		return userAdministration.allUsers();
	}

	@GET
	@Path("groups.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<GroupData> getAllGroups() {
		return groupAdministration.allGroups();
	}

	@GET
	@Path("get.json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData getUser(@PathParam("id") Long id) {
		return userAdministration.userWithId(id);
	}

	@POST
	@Path("create.json")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData createUser(UserData userData) {
		return userAdministration.createUser(userData);
	}

	@PUT
	@Path("update.json")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData updateUser(UserData userData) {
		return userAdministration.updateUser(userData);
	}

	@DELETE
	@Path("delete.json/{id}")
	public void deleteUser(@PathParam("id") Long userId) {
		userAdministration.deleteUser(userId);
	}

}