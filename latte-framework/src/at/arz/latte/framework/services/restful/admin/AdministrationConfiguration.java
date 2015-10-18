package at.arz.latte.framework.services.restful.admin;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * initialization of RESTful Service classes
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@ApplicationScoped
@ApplicationPath("/admin/api")
public class AdministrationConfiguration extends Application {

	private static final String ADMIN_CONFIG_FILE = "administration-service-config.xml";

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
		applicationClasses.add(ModuleService.class);
		applicationClasses.add(UserService.class);
		applicationClasses.add(GroupService.class);
	}

	public static URL getConfiguration() {
		return AdministrationConfiguration.class.getResource(ADMIN_CONFIG_FILE);
	}
}