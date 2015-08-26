package at.arz.latte.framework.services.restful;

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

import at.arz.latte.framework.modules.dta.ModuleData;
import at.arz.latte.framework.modules.dta.ModuleListData;
import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.persistence.beans.LatteValidationException;
import at.arz.latte.framework.persistence.beans.ModuleManagementBean;
import at.arz.latte.framework.websockets.WebsocketEndpoint;
import at.arz.latte.framework.websockets.models.WebsocketMessage;

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
	private WebsocketEndpoint websocket;
	
	@Inject
	private Validator validator;
	
	@GET
	@Path("all.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleListData> getAllModules() {
		return bean.getAllModulesBase();
	}

	@GET
	@Path("get.json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleData getModule(@PathParam("id") Long id) {
		return new ModuleData(bean.getModule(id));
	}

	@POST
	@Path("create.json")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleData createModule(ModuleData moduleData) {
				
		Set<ConstraintViolation<Object>> violations = requestValidation(moduleData);
		if(!violations.isEmpty()){
			throw new LatteValidationException(violations);
		}

		Module module = new Module(moduleData);	
		return new ModuleData(bean.createModule(module));
	}
	
	@PUT
	@Path("update.json")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleData updateModule(ModuleData moduleData) {
				
		Set<ConstraintViolation<Object>> violations = requestValidation(moduleData);
		if(!violations.isEmpty()){
			throw new LatteValidationException(violations);
		}	

		// notify clients if urlchanged or module was disabled
		Module module = bean.getModule(moduleData.getId());		
		boolean urlChanged = !module.getUrl().equals(moduleData.getUrl());
		boolean disabled = module.getEnabled() && !moduleData.getEnabled();
		if (urlChanged || disabled) {
			websocket.chat(new WebsocketMessage("update", "server"));
		}
		
		return new ModuleData(bean.updateModule(moduleData));
	}

	@DELETE
	@Path("delete.json/{id}")
	public void deleteModule(@PathParam("id") Long moduleId) {
		bean.deleteModule(moduleId);
	}
		
	private Set<ConstraintViolation<Object>> requestValidation(Object moduleData) {
		return validator.validate(moduleData);
	}
	
}