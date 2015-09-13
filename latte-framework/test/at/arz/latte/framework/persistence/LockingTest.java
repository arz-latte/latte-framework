package at.arz.latte.framework.persistence;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.apache.openjpa.persistence.FetchPlan;
import org.apache.openjpa.persistence.OpenJPAQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.arz.latte.framework.persistence.models.Permission;

public class LockingTest  {

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
	public void lockingTest() {
		em.getTransaction().begin();

	/*	OpenJPAQuery q = em.createNamedQuery(Permission.QUERY_GETALL); 
		
		FetchPlan fp = q.getFetchPlan();
		fp.setReadLockMode(LockModeType.NONE);
		*/
		Permission m = em.find(Permission.class, 1L, LockModeType.NONE);
		
		em.getTransaction().commit();
	}


}
