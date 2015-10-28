package at.arz.latte.framework.admin.restapi;

import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

import at.arz.latte.framework.admin.AdminQuery;
import at.arz.latte.framework.admin.Group;
import at.arz.latte.framework.admin.User;
import at.arz.latte.framework.exceptions.LatteValidationException;
import at.arz.latte.framework.restapi.GroupData;
import at.arz.latte.framework.restapi.UserData;

/**
 * bean for user management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
class UserEditor {
	private static final Logger LOG = Logger.getLogger(UserEditor.class.getSimpleName());

	private EntityManager em;

	public UserEditor(EntityManager em) {
		this.em = Objects.requireNonNull(em);
	}

	public User createUser(UserData userData) {

		assertEmailIsNotOwnedByAnotherUser(userData.getEmail());

		User user = new User(	userData.getFirstName(),
								userData.getLastName(),
								userData.getEmail(),
								userData.getPassword());
		em.persist(user);

		setUserGroups(user, userData.getGroup());

		return user;
	}

	public User updateUser(UserData userData) {

		User user = em.find(User.class, userData.getId());
		user.setFirstName(userData.getFirstName());
		user.setLastName(userData.getLastName());
		user.setPassword(userData.getPassword());

		if (!Objects.equals(user.getEmail(), userData.getEmail())) {
			assertEmailIsNotOwnedByAnotherUser(userData.getEmail());
			user.setEmail(userData.getEmail());
		}
		
		setUserGroups(user, userData.getGroup());

		return user;
	}

	public void deleteUser(Long id) {
		try {
			User user = em.find(User.class, id);
			em.remove(user);
			LOG.info("deleteUser: user deleted:" + user.getEmail());
		} catch (EntityNotFoundException e) {
			LOG.info("deleteUser: user not found:" + id);
		}
	}

	/**
	 * check if email is already stored, throws LatteValidationException if user
	 * is a duplicate
	 * 
	 * @param email
	 * @throws LatteValidationException
	 */
	private void assertEmailIsNotOwnedByAnotherUser(String email) {
		TypedQuery<User> query = new AdminQuery(em).userByEmail(email);
		if (!query.getResultList().isEmpty()) {
			LOG.info("can't store user, email already exists:" + email);
			throw new LatteValidationException(	400,
												"email",
												"Wird bereits von einem anderen Benutzer verwendet.");
		}
	}

	private void setUserGroups(User user, Set<GroupData> groupData) {
		for (GroupData g : groupData) {
			Group group = em.find(Group.class, g.getId());
			user.addGroup(group);
		}
	}
	
}