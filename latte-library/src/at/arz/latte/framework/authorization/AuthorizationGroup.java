package at.arz.latte.framework.authorization;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = AuthorizationGroup.FIND_BY_NAME,
							query = "SELECT g FROM AuthorizationGroup g WHERE g.name = :name") })
@Entity
@Cacheable(true)
@Table(name = "groups")
public class AuthorizationGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	static final String FIND_BY_NAME = "Group.GetByName";

	@Id
	private Long id;

	private String name;

	@ManyToMany
	@JoinTable(	name = "groups_permissions",
				joinColumns = { @JoinColumn(name = "group_id",
											referencedColumnName = "id") },
				inverseJoinColumns = { @JoinColumn(	name = "permissions_id",
													referencedColumnName = "id") })
	private Set<AuthorizationPermission> permissions = new HashSet<AuthorizationPermission>();

	protected AuthorizationGroup() {
		// jpa constructor
	}

	AuthorizationGroup(String name, Set<AuthorizationPermission> permissions) {
		this.name = Objects.requireNonNull(name);
		this.permissions = Objects.requireNonNull(permissions);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<AuthorizationPermission> getPermissions() {
		return Collections.unmodifiableSet(permissions);
	}

	@Override
	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AuthorizationGroup && equals((AuthorizationGroup) obj);
	}

	public boolean equals(AuthorizationGroup other) {
		return other == null ? false : name.equals(other.name);
	}

	@Override
	public String toString() {
		return "GroupEntity [id=" + id + ", name=" + name + "]";
	}

}
