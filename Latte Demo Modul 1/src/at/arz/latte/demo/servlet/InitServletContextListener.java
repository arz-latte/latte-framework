package at.arz.latte.demo.servlet;

import java.util.List;

import javax.imageio.spi.RegisterableService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import at.arz.latte.framework.services.models.ModuleModel;
import at.arz.latte.framework.services.models.ResultModel;

@WebListener
public class InitServletContextListener implements ServletContextListener {

	private static final String API_URL = "http://localhost:8080";
	private static final String API_PATH = "/Latte_Framework/api/v1/framework";

	private Thread runner;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("ServletContextEvent initialized");

		runner = new Thread(new Runnable() {

			@Override
			public void run() {
				register();
			}
		});
		runner.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
		runner.interrupt();

		unregister();
	}

	public void register() {
		ModuleModel request = new ModuleModel("Latte Demo Modul 1");

		ResultModel rm = WebClient.create(API_URL).path(API_PATH + "/register")
				.accept(MediaType.APPLICATION_JSON)
				.post(request, ResultModel.class);

		System.out.println(rm.getSuccess());

		// get all apps
		List<ModuleModel> apps = (List<ModuleModel>) WebClient
				.create(API_URL).path(API_PATH + "/list")
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(ModuleModel.class);

		for (ModuleModel app : apps) {
			System.out.println(app.getName());
		}
	}

	private void unregister() {
		ModuleModel request = new ModuleModel("Latte Demo Modul 1");

		ResultModel rm = WebClient.create(API_URL)
				.path(API_PATH + "/unregister")
				.accept(MediaType.APPLICATION_JSON)
				.post(request, ResultModel.class);
		
		System.out.println(rm.getSuccess());
	}
}
