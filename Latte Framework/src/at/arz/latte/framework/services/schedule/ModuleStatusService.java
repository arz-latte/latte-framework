package at.arz.latte.framework.services.schedule;

import java.net.MalformedURLException;

import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.ClientWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;

import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;

/**
 * check status of a single module
 */
@Stateless
public class ModuleStatusService {

	public void checkStatus(Module module) {
		System.out.println("check module status: " + module.getName());

		try {
			Module status = WebClient.create(module.getHost())
					.path(module.getPath() + "/status")
					.accept(MediaType.APPLICATION_JSON).get(Module.class);

			// set module as active
			status.setStatus(ModuleStatus.StartedActive);
		//	DataHandler.updateApplication(status);

		} catch (WebApplicationException | ClientWebApplicationException ex) {
			System.out.println("WebApplicationException: " + ex.getMessage());

			// set module as inactive
			if (module.getStatus() == ModuleStatus.StartedActive) {
				module.setStatus(ModuleStatus.StartedInactive);
			//	DataHandler.updateApplication(module);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}