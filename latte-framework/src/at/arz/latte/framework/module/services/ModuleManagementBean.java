package at.arz.latte.framework.module.services;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.FrameworkConstants;
import at.arz.latte.framework.admin.Permission;
import at.arz.latte.framework.module.Menu;
import at.arz.latte.framework.module.Module;
import at.arz.latte.framework.module.ModuleQuery;
import at.arz.latte.framework.module.SubMenu;
import at.arz.latte.framework.restapi.ModuleData;
import at.arz.latte.framework.util.Functions;

/**
 * bean for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class ModuleManagementBean {

	@PersistenceContext(unitName = FrameworkConstants.JPA_UNIT)
	private EntityManager em;

	private static final Function<Module, ModuleData> MAP_TO_MODULEDATA = new Function<Module, ModuleData>() {
		@Override
		public ModuleData apply(Module module) {
			return new ModuleData(	module.getId(),
									module.getName(),
									module.getProvider(),
									module.getRunning(),
									module.getEnabled(),
									module.getLastModified());
		}
	};

	public List<ModuleData> getAllModulesData() {
		return Functions.map(MAP_TO_MODULEDATA, new ModuleQuery(em).all());
	}

	public List<Module> getAllModules() {
		return em.createNamedQuery(Module.QUERY_GETALL, Module.class)
					.getResultList();
	}

	public List<Module> getAllEnabledModules() {
		return em.createNamedQuery(Module.QUERY_GETALL_ENABLED, Module.class)
					.getResultList();
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
	public Module updateModule(	Long id,
								String name,
								String provider,
								String url,
								int interval,
								boolean enabled,
								Boolean running) {
		Module module = getModule(id);
		module.setName(name);
		module.setProvider(provider);
		module.setUrl(url);
		module.setInterval(interval);
		module.setEnabled(enabled);
		if (running != null) {
			module.setRunning(running);
		}
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

		// find permission in db
		HashMap<String, Permission> storedPermissions = new HashMap<>();
		List<Permission> permissions = em.createNamedQuery(	Permission.QUERY_GETALL,
															Permission.class)
											.getResultList();
		for (Permission p : permissions) {
			storedPermissions.put(p.getName(), p);
		}

		Module module = updateModuleRunning(moduleId, true);
		module.setLastModified(menu.getLastModified());

		// update menu entry
		module.setMenu(menu);

		// update permissions
		getMenuPermissionsRec(module, storedPermissions);

		return module;
	}

	private void
			getMenuPermissionsRec(	Module module,
									HashMap<String, Permission> storedPermissions) {

		Menu menu = module.getMenu();
		Permission p = menu.getPermission();
		if (p != null) {

			if (storedPermissions.containsKey(p.getName())) {
				menu.setPermission(storedPermissions.get(p.getName()));
			} else {
				storedPermissions.put(p.getName(), p);
				em.persist(p);
				menu.setPermission(p);
			}
		}

		// get recursive submenu permissions
		getSubMenuPermissionsRec(module, menu.getSubMenus(), storedPermissions);
	}

	private void
			getSubMenuPermissionsRec(	Module module,
										List<SubMenu> subMenus,
										HashMap<String, Permission> storedPermissions) {

		for (SubMenu menu : subMenus) {

			Permission p = menu.getPermission();
			if (p != null) {

				if (storedPermissions.containsKey(p.getName())) {
					menu.setPermission(storedPermissions.get(p.getName()));
				} else {
					storedPermissions.put(p.getName(), p);
					em.persist(p);
					menu.setPermission(p);
				}
			}

			// get recursive submenu permissions
			getSubMenuPermissionsRec(	module,
										menu.getSubMenus(),
										storedPermissions);
		}
	}

	public void deleteModule(Long moduleId) {
		Module toBeDeleted = getModule(moduleId);
		em.remove(toBeDeleted);
	}

}