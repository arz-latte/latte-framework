package at.arz.latte.framework.persistence.tests;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.Test;

import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;
import junit.framework.TestCase;

public class ModuleTest extends TestCase {

	@Test
	public void test() throws Exception {

		EntityManager em = Persistence.createEntityManagerFactory("latte-unit").createEntityManager();		
		em.getTransaction().begin();

		Module m = new Module(0, "Demo Modul", "1", "http://localhost:8080/Latte_Framework", 60, ModuleStatus.Unknown,
				true);
				
		em.persist(m);

		em.getTransaction().commit();
		em.close();

	}

}
