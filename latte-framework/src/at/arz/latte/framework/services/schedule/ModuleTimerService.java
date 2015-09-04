package at.arz.latte.framework.services.schedule;

import java.net.MalformedURLException;
import java.util.Set;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.ClientWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import at.arz.latte.framework.persistence.beans.ModuleManagementBean;
import at.arz.latte.framework.persistence.models.Menu;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.restful.dta.MenuData;
import at.arz.latte.framework.services.restful.LatteValidationException;
import at.arz.latte.framework.websockets.WebsocketEndpoint;
import at.arz.latte.framework.websockets.models.WebsocketMessage;

/**
 * timer for periodic checking of module status
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Singleton
@DependsOn({ "ModuleManagementBean", "WebsocketEndpoint" })
public class ModuleTimerService {

	@EJB
	private ModuleManagementBean bean;

	@EJB
	private WebsocketEndpoint websocket;

	@Inject
	private Validator validator;

	/**
	 * wait 10 seconds before start
	 */
	private int counter = -10;

	@Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
	private void checkModules() {

		counter++;

		if (counter >= 0) {

			for (Module module : bean.getAllEnabledModules()) {

				int checkInterval = module.getInterval();
				if (checkInterval > 0 && counter % checkInterval == 0) {
					checkStatus(module);
				}
			}
		}
	}

	private void checkStatus(Module module) {

		try {
			WebClient client = setupClient(module.getUrlHost(), module.getUrlPath() + "/status.json");

			// send lastModified of module to client
			if (module.getLastModified() != null) {
				client.query("lastModified", module.getLastModified());
			}

			// get menu
			MenuData menuData = client.get(MenuData.class);

			// valiate menu response data
			Set<ConstraintViolation<Object>> violations = requestValidation(menuData);
			if (!violations.isEmpty()) {
				throw new LatteValidationException(400, violations);
			}

			// got response -> check version
			if (module.getLastModified() == null || module.getLastModified() < menuData.getLastModified()) {

				System.out.println("save: " + menuData);

				Menu menu = Menu.getMenuRec(menuData);
				bean.updateModuleMenu(module.getId(), menu);
				bean.storeModulePermissions(menu);

				websocket.chat(new WebsocketMessage("update", "server"));
			} else if (!module.getRunning()) {

				// set module running to true
				bean.updateModuleRunning(module.getId(), true);

				websocket.chat(new WebsocketMessage("update", "server"));
			}

		} catch (WebApplicationException | ClientWebApplicationException ex) {
			System.out.println("Check module: " + module);
			System.out.println("WebApplicationException: " + ex.getMessage());

			// set module as inactive
			if (module.getRunning()) {

				bean.updateModuleRunning(module.getId(), false);

				websocket.chat(new WebsocketMessage("inactive", "server"));
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private Set<ConstraintViolation<Object>> requestValidation(Object moduleData) {
		return validator.validate(moduleData);
	}

	private WebClient setupClient(String host, String path) {
		WebClient client = WebClient.create(host).path(path);
		HTTPConduit http = (HTTPConduit) WebClient.getConfig(client).getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(500);
		httpClientPolicy.setReceiveTimeout(500);
		http.setClient(httpClientPolicy);
		client.accept(MediaType.APPLICATION_JSON);
		client.type(MediaType.APPLICATION_JSON);
		return client;
	}
}
