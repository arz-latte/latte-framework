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

	/**
	 * module id
	 */
	private Long id;

	/**
	 * single main menu entry
	 */
	private MenuLeafData mainMenu;

	/**
	 * list of sub menu entries
	 */
	private List<MenuRootData> subMenu;

	public ModuleMenuData() {
	}

	public ModuleMenuData(Long id, MenuLeafData mainMenu, List<MenuRootData> subMenu) {
		super();
		this.id = id;
		this.mainMenu = mainMenu;
		this.subMenu = subMenu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
