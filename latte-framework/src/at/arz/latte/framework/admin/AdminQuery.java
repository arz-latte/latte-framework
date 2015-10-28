package at.arz.latte.framework.admin;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.framework.module.Module;

public class AdminQuery {

	private EntityManager entityManager;

	public AdminQuery(EntityManager entityManager) {
		this.entityManager = Objects.requireNonNull(entityManager);
	}

	public TypedQuery<Module> allModules() {
		return entityManager.createNamedQuery(	Module.QUERY_GETALL,
												Module.class);
	}
	
	public TypedQuery<Module> moduleByName(String name) {
		TypedQuery<Module> query = entityManager.createNamedQuery(Module.QUERY_GET_BY_NAME,
		                                                          Module.class);
		query.setParameter("name", name);
		return query;
	}
	
	public TypedQuery<Module> moduleByUrl(String url) {
		TypedQuery<Module> query = entityManager.createNamedQuery(Module.QUERY_GET_BY_URL,
		                                                          Module.class);
		query.setParameter("url", url);
		return query;
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

	public TypedQuery<Group> allGroups() {
		return entityManager.createNamedQuery(Group.QUERY_ALL, Group.class);
	}

	public TypedQuery<Group> groupByName(String name) {
		TypedQuery<Group> query = entityManager.createNamedQuery(	Group.QUERY_GET_BY_NAME,
																	Group.class);
		query.setParameter("name", name);
		return query;
	}

	public TypedQuery<Permission> allPermissions() {
		return entityManager.createNamedQuery(	Permission.QUERY_GETALL,
												Permission.class);
	}

}
