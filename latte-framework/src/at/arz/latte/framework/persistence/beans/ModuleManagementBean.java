package at.arz.latte.framework.persistence.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Menu;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.persistence.models.Role;
import at.arz.latte.framework.persistence.models.SubMenu;
import at.arz.latte.framework.restful.dta.ModuleData;
import at.arz.latte.framework.services.restful.LatteValidationException;

/**
 * bean for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class ModuleManagementBean {

	@PersistenceContext(unitName = "latte-unit")
	private EntityManager em;

	public void initAllModules() {
		em.createNamedQuery(Module.STOP_ALL).executeUpdate();
	}

	public List<ModuleData> getAllModulesData() {
		return em.createNamedQuery(Module.QUERY_GETALL_BASE, ModuleData.class).getResultList();
	}

	public List<Module> getAllModules() {
		return em.createNamedQuery(Module.QUERY_GETALL, Module.class).getResultList();
	}

	public List<Module> getAllEnabledModules() {
		return em.createNamedQuery(Module.QUERY_GETALL_ENABLED, Module.class).getResultList();
	}

	public Module getModule(Long moduleId) {
		return em.find(Module.class, moduleId);
	}

	public Module createModule(Module module) {
		em.persist(module);
		return module;
	}

	/**
	 * update via REST-service
	 * 
	 * @param id
	 * @param name
	 * @param provider
	 * @param url
	 * @param interval
	 * @param enabled
	 * @param running
	 * @return
	 */
	public Module updateModule(Long id, String name, String provider, String url, int interval, boolean enabled,
			boolean running) {
		Module module = getModule(id);
		module.setName(name);
		module.setProvider(provider);
		module.setUrl(url);
		module.setInterval(interval);
		module.setEnabled(enabled);
		module.setRunning(running);
		return module;
	}

	/**
	 * update module via REST-service, only set running value
	 * 
	 * @param moduleId
	 * @param running
	 * @return
	 */
	public Module updateModuleRunning(Long moduleId, boolean running) {
		Module module = getModule(moduleId);
		module.setRunning(running);
		return module;
	}

	/**
	 * update menu of module via REST-service
	 * 
	 * @param moduleId
	 * @param menu
	 * @return
	 */
	public Module updateModuleMenu(Long moduleId, Menu menu) {

		Module module = updateModuleRunning(moduleId, true);

		module.setLastModified(menu.getLastModified());
		module.setMenu(menu);

		return module;
	}

	public void deleteModule(Long moduleId) {
		Module toBeDeleted = getModule(moduleId);
		em.remove(toBeDeleted);
	}
	
	/**
	 * store permissions of module via REST-service
	 * 
	 * @param menu
	 */
	public void storeModulePermissions(Menu menu) {

		Set<String> permissions = new HashSet<>();
		
		// permission from mainmenu
		if (menu.getPermission() != null) {
			permissions.add(menu.getPermission());
		}
		
		// permissions from submenu
		extractPermissionsRec(menu.getSubMenus(), permissions);
				
		System.out.println(permissions);

		// store all permissions (ignore duplicates)
		for (String permission : permissions) {

			// check if permission already exists
			List<Permission> duplicates = em.createNamedQuery(Permission.QUERY_GET_BY_NAME, Permission.class)
					.setParameter("name", permission).getResultList();

			if (duplicates.isEmpty()) {
				em.persist(new Permission(permission));
			}
		}
	}

	/**
	 * extract permissions from menu
	 * 
	 * @param menus
	 * @param permissions
	 */
	private Set<String> extractPermissionsRec(List<SubMenu> menus, Set<String> permissions) {
		if (menus != null) {

			for (SubMenu submenu : menus) {
				String permission = submenu.getPermission();
				if (permission != null) {
					permissions.add(permission);
				}
				extractPermissionsRec(submenu.getSubMenus(), permissions);
			}
		}

		return permissions;
	}

}