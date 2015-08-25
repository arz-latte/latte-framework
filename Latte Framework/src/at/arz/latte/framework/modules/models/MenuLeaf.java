package at.arz.latte.framework.modules.models;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import at.arz.latte.framework.modules.dta.MenuEntryData;
import at.arz.latte.framework.modules.dta.MenuLeafData;

/**
 * Entity implementation class for Entity: MenuLeaf, represents a single bottom
 * menu with permission
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Embeddable
public class MenuLeaf implements Comparable<MenuLeaf> {

	@Embedded
	private MenuEntry entry;

	private String permission;

	public MenuLeaf() {
		super();
	}

	public MenuLeaf(MenuEntry entry, String permission) {
		super();
		this.entry = entry;
		this.permission = permission;
	}
	
	public MenuLeaf(MenuLeafData data) {
		super();
		this.entry = new MenuEntry(data.getEntry());
		this.permission = data.getPermission();
	}

	public MenuEntry getEntry() {
		return entry;
	}

	public void setEntry(MenuEntry entry) {
		this.entry = entry;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entry == null) ? 0 : entry.hashCode());
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
		MenuLeaf other = (MenuLeaf) obj;
		if (entry == null) {
			if (other.entry != null)
				return false;
		} else if (!entry.equals(other.entry))
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
		return "MenuLeaf [entry=" + entry + ", permission=" + permission + "]";
	}

	@Override
	public int compareTo(MenuLeaf object) {
		return entry.getPosition() - object.getEntry().getPosition();
	}
	
}
