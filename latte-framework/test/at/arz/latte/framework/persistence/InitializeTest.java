package at.arz.latte.framework.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.arz.latte.framework.persistence.JpaPersistenceSetup;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.persistence.models.Group;
import at.arz.latte.framework.persistence.models.User;

public class InitializeTest  {

	private EntityManager em;

	@Before
	public void setup() {
		em = JpaPersistenceSetup.createEntityManager();
	}

	@After
	public void tearDown() {
		em.close();
	}

	@Test
	public void createAuthenticationViews() {

		String rolesView = "CREATE OR REPLACE VIEW tc_realm_roles AS " + "SELECT users.email AS username, groups.name AS rolename "
				+ "FROM (users LEFT JOIN ( SELECT users_groups.user_id, groups_1.name "
				+ "FROM (groups groups_1 LEFT JOIN users_groups ON ((users_groups.group_id = groups_1.id)))) groups ON ((groups.user_id = users.id))); ";

		String usersView = "CREATE OR REPLACE VIEW tc_realm_users AS SELECT users.email AS username, users.password FROM users;";

		em.getTransaction().begin();
		
		Query q = em.createNativeQuery(rolesView);
		q.executeUpdate();
		
		q = em.createNativeQuery(usersView);
		q.executeUpdate();
		
		em.getTransaction().commit();
	}

	@Test
	public void createDefaultUsers() {
		em.getTransaction().begin();
		
		// permissions
		Permission pAdmin = new Permission("admin");
		em.persist(pAdmin);

		Permission pDemo = new Permission("demo");
		em.persist(pDemo);
		
		// groups
		Group gUser = new Group("LatteUser");	// required for authentication
		em.persist(gUser);
		
		Group gDemo = new Group("LatteDemo");	// required for demo
		gDemo.addPermission(pDemo);
		em.persist(gDemo);

		Group gAdmin = new Group("LatteAdministrator");	// required for administration
		gAdmin.addPermission(pAdmin);
		em.persist(gAdmin);
		
		// users
		User uAdmin = new User("Admin", "Admin", "admin@arz.at", "admin");
		Set<Group> groups = new HashSet<>();
		groups.add(gUser);
		groups.add(gDemo);
		groups.add(gAdmin);
		uAdmin.setGroup(groups);
		em.persist(uAdmin);		

		User uDemo = new User("User", "User", "user@arz.at", "user");
		groups = new HashSet<>();
		groups.add(gUser);
		groups.add(gDemo);
		uDemo.setGroup(groups);
		em.persist(uDemo);		
		
		// demo module
		Module mDemo = new Module("Demo", "ARZ", "demo", 60, true);
		em.persist(mDemo);
		
		em.getTransaction().commit();
	}

}
