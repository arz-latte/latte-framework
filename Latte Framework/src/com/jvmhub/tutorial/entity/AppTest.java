package com.jvmhub.tutorial.entity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	private EntityManager entityManager;
	
	public void testApp() {
		entityManager = Persistence.createEntityManagerFactory("latte-unit")
				.createEntityManager();
 
		entityManager.getTransaction().begin();
 
		AppUser user = new AppUser("seconduser");
		entityManager.persist(user);
 
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}