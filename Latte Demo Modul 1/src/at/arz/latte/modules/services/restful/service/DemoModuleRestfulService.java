package at.arz.latte.modules.services.restful.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.modules.dta.ModuleUpdateData;
import at.arz.latte.modules.services.restful.config.DemoModuleConfiguration;

@Path("demo")
public class DemoModuleRestfulService {

	@GET
	@Path("status.json")
	@Produces({ MediaType.APPLICATION_JSON })
	public ModuleUpdateData getModule() {
		return DemoModuleConfiguration.MODULE;
	}

}
