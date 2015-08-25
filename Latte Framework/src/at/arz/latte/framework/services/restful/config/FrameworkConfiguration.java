package at.arz.latte.framework.services.restful.config;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import at.arz.latte.framework.persistence.beans.GenericManagementBean;
import at.arz.latte.framework.services.restful.AdministrationService;
import at.arz.latte.framework.services.restful.FrameworkService;
import at.arz.latte.framework.services.restful.ModuleService;

/**
 * initialization of RESTful Service classes
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@ApplicationScoped
@ApplicationPath("/api/v1")
public class FrameworkConfiguration extends Application {

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
		applicationClasses.add(AdministrationService.class);
		applicationClasses.add(FrameworkService.class);
		
		// todo, add??   applicationClasses.add(GenericManagementBean.class);
		
		// IEGEND: EjbTransactionUtil.handleSystemException: The type "class at.arz.latte.framework.modules.models.AbstractEntity" has not been enhanced.
	}
}