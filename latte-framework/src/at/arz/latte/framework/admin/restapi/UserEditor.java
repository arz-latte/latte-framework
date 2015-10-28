package at.arz.latte.framework.admin.restapi;

import java.util.HashSet;
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

		User user = new User(	userData.getFirstName(),
								userData.getLastName(),
								userData.getEmail(),
								userData.getPassword());

		assertEmailIsNotOwnedByAnotherUser(user.getEmail());

		em.persist(user);

		Set<Group> groups = new HashSet<>();
		for (GroupData r : userData.getGroup()) {
			Group group = em.find(Group.class, r.getId());
			groups.add(group);
		}
		user.setGroup(groups);

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

		Set<Group> groups = new HashSet<>();
		for (GroupData g : userData.getGroup()) {
			Group group = em.find(Group.class, g.getId());
			groups.add(group);
		}
		user.setGroup(groups);

		return user;
	}

	public void deleteUser(Long userId) {
		try {
			User user = em.find(User.class, userId);
			em.remove(user);
			LOG.info("deleteUser: user deleted:" + user.getEmail());
		} catch (EntityNotFoundException e) {
			LOG.info("deleteUser: user not found:" + userId);
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
												"username",
												"Email wird bereits von einem anderen Benutzer verwendet.");
		}
	}

}