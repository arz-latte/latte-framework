package at.arz.latte.framework.modules.dta;

import java.util.ArrayList;
import java.util.List;

/**
 * represents a single menu item
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public class MenuItemData {

	private String value;

	private String permission;

	private int position;

	private List<MenuItemData> children;

	public MenuItemData() {
		super();
		this.children = new ArrayList<>();
	}

	public MenuItemData(String value, String permission, int position, List<MenuItemData> children) {
		super();
		this.value = value;
		this.permission = permission;
		this.position = position;
		this.children = children;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<MenuItemData> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItemData> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "MenuItemData [value=" + value + ", permission=" + permission + ", position=" + position + ", children="
				+ children + "]";
	}

}
