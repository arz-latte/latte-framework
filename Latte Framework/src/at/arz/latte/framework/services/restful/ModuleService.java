package at.arz.latte.framework.services.restful;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.modules.dta.ModuleMultipleData;
import at.arz.latte.framework.modules.dta.ModuleSingleData;
import at.arz.latte.framework.modules.dta.ResultData;
import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;
import at.arz.latte.framework.persistence.beans.ModuleManagementBean;

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

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleMultipleData> getAllModules() {
		return bean.getAllModulesBase();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleSingleData getModule(@PathParam("id") Long id) {
		return new ModuleSingleData(bean.getModule(id));
	}

	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultData createModule(ModuleSingleData m) {
		Module module = new Module(m.getName(), m.getProvider(), m.getUrl(), m.getInterval(), m.getEnabled());
		return new ResultData(bean.createModule(module));
	}

	@PUT
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultData updateModule(ModuleSingleData m) {
		return new ResultData(bean.updateModule(m.getId(), m.getName(), m.getProvider(), m.getUrl(), m.getInterval(), m.getEnabled()));
	}

	@DELETE
	@Path("delete/{id}")
	public void deleteModule(@PathParam("id") Long moduleId) {
		bean.deleteModule(moduleId);
	}
}