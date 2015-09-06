package at.arz.latte.framework.persistence.models;

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

import at.arz.latte.framework.restful.dta.SubMenuData;

@Entity
@Table(name = "submenus")
public class SubMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SubMenu.ID")
	@TableGenerator(name = "SubMenu.ID", table = "latte_seq", pkColumnName = "KEY", pkColumnValue = "SubMenu.ID", valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@Size(max = 511)
	@Column(length = 511)
	private String url;

	@Size(max = 511)
	@Column(length = 511)
	private String script;

	@Size(max = 31)
	private String type;

	@Size(max = 31)
	private String group;

	@ManyToOne
	private Permission permission;

	private Boolean disabled;

	@NotNull
	private int order;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "submenu_id")
	@OrderColumn(name = "order")
	private List<SubMenu> subMenus;

	@Version
	private int version;

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

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
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

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "SubMenu [name=" + name + ", permission=" + permission + ", subMenus=" + subMenus + "]";
	}

	// ----------------------- dta to entity -----------------------
	
	/**
	 * convert submenu data from REST to submenu entity
	 * 
	 * @return
	 */
	public static SubMenu getSubMenuRec(SubMenuData subMenuData, HashMap<String, Permission> permissions) {
		SubMenu subMenu = new SubMenu();
		subMenu.setName(subMenuData.getName());
		subMenu.setUrl(subMenuData.getUrl());
		subMenu.setScript(subMenuData.getScript());

		if (subMenuData.getPermission() != null) {

			// check if permission already exists
			Permission permission = permissions.get(subMenuData.getPermission());
			if (permission == null) {
				permission = new Permission(subMenuData.getPermission());
				permissions.put(permission.getName(), permission);
			}

			subMenu.setPermission(permission);
		}

		subMenu.setDisabled(subMenuData.getDisabled());
		subMenu.setType(subMenuData.getType());
		subMenu.setGroup(subMenuData.getGroup());

		List<SubMenuData> subMenusData = subMenuData.getSubMenus();
		if (subMenusData != null && !subMenusData.isEmpty()) {
			for (SubMenuData s : subMenusData) {
				subMenu.addSubMenu(getSubMenuRec(s, permissions));
			}
		}

		return subMenu;
	}

}