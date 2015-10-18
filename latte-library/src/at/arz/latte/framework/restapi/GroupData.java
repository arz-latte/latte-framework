package at.arz.latte.framework.restapi;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * used to transmit group data to the client, for single view or update
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "group")
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupData {

	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	private Set<PermissionData> permission = new HashSet<PermissionData>();

	public GroupData() {
	}

	/**
	 * constructor for REST list view
	 * 
	 * @param id
	 * @param name
	 */
	public GroupData(Long id, String name) {
		this();
		this.id = id;
		this.name = name;
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

	public Set<PermissionData> getPermission() {
		return permission;
	}

	public void setPermission(Set<PermissionData> permission) {
		this.permission = permission;
	}

	public void addPermission(PermissionData permission) {
		this.permission.add(permission);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
					+ ((permission == null) ? 0 : permission.hashCode());
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
		GroupData other = (GroupData) obj;
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
		return "RoleData [id=" + id
				+ ", name="
				+ name
				+ ", permission="
				+ permission
				+ "]";
	}

}
