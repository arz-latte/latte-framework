package at.arz.latte.framework.persistence.beans;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.modules.dta.ModuleBaseData;
import at.arz.latte.framework.modules.models.Module;

@Stateful
public class ModuleManagementBean extends GenericManagementBean<Module> {

	@PersistenceContext
	private EntityManager em;

	public List<ModuleBaseData> getAllModulesBase() {
		return em.createNamedQuery(Module.QUERY_GETALL_BASE, ModuleBaseData.class).getResultList();
	}

	public List<Module> getAllModules() {
		return em.createNamedQuery(Module.QUERY_GETALL, Module.class).getResultList();
	}

	public Module getModule(int moduleId) {
		return em.find(Module.class, moduleId);
	}

	public Module createModule(Module module) {
		if (validate(module)) {
			em.persist(module);
		}

		return module;
	}

	public Module updateModule(Module module) {
		if (validate(module)) {
			em.merge(module);
		}

		return module;
	}

	public void deleteModule(int moduleId) {
		em.remove(getModule(moduleId));

	}

}