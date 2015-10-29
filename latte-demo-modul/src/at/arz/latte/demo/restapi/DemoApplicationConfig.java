package at.arz.latte.demo.restapi;

import java.io.IOException;
import java.net.URL;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import at.arz.latte.framework.restapi.MenuData;
import at.arz.latte.framework.services.ModuleConfigHelper;

/**
 * RESTful service for module configuration management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@ApplicationScoped
@Path("config")
public class DemoApplicationConfig {

	private static final String CONFIG_PATH = "demo-service-config.xml";

	@EJB
	private ModuleConfigHelper configHelper;

	private static MenuData menuData;

	@GET
	@Path("status.json")
	@Produces({ MediaType.APPLICATION_JSON })
	public	MenuData
			getModule(@QueryParam("lastModified") Long lastModified)	throws JAXBException,
																		IOException {

		if (menuData == null) {
			URL url = getClass().getResource(CONFIG_PATH);
			menuData = configHelper.loadAndCacheServiceConfig(	url,
																lastModified);
		}

		return menuData;
	}

}