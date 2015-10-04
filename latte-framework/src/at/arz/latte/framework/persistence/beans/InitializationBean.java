package at.arz.latte.framework.persistence.beans;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBException;

import at.arz.latte.framework.persistence.models.Menu;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.restful.dta.MenuData;
import at.arz.latte.framework.services.ModuleConfigHelper;
import at.arz.latte.framework.services.restful.exception.LatteValidationException;

/**
 * initializes status of all stored modules (set running to false)
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Singleton
@Startup
@DependsOn("ModuleManagementBean")
public class InitializationBean {
	
	private static final String CONFIG_PATH = "../../services/restful/admin/administration-service-config.xml";
	
	@PersistenceContext(unitName = "latte-unit")
	private EntityManager em;
	
	@EJB
	private ModuleConfigHelper configHelper;
	
	/**
	 * cache administration menu
	 */
	public static Menu ADMIN_MENU;
	
	@PostConstruct
	private void initialize() throws LatteValidationException, JAXBException, IOException {
		
		// load administration configuration
		URL url = getClass().getResource(CONFIG_PATH);
		MenuData menuData = configHelper.loadAndCacheServiceConfig(url, null);
		ADMIN_MENU = Menu.getMenuRec(menuData);
		
		// initialize/stop all modules
		em.createNamedQuery(Module.STOP_ALL).executeUpdate();
	}

	
}
