package at.arz.latte.framework.services.restful.admin;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.persistence.beans.ModuleManagementBean;
import at.arz.latte.framework.persistence.beans.UserManagementBean;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.restful.dta.ModuleData;
import at.arz.latte.framework.services.restful.exception.LatteValidationException;

/**
 * RESTful service for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@RequestScoped
@Path("modules")
public class ModuleService {

	@EJB
	private ModuleManagementBean bean;

	@EJB
	private UserManagementBean userBean;

	@Inject
	private Validator validator;

	@GET
	@Path("all.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleData> getAllModules() {
		return bean.getAllModulesData();
	}

	@GET
	@Path("get.json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleData getModule(@PathParam("id") Long id) {
		Module module = bean.getModule(id);
		return toModuleData(module);
	}

	@POST
	@Path("create.json")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleData createModule(ModuleData moduleData) {
		Set<ConstraintViolation<Object>> violations = requestValidation(moduleData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		Module module = new Module(moduleData.getName(), moduleData.getProvider(), moduleData.getUrl(),
				moduleData.getInterval(), moduleData.getEnabled());

		return toModuleData(bean.createModule(module));
	}

	@PUT
	@Path("update.json")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleData updateModule(ModuleData moduleData) {
		Set<ConstraintViolation<Object>> violations = requestValidation(moduleData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		Module after = bean.updateModule(moduleData.getId(), moduleData.getName(), moduleData.getProvider(),
				moduleData.getUrl(), moduleData.getInterval(), moduleData.getEnabled(),
				!moduleData.getEnabled() ? false : null);

		return toModuleData(after);
	}

	@DELETE
	@Path("delete.json/{id}")
	public void deleteModule(@PathParam("id") Long moduleId) {
		bean.deleteModule(moduleId);
	}

	private Set<ConstraintViolation<Object>> requestValidation(Object moduleData) {
		return validator.validate(moduleData);
	}

	/**
	 * helper for REST service
	 * 
	 * @param moduleData
	 * @return
	 */
	public ModuleData toModuleData(Module module) {
		ModuleData moduleData = new ModuleData();
		moduleData.setId(module.getId());
		moduleData.setName(module.getName());
		moduleData.setProvider(module.getProvider());
		moduleData.setUrl(module.getUrl());
		moduleData.setInterval(module.getInterval());
		moduleData.setEnabled(module.getEnabled());
		return moduleData;
	}
}