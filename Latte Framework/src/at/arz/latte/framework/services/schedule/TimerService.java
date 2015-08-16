package at.arz.latte.framework.services.schedule;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.ClientWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;

import at.arz.latte.framework.persistence.DataHandler;
import at.arz.latte.framework.services.models.ModuleModel;
import at.arz.latte.framework.services.models.ModuleStatus;

@Singleton
public class TimerService {
	@EJB
	HelloService helloService;

	@Schedule(second = "*/10", minute = "*", hour = "*", persistent = false)
	public void doWork() {
		// System.out.println("timer: " + helloService.sayHello());

		String API_URL = "http://localhost:8081";
		String API_PATH = "/Latte_Demo_Modul_1/api/v1/module";
		
		

		try {
			ModuleModel module = WebClient.create(API_URL)
					.path(API_PATH + "/status")
					.accept(MediaType.APPLICATION_JSON).get(ModuleModel.class);

			DataHandler.updateApplication(module);			
			
		} catch (WebApplicationException | ClientWebApplicationException ex) {
			System.out.println("WebApplicationException: " + ex.getMessage());
			
			// set module as inactive
			ModuleModel app = DataHandler.getApplications().get(2);
			app.setStatus(ModuleStatus.STARTED_INACTIVE);
			DataHandler.updateApplication(app);
		}
	}
}
