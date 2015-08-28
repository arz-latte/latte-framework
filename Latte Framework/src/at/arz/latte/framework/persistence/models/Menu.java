package at.arz.latte.framework.persistence.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import at.arz.latte.framework.restful.dta.MenuData;
import at.arz.latte.framework.restful.dta.SubMenuData;
import at.arz.latte.framework.validator.CheckUrl;

@Entity
@Table(name = "menu")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Menu.ID")
	@TableGenerator(name = "Menu.ID", table = "latte_seq", pkColumnName = "KEY", pkColumnValue = "Menu.ID", valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@CheckUrl
	@NotNull
	@Size(max = 511)
	private String url;

	@NotNull
	private int order;

	private Integer subOrder;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	@OrderColumn(name = "order")
	private List<SubMenu> subMenus;

	@NotNull
	@Column(name = "lastmodified")
	private Long lastModified;

	/**
	 * JPA consturctor
	 */
	public Menu() {
		super();
		subMenus = new ArrayList<>();
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

	public List<SubMenu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SubMenu> subMenus) {
		this.subMenus = subMenus;
	}
	
	public void addSubMenu(SubMenu subMenu) {
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
		return "Menu [id=" + id + ", name=" + name + ", url=" + url + ", order=" + order + ", subOrder=" + subOrder
				+ ", subMenus=" + subMenus + ", lastModified=" + lastModified + "]";
	}

	// ----------------------- entity to dta -----------------------

	/**
	 * convert menu entity to menu data for REST
	 * 
	 * @return
	 */
	public MenuData getMenuData() {
		MenuData menuData = new MenuData(name, url, order, subOrder);

		if (subMenus != null && !subMenus.isEmpty()) {
			for (SubMenu submenu : subMenus) {
				menuData.addSubMenu(submenu.getSubMenuDataRec());
			}
		}

		return menuData;
	}
	
	/**
	 * convert menu data from REST to menu entity
	 * @return
	 */	
	public static Menu getMenuRec(MenuData menuData) {
		Menu menu = new Menu();
		menu.setName(menuData.getName());
		menu.setUrl(menuData.getUrl());
		menu.setOrder(menuData.getOrder());
		menu.setSubOrder(menuData.getSubOrder());
		menu.setLastModified(menuData.getLastModified());
		
		List<SubMenuData> subMenusData = menuData.getSubMenus();
		if (subMenusData != null && !subMenusData.isEmpty()) {
			for (SubMenuData s : subMenusData) {
				menu.addSubMenu(SubMenu.getSubMenuRec(s));
			}
		}
		
		return menu;
	}
	
}
