package at.arz.latte.framework.persistence.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Module;

@Stateless
public class ModuleBean {

	@PersistenceContext
	private EntityManager em;

	public List<Module> getAllModulesFull() {
		return em.createQuery("SELECT m FROM Module m", Module.class)
				.getResultList();
	}

	public Module getModule(int moduleId) {
		return em.find(Module.class, moduleId);
	}

	public Module createModule(Module module) {
		em.persist(module);
		return module;
	}

	public Module updateCustomer(Module module) {
		Module result = em.merge(module);

		return result;
	}

	public void deleteModule(int moduleId) {
		em.remove(getModule(moduleId));
	}
}