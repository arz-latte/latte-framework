package at.arz.latte.framework.persistence.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.persistence.models.User;

/**
 * bean for user permission management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class PermissionManagementBean {

	@PersistenceContext(unitName = "latte-unit")
	private EntityManager em;

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
	 * @param email
	 * @return
	 */
	public List<String> getUserPermissions(String email) {
		return em.createNamedQuery(Permission.QUERY_GET_NAME_BY_USER, String.class).setParameter("email", email)
				.getResultList();
	}

}