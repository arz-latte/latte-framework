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

	private Long id;

	private String name;

	private List<MenuRootData> menu;

	public ModuleMenuData() {
	}

	public ModuleMenuData(Long id, String name, List<MenuRootData> menu) {
		super();
		this.id = id;
		this.name = name;
		this.menu = menu;
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

	public List<MenuRootData> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuRootData> menu) {
		this.menu = menu;
	}

}
