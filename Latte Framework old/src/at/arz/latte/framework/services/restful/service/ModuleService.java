package at.arz.latte.framework.services.restful.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;
import at.arz.latte.framework.persistence.beans.ModuleManagementBean;
import at.arz.latte.framework.persistence.beans.MyValidationException;
import at.arz.latte.framework.persistence.beans.ValidateBean;

@RequestScoped
@Path("modules")
// @Stateless
public class ModuleService {

	@EJB
	private ModuleManagementBean bean;

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Module> getAllModules() {
		return bean.getAllModules();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Module getModule(@PathParam("id") int id) {
		return new Module();
		//return bean.getModule(id);
	}

	// 201
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	public Module createModule(Module module) {
		//ValidateBean.validate(module);
		//return null;
		return bean.createModule(module);
	}

	@PUT
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Module> updateModule(Module module) {
		return Arrays.asList(bean.updateModule(module));
	}

	@DELETE
	@Path("delete/{id}")
	public void deleteModule(@PathParam("id") int moduleId) {
		bean.deleteModule(moduleId);
	}
}