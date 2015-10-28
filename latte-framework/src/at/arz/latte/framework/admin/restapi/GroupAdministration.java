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
import at.arz.latte.framework.admin.AdminQuery;
import at.arz.latte.framework.admin.Group;
import at.arz.latte.framework.exceptions.LatteValidationException;
import at.arz.latte.framework.restapi.GroupData;
import at.arz.latte.framework.util.Functions;
import at.arz.latte.framework.util.JPA;

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
		return Functions.map(	AdminMapper.MAP_TO_GROUPDATA,
								new AdminQuery(em).allGroups());
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData groupWithId(@PathParam("id") Long id) {
		Group group = em.find(Group.class, id);
		JPA.fetchAll(group.getPermissions());
		return AdminMapper.MAP_TO_GROUPDETAIL.apply(group);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData createGroup(GroupData groupData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(groupData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		Group group = new GroupEditor(em).createGroup(groupData);
		return AdminMapper.MAP_TO_GROUPDETAIL.apply(group);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData updateGroup(GroupData groupData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(groupData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}
		
		Group group = new GroupEditor(em).updateGroup(groupData);
		return AdminMapper.MAP_TO_GROUPDETAIL.apply(group);
	}

	@DELETE
	@Path("{id}")
	public void deleteGroup(@PathParam("id") Long id) {
		new GroupEditor(em).deleteGroup(id);
	}

	private	Set<ConstraintViolation<Object>>
			requestValidation(Object groupData) {
		return validator.validate(groupData);
	}
}