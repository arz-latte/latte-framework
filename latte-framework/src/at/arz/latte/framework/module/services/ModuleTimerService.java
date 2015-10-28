package at.arz.latte.framework.module.services;

import java.util.List;
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

import at.arz.latte.framework.exceptions.LatteValidationException;
import at.arz.latte.framework.module.Menu;
import at.arz.latte.framework.module.Module;
import at.arz.latte.framework.restapi.MenuData;
import at.arz.latte.framework.websockets.WebsocketEndpoint;
import at.arz.latte.framework.websockets.WebsocketMessage;

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

	// todo
	private static final String FRAMEWORK_URI = "http://localhost:8080";

	private static final String CONFIG_PATH = "/api/config/status.json";

	/**
	 * wait 10 seconds before start
	 */
	private int counter = -10;

	private Integer modulesSize;

	@Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
	private void checkModules() {

		counter++;

		if (counter >= 0) {

			List<Module> modules = bean.getAllEnabledModules();

			// notify clients if number of enabled modules changed
			if (modulesSize == null) {
				modulesSize = modules.size(); // first time
			} else if (modulesSize < modules.size()) {
				modulesSize = modules.size();
				websocket.chat(new WebsocketMessage("new-module"));

			} else if (modulesSize > modules.size()) {
				modulesSize = modules.size();
				websocket.chat(new WebsocketMessage("delete-module"));
			}

			// check status of modules
			for (Module module : modules) {
				int checkInterval = module.getInterval();
				if (checkInterval > 0 && counter % checkInterval == 0) {
					checkStatus(module);
				}
			}
		}
	}

	private void checkStatus(Module module) {

		try {
			WebClient client = setupClient(	FRAMEWORK_URI,
											module.getUrl() + CONFIG_PATH);

			// send lastModified of module to client
			if (module.getLastModified() != null) {
				client.query("lastModified", module.getLastModified());
			}

			// get menu from client
			MenuData menuData = client.get(MenuData.class);

			// valiate menu response data
			Set<ConstraintViolation<Object>> violations = requestValidation(menuData);
			if (!violations.isEmpty()) {
				throw new LatteValidationException(400, violations);
			}

			// got response -> check version
			if (module.getLastModified() == null || module.getLastModified() < menuData.getLastModified()) {

				Menu menu = Menu.getMenuRec(menuData);
				bean.updateModuleMenu(module.getId(), menu);

				websocket.chat(new WebsocketMessage("update-module",
													module.getId().toString()));
			} else if (!module.getRunning()) {

				// set module running to true
				bean.updateModuleRunning(module.getId(), true);

				websocket.chat(new WebsocketMessage("activate-module",
													module.getId().toString()));
			}

		} catch (WebApplicationException | ClientWebApplicationException ex) {
			System.out.println("Check module: " + module);
			System.out.println("WebApplicationException: " + ex.getMessage());

			// set module as inactive
			if (module.getRunning()) {

				bean.updateModuleRunning(module.getId(), false);

				websocket.chat(new WebsocketMessage("deactivate-module",
													module.getId().toString()));
			}
		}
	}

	private Set<ConstraintViolation<Object>>
			requestValidation(Object moduleData) {
		return validator.validate(moduleData);
	}

	private WebClient setupClient(String host, String path) {
		WebClient client = WebClient.create(host).path(path);
		HTTPConduit http = (HTTPConduit) WebClient.getConfig(client)
													.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(500);
		httpClientPolicy.setReceiveTimeout(500);
		http.setClient(httpClientPolicy);
		client.accept(MediaType.APPLICATION_JSON);
		client.type(MediaType.APPLICATION_JSON);
		return client;
	}
}
