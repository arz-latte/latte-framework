package at.arz.latte.framework.modules.dta;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.modules.models.validator.CheckUrl;

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

	private Integer order;

	private Integer suborder;

	private Boolean denied;

	//private List<SubMenuData> submenu;
	private SubMenusData submenus;
	
	private Long lastmodified;

	public MenuData() {
		super();
		//submenu = new ArrayList<>();
	}

	public MenuData(String name, String url) {
		this();
		this.name = name;
		this.url = url;
	}

	public MenuData(String name, String url, Integer order) {
		this(name, url);
		this.order = order;
	}

	public MenuData(String name, String url, Integer order, Integer suborder) {
		this(name, url, order);
		this.suborder = suborder;
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

	public Integer getSuborder() {
		return suborder;
	}

	public void setSuborder(Integer suborder) {
		this.suborder = suborder;
	}

	public Boolean getDenied() {
		return denied;
	}

	public void setDenied(Boolean denied) {
		this.denied = denied;
	}
/*
	public List<SubMenuData> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<SubMenuData> submenu) {
		this.submenu = submenu;
	}

	public void addSubMenu(SubMenuData submenu) {
		this.submenu.add(submenu);
	}
*/
	
	
	public Long getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(Long lastmodified) {
		this.lastmodified = lastmodified;
	}


	public SubMenusData getSubmenus() {
		return submenus;
	}

	public void setSubmenus(SubMenusData submenus) {
		this.submenus = submenus;
	}

}
