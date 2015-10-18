package at.arz.latte.demo.restapi;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.services.ModuleConfigHelper;

/**
 * RESTful service for demo module
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@ApplicationScoped
@Path("demo")
public class DemoModuleRestfulService {

	@EJB
	private ModuleConfigHelper configHelper;

	@GET
	@Path("adminonly.json")
	@Produces({ MediaType.APPLICATION_JSON })
	public String checkUserPermission() {
		return "access ok";
	}

}