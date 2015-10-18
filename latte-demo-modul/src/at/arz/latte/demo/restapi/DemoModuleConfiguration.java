package at.arz.latte.demo.restapi;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import at.arz.latte.demo.restapi.DemoModuleRestfulService;

@ApplicationScoped
@ApplicationPath("/api")
public class DemoModuleConfiguration extends Application {

	private Set<Class<?>> applicationClasses;

	@Override
	public Set<Class<?>> getClasses() {
		if (applicationClasses == null) {
			initApplicationClasses();
		}
		return applicationClasses;
	}

	private void initApplicationClasses() {
		applicationClasses = new HashSet<Class<?>>();
		applicationClasses.add(DemoModuleConfigurationRestfulService.class);
		applicationClasses.add(DemoModuleRestfulService.class);
	}
}