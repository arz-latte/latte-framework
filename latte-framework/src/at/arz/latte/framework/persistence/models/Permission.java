package at.arz.latte.framework.persistence.models;

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
@NamedQueries({
		@NamedQuery(name = Permission.QUERY_GETALL_BASE, query = "SELECT new at.arz.latte.framework.restful.dta.PermissionData(p.id, p.name) FROM Permission p ORDER BY p.name"),
		@NamedQuery(name = Permission.QUERY_GET_BY_NAME, query = "SELECT p FROM Permission p WHERE p.name = :name"),
		@NamedQuery(name = Permission.QUERY_GET_BY_USER_AND_PERMISSION, query = "SELECT p FROM User u JOIN u.role r JOIN r.permission p WHERE u.email = :email AND p.name = :permission"),
		@NamedQuery(name = Permission.QUERY_GET_NAME_BY_USER, query = "SELECT DISTINCT p.name FROM User u JOIN u.role r JOIN r.permission p WHERE u.email = :email"), })
@Entity
@Table(name = "permissions")
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL_BASE = "Permission.GetAllBase";
	public static final String QUERY_GET_BY_NAME = "Permission.GetByName";
	public static final String QUERY_GET_BY_USER_AND_PERMISSION = "Permission.GetByUserAndPermission";
	public static final String QUERY_GET_NAME_BY_USER = "Permission.GetByUser";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Permission.ID")
	@TableGenerator(name = "Permission.ID", table = "latte_seq", pkColumnName = "KEY", pkColumnValue = "Permission.ID", valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	@Column(unique = true)
	private String name;

	@Version
	private int version;

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
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + "]";
	}

}
