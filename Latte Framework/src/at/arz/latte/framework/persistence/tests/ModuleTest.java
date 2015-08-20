package at.arz.latte.framework.persistence.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jvmhub.tutorial.entity.AppUser;

import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;
import at.arz.latte.framework.persistence.beans.ModuleManagementBean;

public class ModuleTest {

	private Context ctx;
	private EJBContainer ejbContainer;

	@BeforeClass
	public void setUp() {
		ejbContainer = EJBContainer.createEJBContainer();
		System.out.println("Opening the container");
		ctx = ejbContainer.getContext();
	}

	@AfterClass
	public void tearDown() {
		ejbContainer.close();
		System.out.println("Closing the container");
	}

	@Test
	public void test() throws Exception {

		ModuleManagementBean bean = (ModuleManagementBean) ctx.lookup("java:global/classes/CommentService");

		System.out.println(bean);

		/*
		 * final Properties p = new Properties();
		 * 
		 * final Context context =
		 * EJBContainer.createEJBContainer(p).getContext();
		 * 
		 * ModuleManagementBean moduleManagementBean = (ModuleManagementBean)
		 * context .lookup("java:global/Latte Framework/ModuleManagementBean");
		 * 
		 * /* final Properties p = new Properties();
		 * 
		 * p.put("LatteFrameworkDS", "new://Resource?type=DataSource");
		 * p.put("LatteFrameworkDS.JdbcDriver", "com.mysql.jdbc.Driver");
		 * p.put("LatteFrameworkDS.JdbcUrl",
		 * "jdbc:mysql://localhost:3306/latte");
		 * p.put("LatteFrameworkDS.userName", "latte");
		 * p.put("LatteFrameworkDS.password", "latte");
		 * 
		 * final Context context =
		 * EJBContainer.createEJBContainer(p).getContext();
		 * 
		 * ModuleManagementBean moduleManagementBean = (ModuleManagementBean)
		 * context .lookup("java:global/Latte Framework/ModuleManagementBean");
		 * 
		 * 
		 * Module m = new Module(0, "Demo Modul", "1",
		 * "http://localhost:8080/Latte_Framework", 60, ModuleStatus.Unknown,
		 * true); moduleManagementBean.createModule(m);
		 * 
		 * List<Module> mo = moduleManagementBean.getAllModules();
		 * assertEquals(1, mo.size());
		 * 
		 * assertEquals(true, mo.get(0).getEnabled());
		 */
	}

}
