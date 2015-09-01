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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * persistent entity for an user
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@NamedQueries({
		@NamedQuery(name = User.QUERY_GETALL_BASE, query = "SELECT new at.arz.latte.framework.restful.dta.UserData(u.id, u.firstName, u.lastName, u.username) FROM User u ORDER BY u.lastName, u.firstName"),
		@NamedQuery(name = User.QUERY_GETALL, query = "SELECT u FROM User u") })
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL_BASE = "User.GetAllBase";
	public static final String QUERY_GETALL = "User.GetAll";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "User.ID")
	@TableGenerator(name = "User.ID", table = "latte_seq", pkColumnName = "KEY", pkColumnValue = "User.ID", valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	@Column(name = "firstname")
	private String firstName;

	@NotNull
	@Size(min = 1, max = 63)
	@Column(name = "lastname")
	private String lastName;

	@NotNull
	@Size(min = 1, max = 63)
	private String username;

	@NotNull
	@Size(min = 1, max = 63)
	private String password;

	@ManyToMany
	private Set<Role> role = new HashSet<Role>();

	/**
	 * JPA consturctor
	 */
	public User() {
		super();
	}

	/**
	 * used for creation via REST-service or JUnit
	 */
	public User(String firstName, String lastName, String username, String password, Set<Role> role) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + "]";
	}

}
