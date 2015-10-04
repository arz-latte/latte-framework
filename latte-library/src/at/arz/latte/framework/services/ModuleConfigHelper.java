package at.arz.latte.framework.services;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import at.arz.latte.framework.restful.dta.MenuData;
import at.arz.latte.framework.restful.dta.SubMenuData;
import at.arz.latte.framework.services.restful.exception.LatteValidationException;

/**
 * helper class for RESTful module management service
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class ModuleConfigHelper {

	@Inject
	private Validator validator;

	/**
	 * storage for caching service config (menu structure)
	 */
	private static Map<String, MenuData> menu = new HashMap<String, MenuData>();

	/**
	 * load and validate configuration file of a service, cache menu structure
	 * 
	 * @param url
	 * @param lastModified
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 * @throws LatteValidationException
	 */
	public MenuData loadAndCacheServiceConfig(URL url, Long lastModified)
			throws JAXBException, IOException, LatteValidationException {

		String path = url.getPath();// .getPath();
		if (menu.size() == 0 || lastModified == null || lastModified < menu.get(path).getLastModified()) {
			menu.put(path, loadServiceConfig(url));
		}

		return menu.get(path);
	}

	/**
	 * load and validate configuration file of a service
	 * 
	 * @param url
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	private MenuData loadServiceConfig(URL url) throws JAXBException, IOException, LatteValidationException {

		File file = new File(url.getPath());

		JAXBContext jaxbContext = JAXBContext.newInstance(MenuData.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		MenuData menu = (MenuData) unmarshaller.unmarshal(file);
		menu.setLastModified(url.openConnection().getLastModified());

		validateMenu(menu);

		return menu;
	}

	/**
	 * validate menu data structure
	 * 
	 * @param menu
	 * @throws LatteValidationException
	 */
	private void validateMenu(MenuData menu) throws LatteValidationException {

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
	 * @throws LatteValidationException
	 */
	private void validateSubMenu(List<SubMenuData> submenus) throws LatteValidationException {

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