package at.arz.latte.framework.persistence.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.persistence.models.Group;
import at.arz.latte.framework.persistence.models.User;
import at.arz.latte.framework.restful.dta.GroupData;
import at.arz.latte.framework.restful.dta.UserData;
import at.arz.latte.framework.services.restful.exception.LatteValidationException;

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

	public List<GroupData> getAllGroupsData() {
		return em.createNamedQuery(Group.QUERY_GETALL_BASE, GroupData.class).getResultList();
	}

	public List<User> getAllUsers() {
		return em.createNamedQuery(User.QUERY_GETALL, User.class).getResultList();
	}

	public User getUser(Long userId) {
		User u = em.find(User.class, userId);
		for (Group r : u.getGroup()) {
			// fetch eager
		}
		return u;
	}

	/**
	 * get user by unique email (login credentials in session)
	 * 
	 * @param email
	 * @return
	 */
	public User getUser(String email) {
		User user = em.createNamedQuery(User.QUERY_GET_BY_EMAIL, User.class).setParameter("email", email)
				.getSingleResult();
		return user;
	}

	/**
	 * get list of all permissions for an user
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getUserPermissions(Long userId) {
		return em.createNamedQuery(Permission.QUERY_GET_NAME_BY_USER, String.class).setParameter("id", userId)
				.getResultList();
	}

	public User createUser(User user, Set<GroupData> groupData) {

		checkDuplicateUser(user.getId(), user.getEmail());

		em.persist(user);

		Set<Group> groups = new HashSet<>();
		for (GroupData r : groupData) {
			Group group = em.find(Group.class, r.getId());
			groups.add(group);
		}
		user.setGroup(groups);

		return user;
	}

	public User updateUser(Long id, String firstName, String lastName, String email, String password,
			Set<GroupData> groupData) {

		checkDuplicateUser(id, email);

		User user = getUser(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);

		Set<Group> groups = new HashSet<>();
		for (GroupData g : groupData) {
			Group group = em.find(Group.class, g.getId());
			groups.add(group);
		}
		user.setGroup(groups);

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