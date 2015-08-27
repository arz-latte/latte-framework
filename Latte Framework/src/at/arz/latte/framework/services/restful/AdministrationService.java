package at.arz.latte.framework.services.restful;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import at.arz.latte.framework.dta.MenuData;

/**
 * RESTful service for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@RequestScoped
@Path("administration")
public class AdministrationService extends GenericModuleService {

	@GET
	@Path("status.json")
	@Produces({ MediaType.APPLICATION_JSON })
	public MenuData getModule() throws JAXBException, IOException {

		return loadServiceConfig("administration-service-config.xml");
	}

}