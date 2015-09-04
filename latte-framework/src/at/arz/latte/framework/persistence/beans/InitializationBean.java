package at.arz.latte.framework.persistence.beans;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * initializes status of all stored modules (set running to false)
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Singleton
@Startup
@DependsOn("ModuleManagementBean")
public class InitializationBean {

	@EJB
	private ModuleManagementBean bean;

	@PostConstruct
	private void initialize() {
		bean.initAllModules();
	}
}
