package at.arz.latte.framework.persistence.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.restful.dta.ModuleData;

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
	 * @return
	 */
	public List<ModuleData> getAll() {
		List<Module> modules = em.createNamedQuery(Module.QUERY_GETALL_ENABLED_SORTED, Module.class).getResultList();
		
		List<ModuleData> modulesData = new ArrayList<>();

		for (Module module : modules) {

			ModuleData moduleData = new ModuleData();
			moduleData.setId(module.getId());
			moduleData.setMenu(module.getMenu().getMenuData());

			modulesData.add(moduleData);
		}

		return modulesData;
	}

}