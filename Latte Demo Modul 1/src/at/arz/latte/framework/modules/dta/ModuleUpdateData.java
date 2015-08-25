package at.arz.latte.framework.modules.dta;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * used to transmit module update data to the server
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "module_update")
public class ModuleUpdateData {

	private String version;

	private MenuLeafData mainMenu;

	private List<MenuRootData> subMenu;

	public ModuleUpdateData() {
	}

	public ModuleUpdateData(String version, MenuLeafData mainMenu, List<MenuRootData> subMenu) {
		super();
		this.version = version;
		this.mainMenu = mainMenu;
		this.subMenu = subMenu;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public MenuLeafData getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MenuLeafData mainMenu) {
		this.mainMenu = mainMenu;
	}

	public List<MenuRootData> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<MenuRootData> subMenu) {
		this.subMenu = subMenu;
	}

	@Override
	public String toString() {
		return "ModuleUpdateData [version=" + version + ", mainMenu=" + mainMenu + ", subMenu=" + subMenu + "]";
	}

}
