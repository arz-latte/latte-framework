package at.arz.latte.framework.persistence.beans;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.modules.dta.ModuleMultipleData;
import at.arz.latte.framework.modules.models.Module;

/**
 * bean for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
// Todo: stateful?
public class ModuleManagementBean extends GenericManagementBean<Module> {

	@PersistenceContext(unitName="latte-unit")
	private EntityManager em;

	public void initAllModules() {
		em.createNamedQuery(Module.UPDATE_ALL).executeUpdate();
	}

	public List<ModuleMultipleData> getAllModulesBase() {
		return em.createNamedQuery(Module.QUERY_GETALL_BASE, ModuleMultipleData.class).getResultList();
	}

	public List<Module> getAllModules() {
		return em.createNamedQuery(Module.QUERY_GETALL, Module.class).getResultList();
	}

	public Module getModule(Long moduleId) {
		return em.find(Module.class, moduleId);
	}

	public Module createModule(Module module) {
		if (validate(module)) {
			em.persist(module);
		}

		return module;
	}
	
	/**
	 * used for partial updates via timer-service
	 */
	public Module updateModule(Module module) {
		if (validate(module)) {
			em.merge(module);
		}

		return module;
	}

	/**
	 * used for partial updates via REST-service
	 */
	public Module updateModule(Long id, String name, String provider, String url, int interval, boolean enabled) {
		Module m = getModule(id);
		
		m.setName(name);
		m.setProvider(provider);
		m.setUrl(url);
		m.setInterval(interval);
		m.setEnabled(enabled);
		
		if (validate(m)) {
			em.merge(m);
		}

		return m;
	}

	public void deleteModule(Long moduleId) {
		em.remove(getModule(moduleId));

	}

}