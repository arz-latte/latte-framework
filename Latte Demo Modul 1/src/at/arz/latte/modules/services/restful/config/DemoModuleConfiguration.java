package at.arz.latte.modules.services.restful.config;

import javax.ws.rs.ApplicationPath;

import javax.ws.rs.core.Application;

@ApplicationPath("/api/v1")
public class DemoModuleConfiguration extends Application {

	static {
		System.out.println("demo init");
	}
}
