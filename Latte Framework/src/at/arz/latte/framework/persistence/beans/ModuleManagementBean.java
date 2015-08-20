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

	public List<ModuleBaseData> getAllModules() {
		return em.createNamedQuery(Module.QUERY_GETALL, ModuleBaseData.class).getResultList();
	}

	public Module getModule(int moduleId) {
		return em.find(Module.class, moduleId);
	}

	public Module createModule(Module module) {
		validate(module);

		if (module.isSaveable()) {
			em.persist(module);
		}

		return module;
	}

	public Module updateModule(Module module) {
		validate(module);

		if (module.isSaveable()) {
			em.merge(module);
		}

		return module;
	}

	public void deleteModule(int moduleId) {
		em.remove(getModule(moduleId));
	
	}

}