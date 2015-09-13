package at.arz.latte.framework.services.restful;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import at.arz.latte.framework.restful.dta.MenuData;

/**
 * RESTful service for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@ApplicationScoped
@Path("demo")
public class DemoModuleRestfulService extends AbstractModuleHelper {

	@GET
	@Path("status.json")
	@Produces({ MediaType.APPLICATION_JSON })
	public MenuData getModule(@QueryParam("lastModified") Long lastModified) throws JAXBException, IOException {

		return loadAndCacheServiceConfig("demo1-service-config.xml", lastModified);
	}

	@GET
	@Path("useronly.json")
	@Produces({ MediaType.APPLICATION_JSON })
	public String checkUserPermission() {
		return "access ok";
	}

}