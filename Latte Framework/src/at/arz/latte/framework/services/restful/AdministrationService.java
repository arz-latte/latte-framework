package at.arz.latte.framework.services.restful;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import at.arz.latte.framework.modules.dta.MenuData;
import at.arz.latte.framework.modules.dta.MenuEntryData;
import at.arz.latte.framework.modules.dta.MenuLeafData;
import at.arz.latte.framework.modules.dta.MenuRootData;
import at.arz.latte.framework.modules.dta.ModuleData;
import at.arz.latte.framework.modules.dta.ModuleUpdateData;
import at.arz.latte.framework.persistence.beans.ModuleManagementBean;

/**
 * RESTful service for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@RequestScoped
@Path("administration")
public class AdministrationService {

	@EJB
	private ModuleManagementBean bean;

	@Inject
	private Validator validator;

	@GET
	@Path("status.json")
	@Produces({ MediaType.APPLICATION_JSON })
	public ModuleUpdateData getModule() throws JAXBException {
		List<MenuRootData> subMenu = new ArrayList<>();
		
		MenuRootData menu1 = new MenuRootData(new MenuEntryData("Module", "http://localhost:8080/latte/module.html", 10), new ArrayList<>());		
		MenuRootData menu2 = new MenuRootData(new MenuEntryData("Benutzer", "#", 20), null);
		MenuRootData menu3 = new MenuRootData(new MenuEntryData("Rollen", "#", 30), null);

		subMenu.add(menu1);
		subMenu.add(menu2);
		subMenu.add(menu3);
		
		MenuLeafData mainMenu = new MenuLeafData(new MenuEntryData("Administration", "http://localhost:8080/latte/module.html", 3), "admin");
		
		
		loadServiceConfig("administration-service-config.xml");
		
		return new ModuleUpdateData("v1.00", mainMenu, subMenu);
	}
	
	private Set<ConstraintViolation<Object>> requestValidation(Object moduleData) {
		return validator.validate(moduleData);
	}

	
	private void loadServiceConfig(String filename) throws JAXBException {
		
		try {
			URL url = getClass().getResource(filename);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");		
			System.out.println(sdf.format(url.openConnection().getLastModified()));

			File file = new File(url.getPath());

			JAXBContext jaxbContext = JAXBContext.newInstance(MenuData.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			MenuData m = (MenuData) unmarshaller.unmarshal(file);
			
			Set<ConstraintViolation<Object>> violations = requestValidation(m);
			if(!violations.isEmpty()){
				throw new RuntimeException(violations.toString());
			}	

			System.out.println(m.getUrl());
			//System.out.println(m.getSubmenu().get(1).getUrl());

		} catch (IOException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		}
		
	}
	
}