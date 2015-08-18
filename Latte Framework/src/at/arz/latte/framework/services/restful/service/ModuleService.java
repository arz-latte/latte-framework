package at.arz.latte.framework.services.restful.service;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.persistence.beans.ModuleBean;
import at.arz.latte.framework.persistence.models.Module;

@Path("modules")
@Stateless
public class ModuleService {

	@EJB
	private ModuleBean moduleBean;

	/*
	 * @GET
	 * 
	 * @Path("all")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public List<BaseModuleData>
	 * getModules() { return moduleManagementBean.getAllModules(); }
	 */

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Module> getAllModules() {
		return moduleBean.getAllModulesFull();
	}

	// TODO: JAXBException occurred : name:
	// com.sun.xml.internal.bind.namespacePrefixMapper, value:
	// org.apache.cxf.common.jaxb.NamespaceMapper@10267045.
	// issue in cxf jsonprovider configuration...

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Module> getModule(@PathParam("id") int id) {
		return Arrays.asList(moduleBean.getModule(id));
	}

	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Module> createModule(Module module) {
		return Arrays.asList(moduleBean.createModule(module));
	}

	@PUT
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Module> updateModule(Module module) {
		return Arrays.asList(moduleBean.updateModule(module));
	}

	@DELETE
	@Path("delete/{id}")
	public void deleteModule(@PathParam("id") int moduleId) {
		moduleBean.deleteModule(moduleId);
	}
}