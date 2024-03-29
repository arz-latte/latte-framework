package at.arz.latte.framework.restapi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "menu")
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuData implements Serializable, Comparable<MenuData> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@NotNull
	@Size(max = 511)
	private String url;

	@NotNull
	private Integer order;

	/**
	 * default permission for whole module/menu
	 */
	@NotNull
	@Size(min = 1, max = 31)
	private String permission;

	@XmlElement(name = "submenu")
	private List<SubMenuData> subMenus;

	@XmlElement(name = "lastmodified")
	private Long lastModified;

	@Size(max = 31)
	private String style;

	public MenuData() {
		super();
		subMenus = new ArrayList<>();
	}

	public MenuData(String name, String url, Integer order, String style) {
		this();
		this.name = name;
		this.url = url;
		this.order = order;
		this.style = style;
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public List<SubMenuData> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SubMenuData> subMenus) {
		this.subMenus = subMenus;
	}

	public void addSubMenu(SubMenuData subMenu) {
		if (subMenu != null) {
			this.subMenus.add(subMenu);
		}
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "MenuData [name="+ name
				+ ", url="
				+ url
				+ ", order="
				+ order
				+ ", permission="
				+ permission
				+ ", subMenus="
				+ subMenus
				+ ", lastModified="
				+ lastModified
				+ ", style="
				+ style
				+ "]";
	}

	@Override
	public int compareTo(MenuData m) {

		if (this.getOrder() == null && m.getOrder() == null) {
			return 0;
		} else if (this.getOrder() == null) {
			return 1;
		} else if (m.getOrder() == null) {
			return -1;
		}

		return this.getOrder().compareTo(m.getOrder());
	}
}
