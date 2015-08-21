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

import at.arz.latte.framework.modules.dta.ModuleFullData;
import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;
import at.arz.latte.framework.persistence.beans.ModuleManagementBean;
import at.arz.latte.framework.websockets.ChatPlusEndpoint;
import at.arz.latte.framework.websockets.models.ChatMessage;

/**
 * timer for periodic checking of module status
 */
@Singleton
@DependsOn({"ModuleManagementBean","ChatPlusEndpoint"})
public class ModuleTimerService {

	@EJB
	private ModuleManagementBean bean;

	@EJB
	private ChatPlusEndpoint websocket;

	private int counter = 0;

	@Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
	private void checkModules() {

		counter++;

		for (Module module : bean.getAllModules()) {

			int checkInterval = module.getCheckInterval();
			if (module.getEnabled() && checkInterval > 0 && counter % checkInterval == 0) {
				checkStatus(module);
			}
		}

	}

	private void checkStatus(Module module) {
		System.out.println("check module status: " + module.getName());

		try {
			WebClient client = WebClient.create(module.getHost()).path(module.getPath() + "/status");
			HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	        conduit.getClient().setReceiveTimeout(2000);
	        conduit.getClient().setConnectionTimeout(2000);
	        
	        ModuleFullData status = client.accept(MediaType.APPLICATION_JSON).get(ModuleFullData.class);
	        
			// set module as active
	        if (module.getStatus() != ModuleStatus.StartedActive) {
				module.setStatus(ModuleStatus.StartedActive);
				bean.updateModule(module);				
				websocket.chat(new ChatMessage("active", "server"));
			}			

		} catch (WebApplicationException | ClientWebApplicationException ex) {
			System.out.println("WebApplicationException: " + ex.getMessage());

			// set module as inactive
			if (module.getStatus() == ModuleStatus.StartedActive) {
				module.setStatus(ModuleStatus.StartedInactive);
				bean.updateModule(module);
				websocket.chat(new ChatMessage("inactive", "server"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
