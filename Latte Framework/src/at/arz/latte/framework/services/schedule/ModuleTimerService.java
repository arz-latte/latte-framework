package at.arz.latte.framework.services.schedule;

import java.net.MalformedURLException;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.ClientWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;

import at.arz.latte.framework.persistence.beans.ModuleManagementBean;
import at.arz.latte.framework.persistence.models.Menu;
import at.arz.latte.framework.persistence.models.Module;
import at.arz.latte.framework.restful.dta.MenuData;
import at.arz.latte.framework.restful.dta.SubMenuData;
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

	private int counter = 0;

	@Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
	private void checkModules() {

		counter++;

		for (Module module : bean.getAllModules()) {
			
			int checkInterval = module.getInterval();
			if (module.getEnabled() && checkInterval > 0 && counter % checkInterval == 0) {
				checkStatus(module);
			}
		}
	}

	private void checkStatus(Module module) {
		
		try {
			WebClient client = WebClient.create(module.getUrlHost()).path(module.getUrlPath() + "/status.json");
			HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
			conduit.getClient().setReceiveTimeout(2000);
			conduit.getClient().setConnectionTimeout(2000);

			// todo: send version to client via post...
			MenuData menuData = client.accept(MediaType.APPLICATION_JSON).get(MenuData.class);

			
			// valiate menu response data....
			
			// got response -> check version
			if (module.getLastModified() == null || module.getLastModified() < menuData.getLastModified()) {

				System.out.println("save new menu");
				System.out.println("save: " + module);
				System.out.println("save: " + menuData);
								
				Menu menu = Menu.getMenuRec(menuData);
				bean.updateModuleMenu(module.getId(), menu);				
				
				websocket.chat(new WebsocketMessage("update", "server"));
			} else if (!module.getRunning()) {
				
				// set module running to true
				System.out.println("save running");
				System.out.println("save: " + module);
				System.out.println("save: " + menuData);
				
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
}
