package at.arz.latte.framework.modules.dta;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a single top menu with attached submenu-entries
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "menu")
public class MenuRootData {

	private MenuEntryData entry;

	private List<MenuLeafData> children;

	public MenuRootData() {
		super();
		this.children = new ArrayList<>();
	}

	public MenuRootData(MenuEntryData entry, List<MenuLeafData> children) {
		super();
		this.entry = entry;
		this.children = children;
	}

	public MenuEntryData getEntry() {
		return entry;
	}

	public void setEntry(MenuEntryData entry) {
		this.entry = entry;
	}

	public List<MenuLeafData> getChildren() {
		return children;
	}

	public void setChildren(List<MenuLeafData> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "MenuRootData [entry=" + entry + ", children=" + children + "]";
	}

	

}
