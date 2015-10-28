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
import at.arz.latte.framework.admin.User;
import at.arz.latte.framework.exceptions.LatteValidationException;
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
		return Functions.map(	AdminMapper.MAP_TO_USERDATA,
								new AdminQuery(em).allUsers());
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData userWithId(@PathParam("id") Long id) {
		User user = em.find(User.class, id);
		JPA.fetchAll(user.getGroup());
		return AdminMapper.MAP_TO_USERDETAIL.apply(user);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserData createUser(UserData userData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(userData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		User user = new UserEditor(em).createUser(userData);
		return AdminMapper.MAP_TO_USERDETAIL.apply(user);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public UserData updateUser(UserData userData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(userData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		User user = new UserEditor(em).updateUser(userData);

		return AdminMapper.MAP_TO_USERDETAIL.apply(user);
	}

	@DELETE
	@Path("{id}")
	public void deleteUser(@PathParam("id") Long userId) {
		new UserEditor(em).deleteUser(userId);
	}

	private	Set<ConstraintViolation<Object>>
			requestValidation(Object userData) {
		return validator.validate(userData);
	}

}
