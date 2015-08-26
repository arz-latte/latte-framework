package at.arz.latte.framework.persistence.beans;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import at.arz.latte.framework.modules.dta.ModuleData;
import at.arz.latte.framework.modules.dta.ModuleListData;
import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;

/**
 * bean for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class ModuleManagementBean {

	@PersistenceContext(unitName="latte-unit")
	private EntityManager em;
	
	@Inject
	private Validator validator;

	public void initAllModules() {
		em.createNamedQuery(Module.UPDATE_ALL).executeUpdate();
	}

	public List<ModuleListData> getAllModulesBase() {
		return em.createNamedQuery(Module.QUERY_GETALL_BASE, ModuleListData.class).getResultList();
	}

	public List<Module> getAllModules() {
		return em.createNamedQuery(Module.QUERY_GETALL, Module.class).getResultList();
	}

	public Module getModule(Long moduleId) {
		return em.find(Module.class, moduleId);
	}

	/**
	 * used for creation via REST-service
	 */
	public Module createModule(Module module) {	
		em.persist(module);
		return module;
	}
	
	/**
	 * used for partial updates via timer-service
	 */
	public Module updateModule(Module module) {		
		em.merge(module);
		return module;
	}

	/**
	 * used for partial updates via REST-service
	 */
	public Module updateModule(ModuleData moduleData) {
		Module module = getModule(moduleData.getId());

		module.setName(moduleData.getName());
		module.setProvider(moduleData.getProvider());
		module.setUrl(moduleData.getUrl().toString());
		module.setInterval(moduleData.getInterval());
		module.setEnabled(moduleData.getEnabled());
		
		// set status to stopped if module gets disabled
		if (!module.getEnabled()) {
			module.setStatus(ModuleStatus.Stopped);
		}

		return module;
	}

	public void deleteModule(Long moduleId) {
		Module toBeDeleted = getModule(moduleId);
		em.remove(toBeDeleted);
	}

}