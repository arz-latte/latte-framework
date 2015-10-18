package at.arz.latte.framework.admin.restapi;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.FrameworkConstants;
import at.arz.latte.framework.admin.Group;
import at.arz.latte.framework.exceptions.LatteValidationException;
import at.arz.latte.framework.restapi.GroupData;

/**
 * RESTful service for group management
 * 
 * @author Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 * @author mrodler
 *
 */
@Stateless
@Path("/groups")
public class GroupAdministration {

	@PersistenceContext(unitName = FrameworkConstants.JPA_UNIT)
	private EntityManager em;

	@Inject
	private Validator validator;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<GroupData> allGroups() {
		return new GroupEditor(em).getAllGroupsData();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData getGroupById(@PathParam("id") Long id) {
		Group group = new GroupEditor(em).getGroup(id);
		return AdminMapper.MAP_TO_GROUPDETAIL.apply(group);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData createNewGroup(GroupData groupData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(groupData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		Group group = new Group(groupData.getName());

		return AdminMapper.MAP_TO_GROUPDETAIL.apply(new GroupEditor(em).createGroup(	group,
		groupData.getPermission()));
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData updateGroup(GroupData groupData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(groupData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		Group group = new GroupEditor(em).updateGroup(	groupData.getId(),
														groupData.getName(),
														groupData.getPermission());

		return AdminMapper.MAP_TO_GROUPDETAIL.apply(group);
	}

	@DELETE
	@Path("{id}")
	public void deleteGroup(@PathParam("id") Long groupId) {
		new GroupEditor(em).deleteGroup(groupId);
	}

	private Set<ConstraintViolation<Object>>
			requestValidation(Object groupData) {
		return validator.validate(groupData);
	}
}