package at.arz.latte.framework.dta;

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

@XmlRootElement(name = "submenu")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubMenuData implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@CheckUrl
	@NotNull
	@Size(max = 511)
	private String url;

	@Size(max = 31)
	private String type;

	@Size(max = 31)
	private String group;

	@Size(max = 31)
	private String permission;

	private Boolean denied;

	@XmlElement(name="submenu")
	private List<SubMenuData> submenus;

	public SubMenuData() {
		super();
		this.submenus = new ArrayList<>();
	}

	public SubMenuData(String name, String url, String permission) {
		this();
		this.name = name;
		this.url = url;
		this.permission = permission;
	}

	public SubMenuData(String name, String url, String type, String group, String permission) {
		this(name, url, permission);
		this.type = type;
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Boolean isDenied() {
		return denied;
	}

	public void setDenied(Boolean denied) {
		this.denied = denied;
	}

	public List<SubMenuData> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<SubMenuData> submenus) {
		this.submenus = submenus;
	}

	public void addSubMenu(SubMenuData submenu) {
		this.submenus.add(submenu);
	}

}
