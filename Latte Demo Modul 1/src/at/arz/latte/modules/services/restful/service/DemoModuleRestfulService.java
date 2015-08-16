package at.arz.latte.modules.services.restful.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.services.models.ModuleModel;
import at.arz.latte.framework.services.models.ModuleStatus;

@Path("module")
public class DemoModuleRestfulService {

	@GET
	@Path("status")
	@Produces({ MediaType.APPLICATION_JSON })
	public ModuleModel getModule() {
		return new ModuleModel("Demo Module 1", ModuleStatus.STARTED_ACTIVE, 1);
	}

}