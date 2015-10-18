package at.arz.latte.framework.authorization;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = AuthorizationUser.FIND_BY_USERID,
							query = "SELECT u FROM AuthorizationUser u WHERE u.userId = :userId") })
@Entity
@Cacheable(true)
@Table(name = "users")
public class AuthorizationUser implements Serializable {
	private static final long serialVersionUID = 1L;

	static final String FIND_BY_USERID = "User.GetByEmail";

	@Id
	private Long id;

	@Column(name = "email")
	private String userId;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "users_groups",
				joinColumns = { @JoinColumn(name = "user_id",
											referencedColumnName = "id") },
				inverseJoinColumns = { @JoinColumn(	name = "group_id",
													referencedColumnName = "id") })
	private Set<AuthorizationGroup> groups = new HashSet<AuthorizationGroup>();

	protected AuthorizationUser() {
		// jpa constructor
	}

	AuthorizationUser(String userId, Set<AuthorizationGroup> groups) {
		this.userId = Objects.requireNonNull(userId);
		this.groups = Objects.requireNonNull(groups);
	}

	public Long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public Set<AuthorizationGroup> getGroup() {
		return Collections.unmodifiableSet(groups);
	}

	@Override
	public int hashCode() {
		return userId == null ? 0 : userId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AuthorizationUser && equals((AuthorizationUser) obj);
	}

	public boolean equals(AuthorizationUser entity) {
		return entity == null ? false : userId.equals(entity.userId);
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", userId=" + userId + "]";
	}

}
