package at.arz.latte.framework.services.restful;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import at.arz.latte.framework.dta.MenuData;
import at.arz.latte.framework.dta.SubMenuData;

/**
 * helper class for RESTful module management service
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@RequestScoped
public abstract class GenericModuleService {

	@Inject
	private Validator validator;	

	/**
	 * validate menu data structure
	 * 
	 * @param menu
	 */
	private void validateMenu(MenuData menu) {

		// validate menu structure
		Set<ConstraintViolation<Object>> violations = requestValidation(menu);
		if (!violations.isEmpty()) {
			// throw new RuntimeException(violations.toString());
			throw new LatteValidationException(500, violations);
		}

		// validate sub menu structure
		validateSubMenu(menu.getSubmenus());
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
					// throw new RuntimeException(violations.toString());
				}

				// recursive validate sub menu structure
				validateSubMenu(submenu.getSubmenus());
			}
		}
	}
	
	private Set<ConstraintViolation<Object>> requestValidation(Object moduleData) {
		return validator.validate(moduleData);
	}

	/**
	 * load and validate configuration file of a service
	 * @param filename
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	protected MenuData loadServiceConfig(String filename) throws JAXBException, IOException {

		URL url = getClass().getResource(filename);

		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		// System.out.println(sdf.format(url.openConnection().getLastModified()));

		File file = new File(url.getPath());

		JAXBContext jaxbContext = JAXBContext.newInstance(MenuData.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		MenuData menu = (MenuData) unmarshaller.unmarshal(file);
		menu.setLastmodified(url.openConnection().getLastModified());

		// validate menu structure
		validateMenu(menu);

		return menu;
	}

}