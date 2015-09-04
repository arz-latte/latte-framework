package at.arz.latte.framework.persistence.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Role;
import at.arz.latte.framework.persistence.models.User;
import at.arz.latte.framework.restful.dta.RoleData;
import at.arz.latte.framework.restful.dta.UserData;
import at.arz.latte.framework.services.restful.LatteValidationException;

/**
 * bean for user management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class UserManagementBean {

	@PersistenceContext(unitName = "latte-unit")
	private EntityManager em;

	public List<UserData> getAllUsersData() {
		return em.createNamedQuery(User.QUERY_GETALL_BASE, UserData.class).getResultList();
	}

	public List<RoleData> getAllRolesData() {
		return em.createNamedQuery(Role.QUERY_GETALL_BASE, RoleData.class).getResultList();
	}

	public List<User> getAllUsers() {
		return em.createNamedQuery(User.QUERY_GETALL, User.class).getResultList();
	}

	public User getUser(Long userId) {
		User u = em.find(User.class, userId);
		for (Role r : u.getRole()) {
			// fetch eager
		}
		return u;
	}

	public User getUser(String email) {
		return em.createNamedQuery(User.QUERY_GET_BY_EMAIL, User.class).setParameter("email", email).getSingleResult();
	}

	public User createUser(User user, Set<RoleData> roleData) {

		checkDuplicateUser(user.getId(), user.getEmail());

		em.persist(user);

		Set<Role> roles = new HashSet<>();
		for (RoleData r : roleData) {
			Role role = em.find(Role.class, r.getId());
			roles.add(role);
		}
		user.setRole(roles);

		return user;
	}

	public User updateUser(Long id, String firstName, String lastName, String email, String password,
			Set<RoleData> roleData) {

		checkDuplicateUser(id, email);

		User user = getUser(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);

		Set<Role> roles = new HashSet<>();
		for (RoleData r : roleData) {
			Role role = em.find(Role.class, r.getId());
			roles.add(role);
		}
		user.setRole(roles);

		return user;
	}

	public void deleteUser(Long userId) {
		User toBeDeleted = getUser(userId);
		em.remove(toBeDeleted);
	}

	/**
	 * check if email is already stored, throws LatteValidationException if user
	 * is a duplicate
	 * 
	 * @param userId
	 * @param username
	 * @throws LatteValidationException
	 */
	private void checkDuplicateUser(Long userId, String email) throws LatteValidationException {
		List<User> duplicates = em.createNamedQuery(User.QUERY_GET_BY_EMAIL, User.class).setParameter("email", email)
				.getResultList();

		if (duplicates.size() == 1 && !duplicates.get(0).getId().equals(userId)) {
			throw new LatteValidationException(400, "username", "Eintrag bereits vorhanden");
		}
	}

}