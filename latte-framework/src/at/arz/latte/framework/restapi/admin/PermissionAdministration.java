package at.arz.latte.framework.restapi.admin;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.persistence.beans.GroupManagementBean;
import at.arz.latte.framework.restful.dta.PermissionData;

/**
 * RESTful service for group management
 * 
 * @author Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 * @author mrodler
 *
 */
@Stateless
@Path("/permissions")
public class PermissionAdministration {

	@Inject
	private GroupManagementBean bean;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PermissionData> getAllPermissions() {
		return bean.getAllPermissionsData();
	}

}