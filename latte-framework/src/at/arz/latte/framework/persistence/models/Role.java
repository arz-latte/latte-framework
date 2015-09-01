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
		@NamedQuery(name = Role.QUERY_GETALL, query = "SELECT r FROM Role r") })
@Entity
@Table(name = "roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL_BASE = "Role.GetAllBase";
	public static final String QUERY_GETALL = "Role.GetAll";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Role.ID")
	@TableGenerator(name = "Role.ID", table = "latte_seq", pkColumnName = "KEY", pkColumnValue = "Role.ID", valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

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

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

}
