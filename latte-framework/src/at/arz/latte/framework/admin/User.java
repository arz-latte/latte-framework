package at.arz.latte.framework.admin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import at.arz.latte.framework.validator.EMail;

/**
 * persistent entity for an user
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@NamedQueries({	@NamedQuery(name = User.QUERY_GETALL,
							query = "SELECT u FROM User u"),
				@NamedQuery(name = User.QUERY_GET_BY_EMAIL,
							query = "SELECT u FROM User u WHERE u.email = :email") })
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	static final String QUERY_GETALL = "User.GetAll";
	static final String QUERY_GET_BY_EMAIL = "User.GetByEmail";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "User.ID")
	@TableGenerator(name = "User.ID",
					table = "latte_seq",
					pkColumnName = "KEY",
					pkColumnValue = "User.ID",
					valueColumnName = "VALUE")
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
	@EMail
	@Column(unique = true)
	@Size(min = 1, max = 63)
	private String email;

	@NotNull
	@Size(min = 1, max = 63)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "users_groups",
	joinColumns = { @JoinColumn(name = "user_id",
								referencedColumnName = "id") },
	inverseJoinColumns = { @JoinColumn(	name = "group_id",
										referencedColumnName = "id") })	
	private Set<Group> groups = new HashSet<Group>();

	@Version
	private long version;

	/**
	 * JPA consturctor
	 */
	public User() {
		super();
	}

	/**
	 * used for creation via REST-service or JUnit
	 */
	public User(String firstName,
				String lastName,
				String email,
				String password) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void addGroup(Group group) {
		Objects.requireNonNull(group);
		this.groups.add(group);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
					+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
					+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
					+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id="+ id
				+ ", firstName="
				+ firstName
				+ ", lastName="
				+ lastName
				+ ", email="
				+ email
				+ ", password="
				+ password
				+ "]";
	}

}
