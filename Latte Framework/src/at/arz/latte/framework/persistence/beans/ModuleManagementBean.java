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

import at.arz.latte.framework.modules.models.Module;

@Stateful
public class ModuleManagementBean {

	@PersistenceContext
	private EntityManager em;

	public List<Module> getAllModules() {
		return em.createQuery("SELECT m FROM Module m", Module.class)
				.getResultList();
	}

	public Module getModule(int moduleId) {
		//return em.find(Module.class, moduleId);
		return new Module();
	}

	public Module createModule(Module module) {
		em.persist(module);
		return module;
	}

	public Module updateModule(Module module) {
		Module result = em.merge(module);

		return result;
	}

	public void deleteModule(int moduleId) {
		em.remove(getModule(moduleId));
	}

	private Module validate(Module module) {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();
		Set<ConstraintViolation<Module>> constraintViolations = validator
				.validate(module);

		if (constraintViolations.size() > 0) {
			System.out.println("---------------------------------------");
			HashMap<String, String> violationMessages = new HashMap<String, String>();

			for (ConstraintViolation<Module> cv : constraintViolations) {
				violationMessages.put(cv.getPropertyPath().toString(),
						cv.getMessage());

				System.out.println(cv.getPropertyPath() + ": "
						+ cv.getMessage());
			}

			System.out.println("---------------------------------------");
		}
		return module;
	}

}