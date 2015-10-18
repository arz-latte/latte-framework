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
import at.arz.latte.framework.admin.restapi.PermissionAdministration;
import at.arz.latte.framework.restapi.GroupData;
import at.arz.latte.framework.restapi.PermissionData;

/**
 * RESTful service for group management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
@Path("groups")
public class GroupService {

	@Inject
	private GroupAdministration groupAdministration;

	@Inject
	private PermissionAdministration permissionAdministration;

	@GET
	@Path("all.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<GroupData> getAllGroups() {
		return groupAdministration.allGroups();
	}

	@GET
	@Path("permissions.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PermissionData> getAllPermissions() {
		return permissionAdministration.allPermissions();
	}

	@GET
	@Path("get.json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData getRole(@PathParam("id") Long id) {
		return groupAdministration.getGroupById(id);
	}

	@POST
	@Path("create.json")
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData createRole(GroupData groupData) {
		return groupAdministration.createNewGroup(groupData);
	}

	@PUT
	@Path("update.json")
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData updateRole(GroupData groupData) {
		return groupAdministration.updateGroup(groupData);
	}

	@DELETE
	@Path("delete.json/{id}")
	public void deleteGroup(@PathParam("id") Long groupId) {
		groupAdministration.deleteGroup(groupId);
	}
}