package at.arz.latte.framework.modules.dta;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * used to transmit module and menu data to the client
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "module")
public class ModuleMenuData {

	private String name;
	private List<MenuRootData> menu;

	public ModuleMenuData() {
	}

	public ModuleMenuData(String name, List<MenuRootData> menu) {
		super();
		this.name = name;
		this.menu = menu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MenuRootData> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuRootData> menu) {
		this.menu = menu;
	}

}
