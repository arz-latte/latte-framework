package at.arz.latte.framework.admin.restapi;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import at.arz.latte.framework.FrameworkConstants;
import at.arz.latte.framework.admin.AdminQuery;
import at.arz.latte.framework.exceptions.LatteValidationException;
import at.arz.latte.framework.module.Module;
import at.arz.latte.framework.restapi.ModuleData;
import at.arz.latte.framework.util.Functions;

/**
 * RESTful service for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
@Path("modules")
public class ModuleAdministration {

	@PersistenceContext(unitName = FrameworkConstants.JPA_UNIT)
	private EntityManager em;

	@Inject
	private Validator validator;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleData> allModules() {
		return Functions.map(	AdminMapper.MAP_TO_MODULEDATA,
								new AdminQuery(em).allModules());
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleData moduleWithId(@PathParam("id") Long id) {
		Module module = em.find(Module.class, id);
		return AdminMapper.MAP_TO_MODULEDATA.apply(module);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleData createModule(ModuleData moduleData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(moduleData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		Module module = new ModuleEditor(em).createModule(moduleData);
		return AdminMapper.MAP_TO_MODULEDATA.apply(module);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleData updateModule(ModuleData moduleData) {
		
		Set<ConstraintViolation<Object>> violations = requestValidation(moduleData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}
		
		Module module = new ModuleEditor(em).updateModule(moduleData);		
		return AdminMapper.MAP_TO_MODULEDATA.apply(module);
	}

	@DELETE
	@Path("{id}")
	public void deleteModule(@PathParam("id") Long id) {
		new ModuleEditor(em).deleteModule(id);
	}

	private Set<ConstraintViolation<Object>>
			requestValidation(Object moduleData) {
		return validator.validate(moduleData);
	}

}