package at.arz.latte.framework.services.restful;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.modules.dta.ModuleMenuData;
import at.arz.latte.framework.persistence.beans.FrameworkManagementBean;

/**
 * RESTful service for module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@RequestScoped
@Path("framework")
public class FrameworkService {

	@EJB
	private FrameworkManagementBean bean;

	@GET
	@Path("init.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleMenuData> getInitData() {
		return bean.getAll();
	}

}