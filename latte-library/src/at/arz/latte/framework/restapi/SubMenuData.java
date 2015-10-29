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

@XmlRootElement(name = "submenu")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubMenuData implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@Size(max = 511)
	private String url;

	@Size(max = 511)
	private String script;

	@Size(max = 31)
	private String type;

	@Size(max = 31)
	private String group;

	@Size(max = 31)
	private String style;

	@Size(min = 1, max = 31)
	private String permission;

	private Boolean disabled;

	@XmlElement(name = "submenu")
	private List<SubMenuData> subMenus;

	public SubMenuData() {
		super();
		this.subMenus = new ArrayList<>();
	}

	/**
	 * constructor for JUnit
	 * 
	 * @param name
	 * @param url
	 * @param permission
	 */
	public SubMenuData(String name, String url, String permission) {
		this();
		this.name = name;
		this.url = url;
		this.permission = permission;
	}

	/**
	 * constructor for allowed menu entry
	 * 
	 * @param name
	 * @param url
	 * @param script
	 * @param type
	 * @param group
	 * @param style
	 */
	public SubMenuData(	String name,
						String url,
						String script,
						String type,
						String group,
						String style,
						Boolean disabled) {
		this();
		this.name = name;
		this.url = url;
		this.script = script;
		this.type = type;
		this.group = group;
		this.style = style;
		this.disabled = disabled;
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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
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

	@Override
	public String toString() {
		return "SubMenuData [name="+ name
				+ ", url="
				+ url
				+ ", script="
				+ script
				+ ", type="
				+ type
				+ ", group="
				+ group
				+ ", style="
				+ style
				+ ", permission="
				+ permission
				+ ", disabled="
				+ disabled
				+ ", subMenus="
				+ subMenus
				+ "]";
	}

}
