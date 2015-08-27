package at.arz.latte.framework.services.restful;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
	
	private void loadServiceConfig(String filename) throws JAXBException {
		
		URL url = getClass().getResource(filename);
		File file = new File(url.getPath());

		JAXBContext jaxbContext = JAXBContext.newInstance(ModuleData.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		ModuleData m = (ModuleData) unmarshaller.unmarshal(file);
		System.out.println(m.getName());
	}
	
}