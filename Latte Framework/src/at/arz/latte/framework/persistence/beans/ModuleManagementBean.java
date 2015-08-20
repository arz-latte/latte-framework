package at.arz.latte.framework.persistence.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import at.arz.latte.framework.modules.dta.ModuleBaseData;
import at.arz.latte.framework.modules.dta.ModuleFullData;
import at.arz.latte.framework.modules.dta.ResultData;
import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;

@Stateful
public class ModuleManagementBean {

	@PersistenceContext
	private EntityManager em;

	public List<ModuleBaseData> getAllModules() {
		return em.createNamedQuery(Module.QUERY_GETALL, ModuleBaseData.class).getResultList();
	}

	public Module getModule(int moduleId) {
		return em.find(Module.class, moduleId);
	}

	public ResultData createModule(Module module) {
		ResultData resp = validate(module);
		if (resp.isSaveable()) {
			em.persist(module);
			resp.setId(module.getId());
			System.out.println("id: " + module.getId());
		}

		return resp;
	}

	public ResultData updateModule(Module module) {
		ResultData resp = validate(module);
		if (resp.isSaveable()) {
			em.merge(module);
			resp.setId(module.getId());
			System.out.println("id: " + module.getId());
		}

		return resp;
	}

	public void deleteModule(int moduleId) {
		em.remove(getModule(moduleId));
	}

	private ResultData validate(Module module) {
		ResultData result = new ResultData();

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Module>> constraintViolations = validator.validate(module);

		if (constraintViolations.size() > 0) {
			HashMap<String, String> violationMessages = new HashMap<String, String>();

			for (ConstraintViolation<Module> cv : constraintViolations) {
				violationMessages.put(cv.getPropertyPath().toString(), cv.getMessage());

				System.out.println(cv.getPropertyPath() + ": " + cv.getMessage());
			}

			result.setValidation(violationMessages);
		}

		return result;
	}

}