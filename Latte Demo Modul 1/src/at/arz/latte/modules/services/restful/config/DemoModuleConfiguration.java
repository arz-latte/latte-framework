package at.arz.latte.modules.services.restful.config;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import at.arz.latte.framework.modules.dta.ModuleFullData;
import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;

@ApplicationScoped
@ApplicationPath("/api/v1")
public class DemoModuleConfiguration extends Application {

	public static ModuleFullData MODULE = null;

	static {
		System.out.println("demo init");
		
		Module m = new Module(0, "Demo Module", "v1.00", "http://localhost:8081/Latte_Demo_Modul_1/api/v1/module", 60,  ModuleStatus.Unknown, true); 
		MODULE = new ModuleFullData(m);
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
		//applicationClasses.add(ModuleService.class);
	}
}