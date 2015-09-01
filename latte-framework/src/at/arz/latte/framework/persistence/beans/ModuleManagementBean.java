package at.arz.latte.framework.persistence.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Menu;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.persistence.models.SubMenu;
import at.arz.latte.framework.restful.dta.ModuleData;

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
	 * @param id
	 * @param name
	 * @param provider
	 * @param url
	 * @param interval
	 * @param enabled
	 * @param running
	 * @return
	 */
	public Module updateModule(Long id, String name, String provider, String url, int interval, boolean enabled, boolean running) {
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
	 * @param moduleId
	 * @param menu
	 * @return
	 */
	public Module updateModuleMenu(Long moduleId, Menu menu) {

		Module module = updateModuleRunning(moduleId, true);

		module.setLastModified(menu.getLastModified());
		module.setMenu(menu);
		
		// extract and store permissions
		storeRecPermissions(menu.getSubMenus());

		return module;
	}
	
	/**
	 * extract and store permissions
	 * @param menus
	 */
	private void storeRecPermissions(List<SubMenu> menus) {
		if (menus != null) {
		
			for(SubMenu submenu : menus) {
				System.out.println(submenu.getName() + ": " + submenu.getPermission());

				
				storeRecPermissions(submenu.getSubMenus());
			}
		}
	}

	public void deleteModule(Long moduleId) {
		Module toBeDeleted = getModule(moduleId);
		em.remove(toBeDeleted);
	}

}