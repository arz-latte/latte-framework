package at.arz.latte.framework.persistence.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * persistent entity for a role
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@NamedQueries({
		@NamedQuery(name = Role.QUERY_GETALL_BASE, query = "SELECT new at.arz.latte.framework.restful.dta.RoleData(r.id, r.name) FROM Role r ORDER BY r.name"),
		@NamedQuery(name = Role.QUERY_GET_BY_NAME, query = "SELECT r FROM Role r WHERE r.name = :name") })
@Entity
@Table(name = "roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL_BASE = "Role.GetAllBase";
	public static final String QUERY_GET_BY_NAME = "Role.GetByName";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Role.ID")
	@TableGenerator(name = "Role.ID", table = "latte_seq", pkColumnName = "KEY", pkColumnValue = "Role.ID", valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Column(unique=true)
	@Size(min = 1, max = 63)
	private String name;

	@ManyToMany
	private Set<Permission> permission = new HashSet<Permission>();
	
	@Version
	private long version;

	/**
	 * JPA consturctor
	 */
	public Role() {
		super();
	}

	/**
	 * used for creation via REST-service or JUnit
	 */
	public Role(String name) {
		this();
		this.name = name;
	}

	/**
	 * used for creation via REST-service or JUnit
	 */
	public Role(Long id) {
		this();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Permission> getPermission() {
		return permission;
	}

	public void setPermission(Set<Permission> permission) {
		this.permission = permission;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", permission=" + permission + "]";
	}

}
