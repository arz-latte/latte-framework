package at.arz.latte.framework.persistence;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.arz.latte.framework.persistence.JpaPersistenceSetup;
import at.arz.latte.framework.persistence.models.User;

public class UserTest {

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
	public void moduleIsPersistent() {
		em.getTransaction().begin();

		User u = new User("Max", "Mustermann", "max", "max");
		em.persist(u);

		em.getTransaction().commit();
	}

}
