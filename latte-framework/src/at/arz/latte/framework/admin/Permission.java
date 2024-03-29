package at.arz.latte.framework.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * persistent entity for a permission
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@NamedQueries({	@NamedQuery(name = Permission.QUERY_GETALL,
							query = "SELECT p FROM Permission p"),
				@NamedQuery(name = Permission.QUERY_GETALL_NAME,
							query = "SELECT p.name FROM Permission p"),
				@NamedQuery(name = Permission.QUERY_GET_NAME_BY_USER,
							query = "SELECT p.name FROM User u JOIN u.groups g JOIN g.permissions p WHERE u.email = :email"), })
@Entity
@Table(name = "permissions")
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL = "Permission.GetAll";
	public static final String QUERY_GETALL_NAME = "Permission.GetAllName";
	public static final String QUERY_GET_NAME_BY_USER = "Permission.GetNameByUser";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE,
					generator = "Permission.ID")
	@TableGenerator(name = "Permission.ID",
					table = "latte_seq",
					pkColumnName = "KEY",
					pkColumnValue = "Permission.ID",
					valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	@Column(unique = true)
	private String name;

	@Version
	private long version;

	/**
	 * JPA consturctor
	 */
	public Permission() {
		super();
	}

	/**
	 * used for creation via REST-service or JUnit
	 */
	public Permission(String name) {
		this();
		this.name = name;
	}

	/**
	 * used for creation via REST-service or JUnit
	 */
	public Permission(Long id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Permission other = (Permission) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + "]";
	}

}
