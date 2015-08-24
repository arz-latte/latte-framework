package at.arz.latte.framework.services.restful;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.modules.dta.MenuEntryData;
import at.arz.latte.framework.modules.dta.MenuRootData;
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
	public ModuleUpdateData getModule() {
		List<MenuRootData> menu = new ArrayList<>();
		
		MenuRootData menu1 = new MenuRootData(new MenuEntryData("Module", "#", 10), new ArrayList<>());		
		MenuRootData menu2 = new MenuRootData(new MenuEntryData("Benutzer", "#", 20), null);
		MenuRootData menu3 = new MenuRootData(new MenuEntryData("Rollen", "#", 30), null);

		menu.add(menu1);
		menu.add(menu2);
		menu.add(menu3);
		
		return new ModuleUpdateData("v1.00", menu);
	}
	
}