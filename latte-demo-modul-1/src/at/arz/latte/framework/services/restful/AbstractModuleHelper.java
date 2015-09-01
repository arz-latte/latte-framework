package at.arz.latte.framework.services.restful;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import at.arz.latte.framework.restful.dta.MenuData;
import at.arz.latte.framework.restful.dta.SubMenuData;

/**
 * helper class for RESTful module management service
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@ApplicationScoped
public abstract class AbstractModuleHelper {

	@Inject
	private Validator validator;

	/**
	 * storage for menu structure
	 */
	private static MenuData menu;

	/**
	 * load and validate configuration file of a service, cache menu structure
	 * 
	 * @param filename
	 * @param lastModified
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	protected MenuData loadAndCacheServiceConfig(String filename, Long lastModified) throws JAXBException, IOException {

		if (menu == null || lastModified == null || lastModified < menu.getLastModified()) {
			menu = loadServiceConfig(filename);
		}

		return menu;
	}

	/**
	 * load and validate configuration file of a service
	 * 
	 * @param filename
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	protected MenuData loadServiceConfig(String filename) throws JAXBException, IOException {

		URL url = getClass().getResource(filename);

		File file = new File(url.getPath());

		JAXBContext jaxbContext = JAXBContext.newInstance(MenuData.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		MenuData menu = (MenuData) unmarshaller.unmarshal(file);
		menu.setLastModified(url.openConnection().getLastModified());

		// validate menu structure
		validateMenu(menu);

		return menu;
	}

	/**
	 * validate menu data structure
	 * 
	 * @param menu
	 */
	private void validateMenu(MenuData menu) {

		// validate menu structure
		Set<ConstraintViolation<Object>> violations = requestValidation(menu);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(500, violations);
		}

		// validate sub menu structure
		validateSubMenu(menu.getSubMenus());
	}

	/**
	 * validate sub menu data structure
	 * 
	 * @param submenus
	 */
	private void validateSubMenu(List<SubMenuData> submenus) {

		if (submenus != null) {

			for (SubMenuData submenu : submenus) {

				Set<ConstraintViolation<Object>> violations = requestValidation(submenu);
				if (!violations.isEmpty()) {
					throw new LatteValidationException(500, violations);
				}

				// recursive validate sub menu structure
				validateSubMenu(submenu.getSubMenus());
			}
		}
	}

	private Set<ConstraintViolation<Object>> requestValidation(Object moduleData) {
		return validator.validate(moduleData);
	}

}