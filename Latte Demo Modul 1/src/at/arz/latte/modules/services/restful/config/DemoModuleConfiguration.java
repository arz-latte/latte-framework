package at.arz.latte.modules.services.restful.config;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import at.arz.latte.framework.modules.dta.ModulUpdateData;
import at.arz.latte.modules.services.restful.service.DemoModuleRestfulService;

@ApplicationScoped
@ApplicationPath("/api/v1")
public class DemoModuleConfiguration extends Application {

	public static ModulUpdateData MODULE = null;

	static {
		System.out.println("demo init");
		MODULE = new ModulUpdateData("v1.00");
	}

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
		applicationClasses.add(DemoModuleRestfulService.class);
	}
}