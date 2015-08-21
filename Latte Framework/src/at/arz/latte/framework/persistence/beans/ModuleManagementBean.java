package at.arz.latte.framework.persistence.beans;

import java.util.List;

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
public class ModuleManagementBean extends GenericManagementBean<Module> {

	@PersistenceContext
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

	public Module updateModule(Module module) {
		Module m = getModule(module.getId());
		m.setName(module.getName());
		m.setProvider(module.getProvider());
		m.setUrl(module.getUrl());
		m.setInterval(module.getInterval());
		m.setEnabled(module.getEnabled());
		
		if (validate(m)) {
			em.merge(m);
		}

		return m;
	}

	public void deleteModule(Long moduleId) {
		em.remove(getModule(moduleId));

	}

}