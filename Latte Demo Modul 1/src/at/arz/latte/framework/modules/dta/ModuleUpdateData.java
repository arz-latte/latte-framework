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
public class ModuleUpdateData {

	private String version;

	private List<MenuRootData> menu;

	public ModuleUpdateData() {
	}

	public ModuleUpdateData(String version, List<MenuRootData> menu) {
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

	public List<MenuRootData> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuRootData> menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "ModuleUpdateData [version=" + version + ", menu=" + menu + "]";
	}	
}
