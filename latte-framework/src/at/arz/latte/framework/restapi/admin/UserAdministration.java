package at.arz.latte.framework.restapi.admin;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
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

import at.arz.latte.framework.persistence.beans.UserManagementBean;
import at.arz.latte.framework.persistence.models.Group;
import at.arz.latte.framework.persistence.models.User;
import at.arz.latte.framework.restful.dta.GroupData;
import at.arz.latte.framework.restful.dta.UserData;
import at.arz.latte.framework.services.restful.exception.LatteValidationException;

@Stateless
@Path("/users")
public class UserAdministration {

	@Inject
	private UserManagementBean bean;

	@Inject
	private Validator validator;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserData> allUsers() {
		return bean.getAllUsersData();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData userWithId(@PathParam("id") Long id) {
		User user = bean.getUser(id);
		return toUserData(user);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserData createUser(UserData userData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(userData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		User user = new User(userData.getFirstName(), userData.getLastName(),
				userData.getEmail(), userData.getPassword());

		return toUserData(bean.createUser(user, userData.getGroup()));
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public UserData updateUser(UserData userData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(userData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		User user = bean.updateUser(userData.getId(), userData.getFirstName(),
				userData.getLastName(), userData.getEmail(),
				userData.getPassword(), userData.getGroup());

		return toUserData(user);
	}

	@DELETE
	@Path("{id}")
	public void deleteUser(@PathParam("id") Long userId) {
		bean.deleteUser(userId);
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
