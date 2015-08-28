package at.arz.latte.framework.persistence.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import at.arz.latte.framework.restful.dta.SubMenuData;
import at.arz.latte.framework.validator.CheckUrl;

@Entity
@Table(name = "submenu")
public class SubMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SubMenu.ID")
	@TableGenerator(name = "SubMenu.ID", table = "latte_seq", pkColumnName = "KEY", pkColumnValue = "SubMenu.ID", valueColumnName = "VALUE")
	private Long id;

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

	@NotNull
	private int order;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "submenu_id")
	@OrderColumn(name = "order")
	private List<SubMenu> subMenus;

	/**
	 * JPA consturctor
	 */
	public SubMenu() {
		super();
		this.subMenus = new ArrayList<>();
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

	public List<SubMenu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SubMenu> subMenus) {
		this.subMenus = subMenus;
	}

	public void addSubMenu(SubMenu subMenu) {
		this.subMenus.add(subMenu);
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "SubMenu [id=" + id + ", name=" + name + ", url=" + url + ", type=" + type + ", group=" + group
				+ ", permission=" + permission + ", order=" + order + ", subMenus=" + subMenus + "]";
	}

	// ----------------------- entity to dta -----------------------

	/**
	 * recursive convert submenu entity to submenu data for REST
	 * 
	 * @return
	 */
	public SubMenuData getSubMenuDataRec() {

		SubMenuData subMenuData = new SubMenuData(name, url, type, group, permission);

		if (subMenus != null && !subMenus.isEmpty()) {
			for (SubMenu subMenu : subMenus) {
				subMenuData.addSubMenu(subMenu.getSubMenuDataRec());
			}
		}

		return subMenuData;
	}

	/**
	 * convert submenu data from REST to submenu entity
	 * 
	 * @return
	 */
	public static SubMenu getSubMenuRec(SubMenuData subMenuData) {
		SubMenu subMenu = new SubMenu();
		subMenu.setName(subMenuData.getName());
		subMenu.setUrl(subMenuData.getUrl());
		subMenu.setPermission(subMenuData.getPermission());
		subMenu.setType(subMenuData.getType());
		subMenu.setGroup(subMenuData.getGroup());

		List<SubMenuData> subMenusData = subMenuData.getSubMenus();
		if (subMenusData != null && !subMenusData.isEmpty()) {
			for (SubMenuData s : subMenusData) {
				subMenu.addSubMenu(getSubMenuRec(s));
			}
		}

		return subMenu;
	}

}