package at.arz.latte.framework.restapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
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
import at.arz.latte.framework.admin.Group;
import at.arz.latte.framework.admin.restapi.AdminMapper;
import at.arz.latte.framework.authorization.LatteAuthorization;
import at.arz.latte.framework.exceptions.UserNotFound;
import at.arz.latte.framework.module.Menu;
import at.arz.latte.framework.module.Module;
import at.arz.latte.framework.module.SubMenu;
import at.arz.latte.framework.module.services.FrameworkInitialization;
import at.arz.latte.framework.util.Functions;
import at.arz.latte.framework.util.JPA;
import at.arz.latte.framework.websockets.WebsocketEndpoint;
import at.arz.latte.framework.websockets.WebsocketMessage;

/**
 * RESTful service for framework management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Path("framework")
public class FrameworkService {

	@Inject
	private LatteAuthorization authorization;

	@PersistenceContext(unitName = FrameworkConstants.JPA_UNIT)
	private EntityManager em;

	@EJB
	private WebsocketEndpoint websocket;

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

		// get modules from database
		List<Module> modules = em	.createNamedQuery(Module.QUERY_GETALL_ENABLED_SORTED,
													Module.class)
									.getResultList();

		for (Module module : modules) {
			ModuleData moduleData = new ModuleData();
			moduleData.setId(module.getId());
			moduleData.setRunning(module.getRunning());

			moduleData.setMenu(getMenuData(	module.getMenu(),
											authorization.getPermissions()));

			// ignore module if user has no permission to submenus
			if (moduleData.getMenu() != null) {
				modulesData.add(moduleData);
			}
		}

		// load and sort administration menu if user has "admin"-permission
		if (authorization.getPermissions().contains("admin")) {
			ModuleData moduleData = new ModuleData();
			moduleData.setId(0L);
			moduleData.setRunning(true);
			moduleData.setMenu(getMenuData(	FrameworkInitialization.ADMIN_MENU,
											authorization.getPermissions()));

			// sort admin menu
			boolean added = false;
			int adminMenuOrder = FrameworkInitialization.ADMIN_MENU.getOrder();
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
		if (menu.getPermission() != null
			&& !permissions.contains(menu.getPermission().getName())) {
			return null;
		}

		// main menu entry
		MenuData menuData = new MenuData(	menu.getName(),
											menu.getUrl(),
											menu.getStyle());

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
		if (menu.getPermission() != null
			&& !permissions.contains(menu.getPermission().getName())) {
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
			for (SubMenu subMenu : menu.getSubMenus()) {
				subMenuData.addSubMenu(getSubMenuDataRec(subMenu, permissions));
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