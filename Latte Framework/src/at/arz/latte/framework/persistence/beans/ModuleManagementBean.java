package at.arz.latte.framework.persistence.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
public class ModuleManagementBean extends GenericManagementBean<Module> {

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
	public Module updateModule(ModuleData moduleData) {
		Set<ConstraintViolation<Object>> violations = requestValidation(moduleData);
		if(!violations.isEmpty()){
			throw new LatteValidationException("invalid moduleData");
		}
		System.out.println("update module ");
		Module module = getModule(moduleData.getId());

		module.setName(moduleData.getName());
		module.setProvider(moduleData.getProvider());
		module.setUrl(moduleData.getUrl());
		module.setInterval(moduleData.getInterval());
		module.setEnabled(moduleData.getEnabled());
		
		// set status to stopped if module gets disabled
		if (!module.getEnabled()) {
			module.setStatus(ModuleStatus.Stopped);
		}
		
//		if (validate(module)) {
//			em.merge(module);
//		}

		return module;
	}

	private Set<ConstraintViolation<Object>> requestValidation(Object moduleData) {
		return validator.validate(moduleData);
	}

	public void deleteModule(Long moduleId) {
		Module toBeDeleted = getModule(moduleId);
		em.remove(toBeDeleted);

	}

	public void sample() {
		throw new LatteValidationException("something happened");
	}

}