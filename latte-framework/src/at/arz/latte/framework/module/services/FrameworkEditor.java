package at.arz.latte.framework.module.services;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import at.arz.latte.framework.admin.Permission;
import at.arz.latte.framework.module.Module;
import at.arz.latte.framework.module.ModuleQuery;
import at.arz.latte.framework.restapi.MenuData;
import at.arz.latte.framework.restapi.SubMenuData;

/**
 * bean for framework/module management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class FrameworkEditor {

	private EntityManager em;

	public FrameworkEditor(EntityManager em) {
		this.em = Objects.requireNonNull(em);
	}

	public Module update(Long id, boolean running) {
		Module module = em.find(Module.class, id);
		module.setRunning(running);
		return module;
	}

	public Module update(Long id, MenuData menu) {

		// find permission in db
		List<String> permissions = new ModuleQuery(em)	.allPermissionsName()
														.getResultList();

		Module module = update(id, true);
		module.setLastModified(menu.getLastModified());

		// update permissions
		updatePermissions(menu, permissions);

		return module;
	}

	private void updatePermissions(	MenuData menu,
										List<String> permissions) {

		String permission = menu.getPermission();
		if (permission != null) {
			if (!permissions.contains(permission)) {
				permissions.add(permission);
				em.persist(new Permission(permission));
			}
		}

		// get recursive submenu permissions
		updatePermissions(menu.getSubMenus(), permissions);
	}

	private void updatePermissions(	List<SubMenuData> subMenus,
											List<String> permissions) {

		for (SubMenuData menu : subMenus) {

			String permission = menu.getPermission();
			if (permission != null) {
				if (!permissions.contains(permission)) {
					permissions.add(permission);
					em.persist(new Permission(permission));
				}
			}

			// get recursive submenu permissions
			updatePermissions(menu.getSubMenus(), permissions);
		}
	}

}