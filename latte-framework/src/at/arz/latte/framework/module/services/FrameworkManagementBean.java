package at.arz.latte.framework.module.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.FrameworkConstants;
import at.arz.latte.framework.module.Menu;
import at.arz.latte.framework.module.Module;
import at.arz.latte.framework.module.SubMenu;
import at.arz.latte.framework.persistence.beans.InitializationBean;
import at.arz.latte.framework.restapi.MenuData;
import at.arz.latte.framework.restapi.ModuleData;
import at.arz.latte.framework.restapi.SubMenuData;

/**
 * bean for framework management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class FrameworkManagementBean {

	@PersistenceContext(unitName = FrameworkConstants.JPA_UNIT)
	private EntityManager em;

	/**
	 * get all modules with their menus for REST-service
	 * 
	 * @param permissions
	 * @return
	 */
	public List<ModuleData> getAll(Collection<String> permissions) {
		List<ModuleData> modulesData = new ArrayList<>();

		// get modules from database
		List<Module> modules = em.createNamedQuery(	Module.QUERY_GETALL_ENABLED_SORTED,
													Module.class)
									.getResultList();
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
		if (permissions.contains("admin")) {
			ModuleData moduleData = new ModuleData();
			moduleData.setId(0L);
			moduleData.setRunning(true);
			moduleData.setMenu(getMenuData(	InitializationBean.ADMIN_MENU,
											permissions));

			// sort admin menu
			boolean added = false;
			int adminMenuOrder = InitializationBean.ADMIN_MENU.getOrder();
			for (int i = 0; i < modules.size(); i++) {
				Menu menu = modules.get(i).getMenu();
				if (menu.getOrder() > adminMenuOrder) {
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
	private MenuData getMenuData(Menu menu, Collection<String> permissions) {

		// ignore module if user has no permission
		if (menu.getPermission() != null && !permissions.contains(menu.getPermission()
																		.getName())) {
			return null;
		}

		// main menu entry
		MenuData menuData = new MenuData(menu.getName(), menu.getUrl());

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
	private SubMenuData getSubMenuDataRec(	SubMenu menu,
											Collection<String> permissions) {

		// ignore submenu if user has no permission
		if (menu.getPermission() != null && !permissions.contains(menu.getPermission()
																		.getName())) {
			return null;
		}

		SubMenuData subMenuData = new SubMenuData(	menu.getName(),
													menu.getUrl(),
													menu.getScript(),
													menu.getType(),
													menu.getGroup(),
													menu.getDisabled());

		// recursive add sub menus
		if (menu.getSubMenus() != null && !menu.getSubMenus().isEmpty()) {
			for (SubMenu subMenu : menu.getSubMenus()) {
				subMenuData.addSubMenu(getSubMenuDataRec(subMenu, permissions));
			}
		}

		return subMenuData;
	}

}
