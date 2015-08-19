package at.arz.latte.framework.services.schedule;

import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.ClientWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;

import at.arz.latte.framework.persistence.DataHandler;
import at.arz.latte.framework.services.models.ModuleModel;
import at.arz.latte.framework.services.models.ModuleStatus;

/**
 * check status of a single module
 */
@Stateless
public class ModuleStatusService {

	public void checkStatus(ModuleModel module) {
		System.out.println("check module status: " + module.getName());

		try {
			ModuleModel status = WebClient.create(module.getHost())
					.path(module.getPath() + "/status")
					.accept(MediaType.APPLICATION_JSON).get(ModuleModel.class);

			// set module as active
			status.setStatus(ModuleStatus.STARTED_ACTIVE);
			DataHandler.updateApplication(status);

		} catch (WebApplicationException | ClientWebApplicationException ex) {
			System.out.println("WebApplicationException: " + ex.getMessage());

			// set module as inactive
			if (module.getStatus() == ModuleStatus.STARTED_ACTIVE) {
				module.setStatus(ModuleStatus.STARTED_INACTIVE);
				DataHandler.updateApplication(module);
			}
		}
	}
}