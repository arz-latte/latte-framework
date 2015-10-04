package at.arz.latte.framework.persistence.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import at.arz.latte.framework.persistence.models.Group;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.persistence.models.User;

/**
 * initializes status of all stored modules (set running to false)
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Singleton
@Startup
@DependsOn("ModuleManagementBean")
public class InitializationBean {

	@PersistenceContext(unitName = "latte-unit")
	private EntityManager em;

	@PostConstruct
	private void initialize() {
		
		// first install - create admin user and views
		List<User> users = em.createNamedQuery(User.QUERY_GETALL, User.class).getResultList();
		
		if (users.isEmpty()) {
			createAuthenticationViews();			
			createAdminUser();
		}
		
		// initialize/stop all modules
		em.createNamedQuery(Module.STOP_ALL).executeUpdate();
	}

	private void createAuthenticationViews() {
		String rolesView = "CREATE OR REPLACE VIEW tc_realm_roles AS " + "SELECT users.email AS username, groups.name AS rolename "
				+ "FROM (users LEFT JOIN ( SELECT users_groups.user_id, groups_1.name "
				+ "FROM (groups groups_1 LEFT JOIN users_groups ON ((users_groups.group_id = groups_1.id)))) groups ON ((groups.user_id = users.id))); ";

		String usersView = "CREATE OR REPLACE VIEW tc_realm_users AS SELECT users.email AS username, users.password FROM users;";

		Query q = em.createNativeQuery(rolesView);
		q.executeUpdate();
		
		q = em.createNativeQuery(usersView);
		q.executeUpdate();
	}

	private void createAdminUser() {
		Permission pAdmin = new Permission("admin");
		em.persist(pAdmin);
		
		Group gUser = new Group("LatteUser");	// required for authentication
		em.persist(gUser);

		Group gAdmin = new Group("LatteAdministrator");	// required for administration
		Set<Permission> permissions = new HashSet<>();
		permissions.add(pAdmin);
		gAdmin.setPermission(permissions);
		em.persist(gAdmin);
		
		User uAdmin = new User("Admin", "Admin", "admin@arz.at", "admin");
		Set<Group> groups = new HashSet<>();
		groups.add(gUser);
		groups.add(gAdmin);
		uAdmin.setGroup(groups);
		em.persist(uAdmin);
	}
}
