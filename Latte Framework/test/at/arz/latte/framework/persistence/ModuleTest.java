package at.arz.latte.framework.persistence;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;
import at.arz.latte.framework.persistence.JpaPersistenceSetup;

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

		Module m = new Module(1L, "Demo Modul", "v1.00", "http://localhost:8080/demo", 60, ModuleStatus.Unknown, true);

		em.persist(m);

		em.getTransaction().commit();
	}

}
