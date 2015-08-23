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

		Module m = new Module("Demo Modul", "ARZ", "http://localhost:8081/Latte_Demo_Modul_1/api/v1/module", 60, false);

		em.persist(m);

		em.getTransaction().commit();
	}

}
