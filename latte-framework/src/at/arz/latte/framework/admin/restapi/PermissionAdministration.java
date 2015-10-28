package at.arz.latte.framework.admin.restapi;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.FrameworkConstants;
import at.arz.latte.framework.admin.AdminQuery;
import at.arz.latte.framework.restapi.PermissionData;
import at.arz.latte.framework.util.Functions;

/**
 * RESTful service for permission management
 * 
 * @author Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 * @author mrodler
 *
 */
@Stateless
@Path("/permissions")
public class PermissionAdministration {

	@PersistenceContext(unitName = FrameworkConstants.JPA_UNIT)
	private EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PermissionData> allPermissions() {
		return Functions.map(	AdminMapper.MAP_TO_PERMISSIONDATA,
								new AdminQuery(em).allPermissions());
	}

}