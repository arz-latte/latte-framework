package at.arz.latte.framework.authorization;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class AuthorizationPermissionRepository {

	@Inject
	private EntityManager entityManager;

	protected AuthorizationPermissionRepository() {
		// tool constructor
	}

	public AuthorizationPermissionRepository(EntityManager entityManager) {
		this.entityManager = Objects.requireNonNull(entityManager);
	}

	public List<String> findGrantedPermissionsForUserId(String userId) {
		TypedQuery<String> query = entityManager.createNamedQuery(	AuthorizationPermission.FIND_NAMES_BY_USER,
																	String.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

}
