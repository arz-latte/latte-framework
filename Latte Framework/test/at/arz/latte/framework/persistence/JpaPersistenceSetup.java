package at.arz.latte.framework.persistence;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.derby.jdbc.EmbeddedDataSource;

public class JpaPersistenceSetup {

	private static EntityManagerFactory entityMangerFactory;

	public static EntityManager createEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityMangerFactory == null) {
			initEntityManagerFactory();
		}
		return entityMangerFactory;
	}

	private static void initEntityManagerFactory() {
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

	private static Properties loadJpaPersistenceSetupProperties() {
		Properties persistenceProperties = new Properties();
		try {
			persistenceProperties.load(ModuleTest.class.getResourceAsStream("persistence-test.properties"));
			return persistenceProperties;
		} catch (IOException e) {
			throw new RuntimeException("could not load jpaPersistenceSetupProperties", e);
		}
	}

}