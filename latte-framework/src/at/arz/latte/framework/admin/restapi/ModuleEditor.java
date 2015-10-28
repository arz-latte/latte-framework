package at.arz.latte.framework.admin.restapi;

import java.util.Objects;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

import at.arz.latte.framework.admin.AdminQuery;
import at.arz.latte.framework.exceptions.LatteValidationException;
import at.arz.latte.framework.module.Module;
import at.arz.latte.framework.restapi.ModuleData;

/**
 * bean for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
class ModuleEditor {
	private static final Logger LOG = Logger.getLogger(ModuleEditor.class.getSimpleName());

	private EntityManager em;

	public ModuleEditor(EntityManager em) {
		this.em = Objects.requireNonNull(em);
	}

	public Module createModule(ModuleData moduleData) {

		assertNameIsNotOwnedByAnotherModule(moduleData.getName());
		assertUrlIsNotOwnedByAnotherModule(moduleData.getUrl());

		Module module = new Module();
		module.setName(moduleData.getName());
		module.setProvider(moduleData.getProvider());
		module.setUrl(moduleData.getUrl());
		module.setInterval(moduleData.getInterval());
		module.setEnabled(moduleData.getEnabled());
		module.setRunning(false);

		em.persist(module);

		return module;
	}

	public Module updateModule(ModuleData moduleData) {

		Module module = em.find(Module.class, moduleData.getId());
		module.setProvider(moduleData.getProvider());
		module.setUrl(moduleData.getUrl());
		module.setInterval(moduleData.getInterval());
		if (moduleData.getEnabled() != null) {
			module.setEnabled(moduleData.getEnabled());
		}

		if (!moduleData.getEnabled()) {
			module.setRunning(false);
		}

		if (!Objects.equals(module.getName(), moduleData.getName())) {
			assertNameIsNotOwnedByAnotherModule(moduleData.getName());
			module.setName(moduleData.getName());
		}

		if (!Objects.equals(module.getUrl(), moduleData.getUrl())) {
			assertUrlIsNotOwnedByAnotherModule(moduleData.getUrl());
			module.setUrl(moduleData.getUrl());
		}

		return module;
	}

	public void deleteModule(Long id) {
		try {
			Module module = em.find(Module.class, id);
			em.remove(module);
			LOG.info("deleteModule: module deleted:" + module.getName());
		} catch (EntityNotFoundException e) {
			LOG.info("deleteModule: module not found:" + id);
		}
	}

	/**
	 * check if name is already stored, throws LatteValidationException if
	 * module is a duplicate
	 * 
	 * @param email
	 * @throws LatteValidationException
	 */
	private void assertNameIsNotOwnedByAnotherModule(String name) {
		TypedQuery<Module> query = new AdminQuery(em).moduleByName(name);
		if (!query.getResultList().isEmpty()) {
			LOG.info("can't store module, name already exists:" + name);
			throw new LatteValidationException(	400,
												"name",
												"Wird bereits von einem anderen Modul verwendet.");
		}
	}

	/**
	 * check if url is already stored, throws LatteValidationException if module
	 * is a duplicate
	 * 
	 * @param email
	 * @throws LatteValidationException
	 */
	private void assertUrlIsNotOwnedByAnotherModule(String url) {
		TypedQuery<Module> query = new AdminQuery(em).moduleByUrl(url);
		if (!query.getResultList().isEmpty()) {
			LOG.info("can't store module, url already exists:" + url);
			throw new LatteValidationException(	400,
												"url",
												"Wird bereits von einem anderen Modul verwendet.");
		}
	}

}