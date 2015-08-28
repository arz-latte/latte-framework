package at.arz.latte.framework.persistence;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.arz.latte.framework.persistence.JpaPersistenceSetup;
import at.arz.latte.framework.persistence.models.Module;

public class ModuleTest  {

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

		Module m = new Module("Administration", "ARZ", "http://localhost:8080/latte/api/v1/administration", 60, true);
		em.persist(m);

		m = new Module("Demo Modul 1", "ARZ", "http://localhost:8080/demo/api/v1/demo", 60, true);
		em.persist(m);

		em.getTransaction().commit();
	}

}
