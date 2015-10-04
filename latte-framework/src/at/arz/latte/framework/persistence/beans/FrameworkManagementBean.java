package at.arz.latte.framework.persistence.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Menu;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.persistence.models.SubMenu;
import at.arz.latte.framework.restful.dta.MenuData;
import at.arz.latte.framework.restful.dta.ModuleData;
import at.arz.latte.framework.restful.dta.SubMenuData;

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

	/**
	 * get all modules with their menus for REST-service
	 * 
	 * @param permissions
	 * @return
	 */
	public List<ModuleData> getAll(List<String> permissions) {

		List<ModuleData> modulesData = new ArrayList<>();
		
		// get modules from database
		List<Module> modules = em.createNamedQuery(Module.QUERY_GETALL_ENABLED_SORTED, Module.class).getResultList();
		for (Module module : modules) {
			
			ModuleData moduleData = new ModuleData();
			moduleData.setId(module.getId());
			moduleData.setRunning(module.getRunning());

			moduleData.setMenu(getMenuData(module.getMenu(), permissions));

			// ignore module if user has no permission to submenus
			if (moduleData.getMenu() != null) {
				modulesData.add(moduleData);
			}
		}

		// load and sort administration menu if user has "admin"-permission
		if(permissions.contains("admin")) {
			ModuleData moduleData = new ModuleData();
			moduleData.setId(0L);
			moduleData.setRunning(true);
			moduleData.setMenu(getMenuData(InitializationBean.ADMIN_MENU, permissions));
			
			// sort admin menu
			boolean added = false;
			for(int i = 0; i < modulesData.size(); i++) { 
				MenuData menuData = modulesData.get(i).getMenu();
				if (menuData.getOrder() > moduleData.getMenu().getOrder()) {
					modulesData.add(i, moduleData);
					added = true;
					break;
				}
			}
			
			if (!added) {
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