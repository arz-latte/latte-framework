package at.arz.latte.framework.restful.dta;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * used to transmit user data to the client, for single view or update
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserData {

	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String firstName;

	@NotNull
	@Size(min = 1, max = 63)
	private String lastName;

	@NotNull
	@Size(min = 1, max = 63)
	private String username;

	@NotNull
	@Size(min = 1, max = 63)
	private String password;

	public UserData() {
	}

	/**
	 * constructor for REST list view
	 * 
	 * @param id
	 * @param name
	 * @param provider
	 * @param enabled
	 * @param running
	 * @param lastModified
	 */
	public UserData(Long id, String firstName, String lastName, String username) {
		this();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + "]";
	}

}
