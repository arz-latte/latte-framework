package at.arz.latte.framework.modules.dta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.modules.models.validator.CheckUrl;

@XmlRootElement(name = "submenus")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubMenusData implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "submenu")
	private List<SubMenuData> submenus;
	
	public SubMenusData() {
		this.submenus = new ArrayList<>();
	}

	public List<SubMenuData> getSubMenus() {
		return submenus;
	}

	public void setSubMenus(List<SubMenuData> submenus) {
		this.submenus = submenus;
	}

	public void addSubMenu(SubMenuData submenu) {
		this.submenus.add(submenu);
	}

}
