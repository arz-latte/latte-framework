package at.arz.latte.modules.services.restful.config;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import at.arz.latte.framework.services.models.ModuleModel;

@ApplicationPath("/api/v1")
public class DemoModuleConfiguration extends Application {

	public static ModuleModel MODULE = null;

	static {
		System.out.println("demo init");
		
		try {
			MODULE = new ModuleModel("Demo Module 1",
					1, new URL("http://localhost:8081/Latte_Demo_Modul_1/api/v1/module"), 60);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
}
