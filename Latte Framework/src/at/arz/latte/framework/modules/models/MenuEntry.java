package at.arz.latte.framework.modules.models;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import at.arz.latte.framework.modules.dta.MenuEntryData;
import at.arz.latte.framework.modules.dta.MenuLeafData;
import at.arz.latte.framework.modules.models.validator.CheckUrl;

/**
 * Entity implementation class for Entity: MenuEntry, represents a single menu
 * entry with url
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Embeddable
public class MenuEntry implements Serializable, Comparable<MenuEntry> {

	private static final long serialVersionUID = 1L;

	/**
	 * hint: value has to be same length as module-name
	 */
	@NotNull
	@Size(min = 1, max = 63)
	private String value;

	@CheckUrl
	private String href;

	private int position;

	public MenuEntry() {
		super();
	}

	public MenuEntry(String value, String href, int position) {
		super();
		this.value = value;
		this.href = href;
		this.position = position;
	}

	public MenuEntry(MenuEntryData entry) {
		super();
		this.value = entry.getValue();
		this.href = entry.getHref();
		this.position = entry.getPosition();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + position;
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		MenuEntry other = (MenuEntry) obj;
		if (position != other.position)
			return false;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuEntry [value=" + value + ", href=" + href + ", position=" + position + "]";
	}

	@Override
	public int compareTo(MenuEntry entry) {
		return position - entry.position;
	}
	
	public MenuEntryData toMenuEntryData() {
		return new MenuEntryData(value, href, position);
	}

}
