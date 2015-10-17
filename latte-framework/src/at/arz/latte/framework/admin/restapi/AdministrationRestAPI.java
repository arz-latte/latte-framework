package at.arz.latte.framework.admin.restapi;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/api/admin")
public class AdministrationRestAPI extends Application {

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
		applicationClasses.add(UserAdministration.class);
		applicationClasses.add(GroupAdministration.class);
		applicationClasses.add(PermissionAdministration.class);
	}

}
