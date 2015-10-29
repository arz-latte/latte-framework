package at.arz.latte.framework.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import at.arz.latte.framework.admin.Permission;
import at.arz.latte.framework.restapi.MenuData;
import at.arz.latte.framework.restapi.SubMenuData;

@Entity
@Table(name = "menus")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Menu.ID")
	@TableGenerator(name = "Menu.ID",
					table = "latte_seq",
					pkColumnName = "KEY",
					pkColumnValue = "Menu.ID",
					valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@NotNull
	@Size(max = 511)
	private String url;

	@NotNull
	private int order;

	@ManyToOne
	private Permission permission;

	@OneToMany(	cascade = CascadeType.ALL,
				orphanRemoval = true,
				fetch = FetchType.EAGER)
	@JoinColumn(name = "menu_id")
	@OrderColumn(name = "order")
	private List<SubMenu> subMenus;

	@NotNull
	@Column(name = "lastmodified")
	private Long lastModified;
	
	/**
	 * css class attribute
	 */
	@Size(max = 31)
	private String style;

	@Version
	private long version;

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

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public List<SubMenu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SubMenu> subMenus) {
		this.subMenus = subMenus;
	}

	public void addSubMenu(SubMenu subMenu) {
		subMenu.setOrder(this.subMenus.size());
		this.subMenus.add(subMenu);
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@Override
	public String toString() {
		return "Menu [id="+ id
				+ ", name="
				+ name
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

	// ----------------------- dta to entity -----------------------

	/**
	 * convert menu data from REST to menu entity
	 * 
	 * @return
	 */
	public static Menu getMenuRec(MenuData menuData) {

		// replace unique permission objects
		HashMap<String, Permission> permissions = new HashMap<>();

		Menu menu = new Menu();
		menu.setName(menuData.getName());
		menu.setUrl(menuData.getUrl());
		menu.setOrder(menuData.getOrder());
		menu.setStyle(menuData.getStyle());
		if (menuData.getPermission() != null) {
			Permission p = new Permission(menuData.getPermission());
			permissions.put(p.getName(), p);
			menu.setPermission(p);
		}
		menu.setLastModified(menuData.getLastModified());

		List<SubMenuData> subMenusData = menuData.getSubMenus();
		if (subMenusData != null && !subMenusData.isEmpty()) {
			for (SubMenuData s : subMenusData) {
				menu.addSubMenu(SubMenu.getSubMenuRec(s, permissions));
			}
		}

		return menu;
	}

}
