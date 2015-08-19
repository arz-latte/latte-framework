package at.arz.latte.framework.services.restful.service;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.persistence.beans.ModuleManagementBean;
import at.arz.latte.framework.persistence.beans.MyValidationException;

@RequestScoped
@Path("modules")
//@Stateless
public class ModuleService {

	@EJB
	private ModuleManagementBean bean;

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Module> getAllModules() {
		return bean.getAllModules();
	}

	// TODO: JAXBException occurred : name:
	// com.sun.xml.internal.bind.namespacePrefixMapper, value:
	// org.apache.cxf.common.jaxb.NamespaceMapper@10267045.
	// issue in cxf jsonprovider configuration...

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Module> getModule(@PathParam("id") int id) {
		return Arrays.asList(bean.getModule(id));
	}

	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Module> createModule(@Valid Module module) { //throws ValidationException {		
		//return Arrays.asList(bean.createModule(module));
		
		return Arrays.asList(module);
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