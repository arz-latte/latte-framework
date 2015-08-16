package at.arz.latte.demo.servlet;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import at.arz.latte.framework.services.models.ApplicationModel;
import at.arz.latte.framework.services.models.ResultModel;

@WebListener
public class InitServletContextListener implements ServletContextListener,
		Runnable {

	private static final String API_URL = "http://localhost:8080";
	private static final String API_PATH = "/Latte_Framework/api/v1/framework";

	private Thread runner;

	// Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("ServletContextEvent initialized");

		runner = new Thread(this);
		runner.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
		runner.interrupt();
	}

	@Override
	public void run() {

		// register app
		ApplicationModel request = new ApplicationModel("Latte Demo Modul 1");

		ResultModel rm = WebClient.create(API_URL).path(API_PATH + "/add")
                .accept(MediaType.APPLICATION_JSON)
                .post(request, ResultModel.class);
		
		System.out.println(rm.getSuccess());

		// get all apps
		List<ApplicationModel> apps = (List<ApplicationModel>) WebClient.create(API_URL).path(API_PATH + "/list")
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(ApplicationModel.class);

		for (ApplicationModel app : apps) {
			System.out.println(app.getName());
		}

	}
}
