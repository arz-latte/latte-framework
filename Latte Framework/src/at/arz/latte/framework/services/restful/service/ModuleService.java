package at.arz.latte.framework.services.restful.service;

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

import at.arz.latte.framework.modules.dta.ModuleBaseData;
import at.arz.latte.framework.modules.dta.ModuleFullData;
import at.arz.latte.framework.modules.dta.ResultData;
import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.persistence.beans.ModuleManagementBean;

@RequestScoped
@Path("modules")
public class ModuleService {

	@EJB
	private ModuleManagementBean bean;

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleBaseData> getAllModules() {
		return bean.getAllModulesBase();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleFullData getModule(@PathParam("id") int id) {
		return new ModuleFullData(bean.getModule(id));
	}

	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultData createModule(ModuleFullData m) {
		Module module = new Module(m.getId(), m.getName(), m.getVersion(), m.getUrl(), m.getCheckInterval(),
				m.getStatus(), m.getEnabled());
		return new ResultData(bean.createModule(module));
	}

	@PUT
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultData updateModule(ModuleFullData m) {
		Module module = new Module(m.getId(), m.getName(), m.getVersion(), m.getUrl(), m.getCheckInterval(),
				m.getStatus(), m.getEnabled());
		return new ResultData(bean.updateModule(module));
	}

	@DELETE
	@Path("delete/{id}")
	public void deleteModule(@PathParam("id") int moduleId) {
		bean.deleteModule(moduleId);
	}
}