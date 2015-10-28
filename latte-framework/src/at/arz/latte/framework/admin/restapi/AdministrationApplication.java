package at.arz.latte.framework.admin.restapi;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/api/admin")
public class AdministrationApplication extends Application {

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
		applicationClasses.add(ModuleAdministration.class);
		applicationClasses.add(UserAdministration.class);
		applicationClasses.add(GroupAdministration.class);
		applicationClasses.add(PermissionAdministration.class);
	}

	public static URL getConfiguration() {
		return AdministrationApplication.class.getResource(ADMIN_CONFIG_FILE);
	}
}
