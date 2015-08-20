package at.arz.latte.demo.servlet;

import java.util.List;

import javax.imageio.spi.RegisterableService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import at.arz.latte.modules.services.restful.config.DemoModuleConfiguration;

@WebListener
public class InitServletContextListener implements ServletContextListener {

	private static final String API_URL = "http://localhost:8080";
	private static final String API_PATH = "/latte/api/v1/module";

	private Thread runner;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("ServletContextEvent initialized");

		runner = new Thread(new Runnable() {

			@Override
			public void run() {
			//	register();
			}
		});
		runner.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
		runner.interrupt();

		//unregister();
	}
/*
	public void register() {
		Module request = DemoModuleConfiguration.MODULE;

		Result rm = WebClient.create(API_URL).path(API_PATH + "/register")
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
		ModuleModel request = DemoModuleConfiguration.MODULE;

		ResultModel rm = WebClient.create(API_URL)
				.path(API_PATH + "/unregister")
				.accept(MediaType.APPLICATION_JSON)
				.post(request, ResultModel.class);
		
		System.out.println(rm.getSuccess());
	}*/
}
