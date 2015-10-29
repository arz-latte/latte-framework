package at.arz.latte.framework.module.services;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import at.arz.latte.framework.admin.Permission;
import at.arz.latte.framework.module.Menu;
import at.arz.latte.framework.module.Module;
import at.arz.latte.framework.module.ModuleQuery;
import at.arz.latte.framework.module.SubMenu;

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

	public Module updateModule(Long id, boolean running) {
		Module module = em.find(Module.class, id);
		module.setRunning(running);
		return module;
	}

	public Module updateModule(Long id, Menu menu) {

		// find permission in db
		HashMap<String, Permission> storedPermissions = new HashMap<>();
		List<Permission> permissions = new ModuleQuery(em)	.allPermissions()
															.getResultList();
		for (Permission p : permissions) {
			storedPermissions.put(p.getName(), p);
		}

		Module module = updateModule(id, true);
		module.setLastModified(menu.getLastModified());

		// update menu entry
		module.setMenu(menu);

		// update permissions
		getMenuPermissionsRec(module, storedPermissions);

		return module;
	}

	private	void
			getMenuPermissionsRec(	Module module,
									HashMap<String, Permission> storedPermissions) {

		Menu menu = module.getMenu();
		Permission p = menu.getPermission();
		if (p != null) {

			if (storedPermissions.containsKey(p.getName())) {
				menu.setPermission(storedPermissions.get(p.getName()));
			} else {
				storedPermissions.put(p.getName(), p);
				em.persist(p);
				menu.setPermission(p);
			}
		}

		// get recursive submenu permissions
		getSubMenuPermissionsRec(module, menu.getSubMenus(), storedPermissions);
	}

	private	void
			getSubMenuPermissionsRec(	Module module,
										List<SubMenu> subMenus,
										HashMap<String, Permission> storedPermissions) {

		for (SubMenu menu : subMenus) {

			Permission p = menu.getPermission();
			if (p != null) {

				if (storedPermissions.containsKey(p.getName())) {
					menu.setPermission(storedPermissions.get(p.getName()));
				} else {
					storedPermissions.put(p.getName(), p);
					em.persist(p);
					menu.setPermission(p);
				}
			}

			// get recursive submenu permissions
			getSubMenuPermissionsRec(	module,
										menu.getSubMenus(),
										storedPermissions);
		}
	}

}