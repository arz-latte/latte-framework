package at.arz.latte.framework.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.arz.latte.framework.persistence.JpaPersistenceSetup;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.persistence.models.Role;
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
	public void createModule() {
		em.getTransaction().begin();

		Module m = new Module("Administration", "ARZ", "http://localhost:8080/latte/api/v1/administration", 10, true);
		em.persist(m);

		m = new Module("Demo Modul 1", "ARZ", "http://localhost:8080/demo1/api/v1/demo", 10, true);
		em.persist(m);

		em.getTransaction().commit();
	}

	@Test
	public void createUserAndRole() {
		em.getTransaction().begin();
		
		Role r1 = new Role("tomcat");
		Role r2 = new Role("Administrator");
		Role r3 = new Role("Benutzer");
		em.persist(r1);
		em.persist(r2);
		em.persist(r3);
		
		User u1 = new User("Admin", "Admin", "admin@arz.at", "admin");
		Set<Role> roles = new HashSet<>();
		roles.add(r1);
		roles.add(r2);
		u1.setRole(roles);
		em.persist(u1);

		User u2 = new User("User", "User", "user@arz.at", "user");
		roles = new HashSet<>();
		roles.add(r1);
		roles.add(r3);
		u2.setRole(roles);
		em.persist(u2);

		em.getTransaction().commit();
	}

}
