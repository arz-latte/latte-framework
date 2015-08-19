package at.arz.latte.framework.services.restful.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.services.models.ModuleModel;
import at.arz.latte.framework.services.models.ResultModel;
import at.arz.latte.framework.persistence.DataHandler;

@Path("framework")
public class FrameworkRestfulService {
	
	@GET
	@Path("list")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ModuleModel> getModuleList() {
		return DataHandler.getApplications();	
	}
	
	@POST
	@Path("register")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultModel registerModule(ModuleModel app) {
		return DataHandler.addApplication(app);
	}
	
	@POST
	@Path("unregister")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultModel unregisterModule(ModuleModel app) {
		return DataHandler.removeApplication(app);
	}
	
	
}