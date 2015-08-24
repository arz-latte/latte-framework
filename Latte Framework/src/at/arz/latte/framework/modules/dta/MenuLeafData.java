package at.arz.latte.framework.modules.dta;

/**
 * represents a single menu entry with permission
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public class MenuLeafData {

	private MenuEntryData entry;

	private String permission;

	public MenuLeafData() {
		super();
	}

	public MenuLeafData(MenuEntryData entry, String permission) {
		super();
		this.entry = entry;
		this.permission = permission;
	}

	/**
	 * constructor for transmitting data to client (ignore permission)
	 * 
	 * @param entry
	 * @param permission
	 */
	public MenuLeafData(MenuEntryData entry) {
		super();
		this.entry = entry;
	}

	public MenuEntryData getEntry() {
		return entry;
	}

	public void setEntry(MenuEntryData entry) {
		this.entry = entry;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "MenuLeafData [entry=" + entry + ", permission=" + permission + "]";
	}

}
