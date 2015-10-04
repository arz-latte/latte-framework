package at.arz.latte.framework.persistence.beans;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBException;

import at.arz.latte.framework.persistence.models.Menu;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.persistence.models.SubMenu;
import at.arz.latte.framework.restful.dta.MenuData;
import at.arz.latte.framework.restful.dta.ModuleData;
import at.arz.latte.framework.restful.dta.SubMenuData;
import at.arz.latte.framework.services.ModuleConfigHelper;
import at.arz.latte.framework.services.restful.exception.LatteValidationException;

/**
 * bean for framework management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class FrameworkManagementBean {

	@PersistenceContext(unitName = "latte-unit")
	private EntityManager em;

	@EJB
	private ModuleConfigHelper configHelper;
	
	/**
	 * cache administration menu
	 */
	private static Menu adminMenu;

	/**
	 * get all modules with their menus for REST-service
	 * 
	 * @param permissions
	 * @return
	 */
	public List<ModuleData> getAll(List<String> permissions) {

		String config = "administration-service-config.xml";

		List<ModuleData> modulesData = new ArrayList<>();

		// load administration service config
		if (adminMenu == null) {
		
			try {
				URL url = getClass().getResource("../../services/restful/admin/" + config);
				MenuData menuData = configHelper.loadServiceConfig(url);
				adminMenu = Menu.getMenuRec(menuData);				
				System.out.println("---- load admin menu ----");
				System.out.println(adminMenu);
			} catch (JAXBException | IOException | LatteValidationException ex) {
				System.out.println("Exception while loading administration-service-config: " + ex.getMessage());
			}
		}
		
		// load administration menu if user has permission
		ModuleData moduleData = new ModuleData();
		moduleData.setId(0L);
		moduleData.setRunning(true);
		moduleData.setMenu(getMenuData(adminMenu, permissions));

		// ignore module if user has no permission to submenus
		if (moduleData.getMenu() != null) {
			modulesData.add(moduleData);
		}
		
		// get modules
		List<Module> modules = em.createNamedQuery(Module.QUERY_GETALL_ENABLED_SORTED, Module.class).getResultList();
		for (Module module : modules) {

			moduleData = new ModuleData();
			moduleData.setId(module.getId());
			moduleData.setRunning(module.getRunning());

			moduleData.setMenu(getMenuData(module.getMenu(), permissions));

			// ignore module if user has no permission to submenus
			if (moduleData.getMenu() != null) {
				modulesData.add(moduleData);
			}
		}

		return modulesData;
	}

	/**
	 * convert menu entity to menu data for REST
	 * 
	 * @param menu
	 * @return menu structure or null if permission for all submenus is missing
	 */
	private MenuData getMenuData(Menu menu, List<String> permissions) {

		// ignore module if user has no permission
		if (menu.getPermission() != null && !permissions.contains(menu.getPermission().getName())) {
			return null;
		}

		// main menu entry
		MenuData menuData = new MenuData(menu.getName(), menu.getUrl(), menu.getOrder());

		// sub menu entries
		if (menu.getSubMenus() != null && !menu.getSubMenus().isEmpty()) {
			for (SubMenu subMenu : menu.getSubMenus()) {
				menuData.addSubMenu(getSubMenuDataRec(subMenu, permissions));
			}

			// ignore module if user has no permmission for any submenu
			if (menuData.getSubMenus().isEmpty()) {
				return null;
			}
		}

		return menuData;
	}

	/**
	 * recursive convert submenu entity to submenu data for REST
	 * 
	 * @param menu
	 * @return
	 */
	private SubMenuData getSubMenuDataRec(SubMenu menu, List<String> permissions) {

		// ignore submenu if user has no permission
		if (menu.getPermission() != null && !permissions.contains(menu.getPermission().getName())) {
			return null;
		}

		SubMenuData subMenuData = new SubMenuData(menu.getName(), menu.getUrl(), menu.getScript(), menu.getType(),
				menu.getGroup(), menu.getDisabled());

		// recursive add sub menus
		if (menu.getSubMenus() != null && !menu.getSubMenus().isEmpty()) {
			for (SubMenu subMenu : menu.getSubMenus()) {
				subMenuData.addSubMenu(getSubMenuDataRec(subMenu, permissions));
			}
		}

		return subMenuData;
	}

}