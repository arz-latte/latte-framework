package at.arz.latte.framework.services.restful;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.persistence.beans.RoleManagementBean;
import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.persistence.models.Role;
import at.arz.latte.framework.restful.dta.PermissionData;
import at.arz.latte.framework.restful.dta.RoleData;

/**
 * RESTful service for role management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@RequestScoped
@Path("roles")
public class RoleService {

	@EJB
	private RoleManagementBean bean;

	@Inject
	private Validator validator;

	@GET
	@Path("all.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RoleData> getAllRoles() {
		return bean.getAllRolesData();
	}

	@GET
	@Path("permissions.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PermissionData> getAllPermissions() {
		return bean.getAllPermissionsData();
	}

	@GET
	@Path("get.json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RoleData getRole(@PathParam("id") Long id) {
		Role role = bean.getRole(id);
		return toRoleData(role);
	}

	@POST
	@Path("create.json")
	@Produces(MediaType.APPLICATION_JSON)
	public RoleData createRole(RoleData roleData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(roleData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		Role role = new Role(roleData.getName());

		return toRoleData(bean.createRole(role, roleData.getPermission()));
	}

	@PUT
	@Path("update.json")
	@Produces(MediaType.APPLICATION_JSON)
	public RoleData updateRole(RoleData roleData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(roleData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		Role role = bean.updateRole(roleData.getId(), roleData.getName(), roleData.getPermission());

		return toRoleData(role);
	}

	@DELETE
	@Path("delete.json/{id}")
	public void deleteRole(@PathParam("id") Long roleId) {
		bean.deleteRole(roleId);
	}

	private Set<ConstraintViolation<Object>> requestValidation(Object roleData) {
		return validator.validate(roleData);
	}

	/**
	 * helper for REST service
	 * 
	 * @param roleData
	 * @return
	 */
	public RoleData toRoleData(Role role) {
		RoleData roleData = new RoleData();
		roleData.setId(role.getId());
		roleData.setName(role.getName());

		if (role.getPermission() != null) {
			for (Permission permission : role.getPermission()) {
				PermissionData permissionData = new PermissionData();
				permissionData.setId(permission.getId());
				roleData.addPermission(permissionData);
			}
		}
		
		return roleData;
	}
}