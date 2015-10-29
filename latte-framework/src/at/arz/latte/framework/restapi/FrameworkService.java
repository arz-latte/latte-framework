package at.arz.latte.framework.restapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.FrameworkConstants;
import at.arz.latte.framework.admin.AdminQuery;
import at.arz.latte.framework.admin.restapi.AdminMapper;
import at.arz.latte.framework.authorization.LatteAuthorization;
import at.arz.latte.framework.exceptions.UserNotFound;
import at.arz.latte.framework.module.Module;
import at.arz.latte.framework.module.services.FrameworkNavigation;
import at.arz.latte.framework.util.Functions;
import at.arz.latte.framework.websockets.WebsocketEndpoint;
import at.arz.latte.framework.websockets.WebsocketMessage;

/**
 * RESTful service for framework management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
@Path("framework")
public class FrameworkService {

	@Inject
	private LatteAuthorization authorization;

	@PersistenceContext(unitName = FrameworkConstants.JPA_UNIT)
	private EntityManager em;

	@EJB
	private WebsocketEndpoint websocket;

	@Inject
	private FrameworkNavigation frameworkNavigation;

	/**
	 * get informations of currently logged in user
	 * 
	 * @return
	 */
	@GET
	@Path("user.json")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData getUserData() {
		String userName = authorization.getPrincipal().getName();
		List<UserData> list = Functions.map(AdminMapper.MAP_TO_USERDATA,
											new AdminQuery(em).userByEmail(userName));
		if (list.isEmpty()) {
			throw new UserNotFound(userName);
		}
		return list.get(0);
	}

	/**
	 * get initialization data of all modules
	 * 
	 * @return
	 */
	@GET
	@Path("init.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleData> getInitData() {

		List<ModuleData> modulesData = new ArrayList<>();

		// load administration menu if user has "admin"-permission
		if (authorization.getPermissions().contains("admin")) {
			ModuleData moduleData = new ModuleData(0L, true);

			MenuData menuData = frameworkNavigation.get(0L);

			moduleData.setMenu(extractMenuData(	menuData,
												authorization.getPermissions()));
			modulesData.add(moduleData);
		}

		// get modules from database
		List<Module> modules = em	.createNamedQuery(Module.QUERY_GETALL_ENABLED,
													Module.class)
									.getResultList();

		for (Module module : modules) {
			ModuleData moduleData = new ModuleData(	module.getId(),
													module.getRunning());

			MenuData menuData = frameworkNavigation.get(module.getId());

			// check permissions for menu
			moduleData.setMenu(extractMenuData(	menuData,
												authorization.getPermissions()));

			// only add module if user has permission for at least one submenus
			if (moduleData.getMenu() != null) {
				modulesData.add(moduleData);
			}
		}

		Collections.sort(modulesData);

		// remove order attributes
		for (ModuleData module : modulesData) {
			module.getMenu().setOrder(null);
		}

		return modulesData;
	}

	/**
	 * process menu data and check permissions
	 * 
	 * @param menu
	 * @return menu structure or null if permission for all submenus is missing
	 */
	private MenuData extractMenuData(	MenuData menu,
										Collection<String> permissions) {

		if (menu == null) {
			return null;
		}

		// ignore module if user has no permission
		if (menu.getPermission() != null
			&& !permissions.contains(menu.getPermission())) {
			return null;
		}

		// remove unnecessary properties
		MenuData menuData = new MenuData(	menu.getName(),
											menu.getUrl(),
											menu.getOrder(),
											menu.getStyle());

		// sub menu entries
		if (menu.getSubMenus() != null && !menu.getSubMenus().isEmpty()) {
			for (SubMenuData subMenu : menu.getSubMenus()) {
				menuData.addSubMenu(extractSubMenuDataRec(	subMenu,
															permissions));
			}

			// ignore module if user has no permission for any submenu
			if (menuData.getSubMenus().isEmpty()) {
				return null;
			}
		}

		return menuData;
	}

	/**
	 * recursive process submenu data and check permissions
	 * 
	 * @param menu
	 * @return
	 */
	private	SubMenuData
			extractSubMenuDataRec(	SubMenuData menu,
									Collection<String> permissions) {

		// ignore submenu if user has no permission
		if (menu.getPermission() != null
			&& !permissions.contains(menu.getPermission())) {
			return null;
		}

		SubMenuData subMenuData = new SubMenuData(	menu.getName(),
													menu.getUrl(),
													menu.getScript(),
													menu.getType(),
													menu.getGroup(),
													menu.getStyle(),
													menu.getDisabled());

		// recursive add sub menus
		if (menu.getSubMenus() != null && !menu.getSubMenus().isEmpty()) {
			for (SubMenuData subMenu : menu.getSubMenus()) {
				subMenuData.addSubMenu(extractSubMenuDataRec(	subMenu,
																permissions));
			}
		}

		return subMenuData;
	}

	/**
	 * notify all clients about something via websocket
	 * 
	 * @return
	 */
	@POST
	@Path("notify.json")
	public void notifyClients(String moduleId) {
		websocket.chat(new WebsocketMessage("notify", moduleId));
	}

}