package at.arz.latte.framework.module;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.framework.admin.Permission;

public class ModuleQuery {

	private EntityManager entityManager;

	public ModuleQuery(EntityManager entityManager) {
		this.entityManager = Objects.requireNonNull(entityManager);
	}

	public TypedQuery<Module> allEnabled() {
		return entityManager.createNamedQuery(	Module.QUERY_GETALL_ENABLED,
												Module.class);
	}

	public TypedQuery<String> allPermissionsName() {
		return entityManager.createNamedQuery(	Permission.QUERY_GETALL_NAME,
												String.class);
	}
}
