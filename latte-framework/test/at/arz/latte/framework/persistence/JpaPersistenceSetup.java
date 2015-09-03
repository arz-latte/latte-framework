package at.arz.latte.framework.persistence;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.postgresql.ds.PGSimpleDataSource;

public class JpaPersistenceSetup {

	private static EntityManagerFactory entityMangerFactory;

	public static EntityManager createEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityMangerFactory == null) {
			initPostgreEntityManagerFactory(); // or initDerbyEntityManagerFactory();
		}
		return entityMangerFactory;
	}

	private static void initDerbyEntityManagerFactory() {
		Properties properties = loadJpaPersistenceSetupProperties();
		EmbeddedDataSource dataSource = createDerbyDB();
		properties.put("openjpa.ConnectionFactory", dataSource);
		properties.put("openjpa.ConnectionFactory2", dataSource);
		entityMangerFactory = Persistence.createEntityManagerFactory("latte-unit", properties);
	}

	private static EmbeddedDataSource createDerbyDB() {
		EmbeddedDataSource dataSource = new EmbeddedDataSource();
		dataSource.setCreateDatabase("create");
		dataSource.setDatabaseName("memory:test-jpa");
		return dataSource;
	}

	private static void initPostgreEntityManagerFactory() {
		Properties properties = loadJpaPersistenceSetupProperties();		
		PGSimpleDataSource dataSource = createPostgreDB();
		properties.put("openjpa.ConnectionFactory", dataSource);
		properties.put("openjpa.ConnectionFactory2", dataSource);
		entityMangerFactory = Persistence.createEntityManagerFactory("latte-unit", properties);
		
	}
	
	private static PGSimpleDataSource createPostgreDB() {
		PGSimpleDataSource dataSource = new PGSimpleDataSource();
		dataSource.setServerName("localhost");
		dataSource.setDatabaseName("latte");
		dataSource.setUser("latte");
		dataSource.setPassword("latte");
		return dataSource;
	}

	private static Properties loadJpaPersistenceSetupProperties() {
		Properties persistenceProperties = new Properties();
		try {
			persistenceProperties.load(InitializeTest.class.getResourceAsStream("persistence-test.properties"));
			return persistenceProperties;
		} catch (IOException e) {
			throw new RuntimeException("could not load jpaPersistenceSetupProperties", e);
		}
	}

}