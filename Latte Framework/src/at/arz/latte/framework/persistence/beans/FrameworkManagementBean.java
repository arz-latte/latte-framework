package at.arz.latte.framework.persistence.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.modules.dta.MenuEntryData;
import at.arz.latte.framework.modules.dta.MenuLeafData;
import at.arz.latte.framework.modules.dta.MenuRootData;
import at.arz.latte.framework.modules.dta.ModuleMenuData;
import at.arz.latte.framework.modules.models.MenuLeaf;
import at.arz.latte.framework.modules.models.MenuRoot;
import at.arz.latte.framework.modules.models.Module;

/**
 * bean for framework management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class FrameworkManagementBean extends GenericManagementBean<Module> {

	@PersistenceContext(unitName = "latte-unit")
	private EntityManager em;

	/**
	 * get all modules with their menus for REST-service
	 * @return
	 */
	public List<ModuleMenuData> getAll() {
		List<Module> modules = em.createQuery("SELECT m FROM Module m WHERE m.enabled=true", Module.class).getResultList();
		System.out.println(modules);
		
		// menu
		List<ModuleMenuData> modulesData = new ArrayList<>();
		
		for(Module module : modules) {
			
			// menu for single module
			List<MenuRootData> menuData = new ArrayList<>();
			for(MenuRoot m : module.getSubmenu()) {
				MenuEntryData rootEntry = new MenuEntryData(m.getEntry());	// top level entry (root)
				
				List<MenuLeafData> leafs = new ArrayList<>();
				for(MenuLeaf l : m.getSubmenu()) {
					MenuEntryData leafEntry = new MenuEntryData(l.getEntry());	// bottom level entry (leaf)
					
					MenuLeafData leaf = new MenuLeafData(leafEntry);
					leafs.add(leaf);
				}
						
				MenuRootData root = new MenuRootData(rootEntry, leafs);
				menuData.add(root);
			}
			
			ModuleMenuData moduleData = new ModuleMenuData(module.getName(), menuData);
			modulesData.add(moduleData);
		}
		
		return modulesData;		
	}

}