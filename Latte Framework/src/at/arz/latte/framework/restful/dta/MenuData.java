package at.arz.latte.framework.restful.dta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.validator.CheckUrl;

@XmlRootElement(name = "menu")
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuData implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@CheckUrl
	@NotNull
	@Size(max = 511)
	private String url;

	@NotNull
	private int order;

	@XmlElement(name = "suborder")
	private Integer subOrder;

	private Boolean denied;

	@XmlElement(name = "submenu")
	private List<SubMenuData> subMenus;

	@XmlElement(name = "lastmodified")
	private Long lastModified;

	public MenuData() {
		super();
		subMenus = new ArrayList<>();
	}

	public MenuData(String name, String url, int order) {
		this();
		this.name = name;
		this.url = url;
		this.order = order;
	}

	public MenuData(String name, String url, int order, Integer subOrder) {
		this(name, url, order);
		this.subOrder = subOrder;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Integer getSubOrder() {
		return subOrder;
	}

	public void setSubOrder(Integer subOrder) {
		this.subOrder = subOrder;
	}

	public Boolean getDenied() {
		return denied;
	}

	public void setDenied(Boolean denied) {
		this.denied = denied;
	}

	public List<SubMenuData> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SubMenuData> subMenus) {
		this.subMenus = subMenus;
	}

	public void addSubMenu(SubMenuData subMenu) {
		this.subMenus.add(subMenu);
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "MenuData [name=" + name + ", url=" + url + ", order=" + order + ", subOrder=" + subOrder + ", denied="
				+ denied + ", subMenus=" + subMenus + ", lastModified=" + lastModified + "]";
	}

}
