package at.arz.latte.modules.services.restful.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.modules.dta.ModulUpdateData;
import at.arz.latte.modules.services.restful.config.DemoModuleConfiguration;

@Path("module")
public class DemoModuleRestfulService {

	@GET
	@Path("status")
	@Produces({ MediaType.APPLICATION_JSON })
	public ModulUpdateData getModule() {
		return DemoModuleConfiguration.MODULE;
	}

}
