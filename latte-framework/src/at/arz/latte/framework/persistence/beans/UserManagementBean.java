package at.arz.latte.framework.persistence.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.User;
import at.arz.latte.framework.restful.dta.UserData;

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

	public List<User> getAllUsers() {
		return em.createNamedQuery(User.QUERY_GETALL, User.class).getResultList();
	}

	public User getUser(Long userId) {
		return em.find(User.class, userId);
	}

	public User createUser(User user) {
		em.persist(user);
		return user;
	}

	/**
	 * update user via REST-service
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param password
	 * @return
	 */
	public User updateUser(Long id, String firstName, String lastName, String username, String password) {
		User user = getUser(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}

	public void deleteUser(Long userId) {
		User toBeDeleted = getUser(userId);
		em.remove(toBeDeleted);
	}

}