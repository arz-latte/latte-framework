package at.arz.latte.framework.module;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ModuleQuery {

	private EntityManager entityManager;

	public ModuleQuery(EntityManager entityManager) {
		this.entityManager = Objects.requireNonNull(entityManager);
	}

	public TypedQuery<Module> all() {
		return entityManager.createNamedQuery(Module.QUERY_GETALL, Module.class);
	}
}
