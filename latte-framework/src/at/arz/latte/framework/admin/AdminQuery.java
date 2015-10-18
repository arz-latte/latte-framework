package at.arz.latte.framework.admin;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class AdminQuery {

	private EntityManager entityManager;

	public AdminQuery(EntityManager entityManager) {
		this.entityManager = Objects.requireNonNull(entityManager);
	}

	public TypedQuery<Group> allGroups() {
		return entityManager.createNamedQuery(Group.QUERY_ALL, Group.class);
	}

	public TypedQuery<Permission> allPermissions() {
		return entityManager.createNamedQuery(	Permission.QUERY_GETALL,
												Permission.class);
	}

	public TypedQuery<User> allUsers() {
		return entityManager.createNamedQuery(User.QUERY_GETALL, User.class);
	}

	public TypedQuery<User> userByEmail(String email) {
		TypedQuery<User> query = entityManager.createNamedQuery(User.QUERY_GET_BY_EMAIL,
																User.class);
		query.setParameter("email", email);
		return query;
	}

}
