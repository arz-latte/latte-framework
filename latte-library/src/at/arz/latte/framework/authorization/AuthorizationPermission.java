package at.arz.latte.framework.authorization;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = AuthorizationPermission.FIND_NAMES_BY_USER,
							query = "SELECT p.name FROM AuthorizationUser u JOIN u.groups g JOIN g.permissions p WHERE u.userId = :userId"), })
@Entity
@Cacheable(true)
@Table(name = "permissions")
public class AuthorizationPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_NAMES_BY_USER = "Permission.GetNameByUser";

	@Id
	private Long id;

	private String name;

	protected AuthorizationPermission() {
		// jpa constructor
	}

	AuthorizationPermission(String name) {
		this.name = Objects.requireNonNull(name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AuthorizationPermission && equals((AuthorizationPermission) obj);
	}

	public boolean equals(AuthorizationPermission other) {
		return other == null ? false : name.equals(other.name);
	}

	@Override
	public String toString() {
		return "PermissionEntity [id=" + id + ", name=" + name + "]";
	}

}
