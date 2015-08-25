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
	 * 
	 * @return
	 */
	public List<ModuleMenuData> getAll() {
		List<Module> modules = em.createQuery("SELECT m FROM Module m WHERE m.enabled=true", Module.class)
				.getResultList();
		
		// menu
		List<ModuleMenuData> modulesMenuData = new ArrayList<>();

		for (Module module : modules) {

			// main menu for single module
			
			MenuEntryData med = module.getMainMenu().getEntry().toMenuEntryData();
			
			MenuLeafData mainMenuData = new MenuLeafData(med);

			// sub menu for single module
			List<MenuRootData> subMenuData = new ArrayList<>();
			for (MenuRoot m : module.getSubMenu()) {
				// top level entry (root)
				MenuEntryData red = m.getEntry().toMenuEntryData();

				List<MenuLeafData> leafs = new ArrayList<>();
				for (MenuLeaf l : m.getSubmenu()) {
					// bottom level entry (leaf)
					med = l.getEntry().toMenuEntryData();
					MenuLeafData leaf = new MenuLeafData(med);
					leafs.add(leaf);
				}

				MenuRootData root = new MenuRootData(red, leafs);
				subMenuData.add(root);
			}

			ModuleMenuData moduleData = new ModuleMenuData(module.getId(), mainMenuData, subMenuData);
			modulesMenuData.add(moduleData);
		}

		return modulesMenuData;
	}

}