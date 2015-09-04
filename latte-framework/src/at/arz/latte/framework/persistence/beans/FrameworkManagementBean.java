package at.arz.latte.framework.persistence.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Menu;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.persistence.models.Permission;
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
	 * @param email
	 * @return
	 */
	public List<ModuleData> getAll(String email) {

		List<ModuleData> modulesData = new ArrayList<>();

		// get permissions of user
		List<String> permissions = em.createNamedQuery(Permission.QUERY_GET_NAME_BY_USER, String.class)
				.setParameter("email", email).getResultList();

		// get modules
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

		return modulesData;
	}

	/**
	 * convert menu entity to menu data for REST
	 * 
	 * @param menu
	 * @return menu structure or null if permission for all submenus is missing
	 */
	public MenuData getMenuData(Menu menu, List<String> permissions) {

		// ignore module if user has no permission
		if (menu.getPermission() != null && !permissions.contains(menu.getPermission())) {
			return null;
		}

		MenuData menuData = new MenuData(menu.getName(), menu.getUrl(), menu.getOrder(), menu.getSubOrder());

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
	public SubMenuData getSubMenuDataRec(SubMenu menu, List<String> permissions) {

		// ignore submenu if user has no permission
		if (menu.getPermission() != null && !permissions.contains(menu.getPermission())) {
			return null;
		}

		SubMenuData subMenuData = new SubMenuData(menu.getName(), menu.getUrl(), menu.getType(), menu.getGroup());

		// recursive add sub menus
		if (menu.getSubMenus() != null && !menu.getSubMenus().isEmpty()) {
			for (SubMenu subMenu : menu.getSubMenus()) {
				subMenuData.addSubMenu(getSubMenuDataRec(subMenu, permissions));
			}
		}

		return subMenuData;
	}

}