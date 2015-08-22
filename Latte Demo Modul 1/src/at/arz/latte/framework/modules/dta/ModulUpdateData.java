package at.arz.latte.framework.modules.dta;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * used to transmit module update data to the client
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "module_update")
public class ModulUpdateData {

	private String version;

	private List<MenuItemData> menu;

	public ModulUpdateData() {
	}

	public ModulUpdateData(String version, List<MenuItemData> menu) {
		super();
		this.version = version;
		this.menu = menu;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<MenuItemData> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuItemData> menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "ModulUpdateData [version=" + version + ", menu=" + menu + "]";
	}	
}
