package at.arz.latte.framework.module.services;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBException;

import at.arz.latte.framework.FrameworkConstants;
import at.arz.latte.framework.admin.restapi.AdministrationApplication;
import at.arz.latte.framework.exceptions.LatteValidationException;
import at.arz.latte.framework.module.Menu;
import at.arz.latte.framework.module.Module;
import at.arz.latte.framework.restapi.MenuData;
import at.arz.latte.framework.services.ModuleConfigHelper;

/**
 * initializes status of all stored modules (set running to false)
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Singleton
@Startup
public class FrameworkInitialization {

	@PersistenceContext(unitName = FrameworkConstants.JPA_UNIT)
	private EntityManager em;

	@EJB
	private ModuleConfigHelper configHelper;

	/**
	 * cache administration menu
	 */
	public static Menu ADMIN_MENU;

	@PostConstruct
	private void initialize()	throws LatteValidationException,
								JAXBException,
								IOException {

		// load administration configuration
		URL url = AdministrationApplication.getConfiguration();
		MenuData menuData = configHelper.loadAndCacheServiceConfig(url, null);
		ADMIN_MENU = Menu.getMenuRec(menuData);

		// initialize/stop all modules
		em.createNamedQuery(Module.STOP_ALL).executeUpdate();
	}

}