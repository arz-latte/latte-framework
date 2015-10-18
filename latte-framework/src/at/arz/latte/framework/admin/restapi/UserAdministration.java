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
import at.arz.latte.framework.admin.User;
import at.arz.latte.framework.exceptions.LatteValidationException;
import at.arz.latte.framework.restapi.GroupData;
import at.arz.latte.framework.restapi.UserData;
import at.arz.latte.framework.util.Functions;
import at.arz.latte.framework.util.JPA;

@Stateless
@Path("/users")
public class UserAdministration {

	@PersistenceContext(unitName = FrameworkConstants.JPA_UNIT)
	private EntityManager em;

	@Inject
	private Validator validator;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserData> allUsers() {
		return Functions.map(AdminMapper.USER_TO_USERDATA, new AdminQuery(em).allUsers());
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData userWithId(@PathParam("id") Long id) {
		User user = em.find(User.class, id);
		JPA.fetchAll(user.getGroup());
		return AdminMapper.USER_TO_USERDATA.apply(user);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserData createUser(UserData userData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(userData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		User user = new User(	userData.getFirstName(),
								userData.getLastName(),
								userData.getEmail(),
								userData.getPassword());

		return toUserData(new UserEditor(em).createUser(user, userData.getGroup()));
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public UserData updateUser(UserData userData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(userData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		User user = new UserEditor(em).updateUser(	userData.getId(),
												userData.getFirstName(),
												userData.getLastName(),
												userData.getEmail(),
												userData.getPassword(),
												userData.getGroup());

		return toUserData(user);
	}

	@DELETE
	@Path("{id}")
	public void deleteUser(@PathParam("id") Long userId) {
		new UserEditor(em).deleteUser(userId);
	}

	private Set<ConstraintViolation<Object>> requestValidation(Object userData) {
		return validator.validate(userData);
	}

	/**
	 * helper for REST service
	 * 
	 * @param userData
	 * @return
	 */
	public UserData toUserData(User user) {
		UserData userData = new UserData();
		userData.setId(user.getId());
		userData.setFirstName(user.getFirstName());
		userData.setLastName(user.getLastName());
		userData.setEmail(user.getEmail());
		userData.setPassword(user.getPassword());

		if (user.getGroup() != null) {
			for (Group group : user.getGroup()) {
				GroupData groupData = new GroupData();
				groupData.setId(group.getId());
				userData.addGroup(groupData);
			}
		}

		return userData;
	}

}
