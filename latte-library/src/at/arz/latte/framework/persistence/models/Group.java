package at.arz.latte.framework.persistence.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
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
 * persistent entity for a group
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@NamedQueries({
		@NamedQuery(name = Group.QUERY_GETALL_BASE, query = "SELECT new at.arz.latte.framework.restful.dta.GroupData(g.id, g.name) FROM Group g ORDER BY g.name"),
		@NamedQuery(name = Group.QUERY_GET_BY_NAME, query = "SELECT g FROM Group g WHERE g.name = :name") })
@Entity
@Table(name = "groups")
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL_BASE = "Group.GetAllBase";
	public static final String QUERY_GET_BY_NAME = "Group.GetByName";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Group.ID")
	@TableGenerator(name = "Group.ID", table = "latte_seq", pkColumnName = "KEY", pkColumnValue = "Group.ID", valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Column(unique = true)
	@Size(min = 1, max = 63)
	private String name;

	@ManyToMany
	private Set<Permission> permissions = new HashSet<Permission>();

	@Version
	private long version;

	/**
	 * JPA consturctor
	 */
	public Group() {
		super();
	}

	/**
	 * used for creation via REST-service or JUnit
	 */
	public Group(String name) {
		this.name = name;
		this.permissions=new HashSet<Permission>();
	}

	/**
	 * used for creation via REST-service or JUnit
	 */
	public Group(Long id) {
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

	public Set<Permission> getPermissions() {
		return permissions;
	}
	
	public void addPermission(Permission permission){
		Objects.requireNonNull(permission);
		this.permissions.add(permission);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((permissions == null) ? 0 : permissions.hashCode());
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
		Group other = (Group) obj;
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
		if (permissions == null) {
			if (other.permissions != null)
				return false;
		} else if (!permissions.equals(other.permissions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", permission=" + permissions + "]";
	}

}
