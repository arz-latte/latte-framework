package at.arz.latte.framework.modules.models;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import at.arz.latte.framework.modules.models.validator.CheckUrl;

/**
 * persistent entity for a module
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@NamedQueries({
		@NamedQuery(name = Module.QUERY_GETALL_BASE, query = "SELECT new at.arz.latte.framework.modules.dta.ModuleListData(m.id, m.name, m.provider, m.version, m.status, m.enabled) FROM Module m ORDER BY m.name"),
		@NamedQuery(name = Module.QUERY_GETALL, query = "SELECT m FROM Module m"),
		@NamedQuery(name = Module.UPDATE_ALL, query = "UPDATE Module m SET m.status = ModuleStatus.Stopped"), })
@Entity
@Table(name = "module")
public class Module extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL_BASE = "Module.GetAllBase";
	public static final String QUERY_GETALL = "Module.GetAll";
	public static final String UPDATE_ALL = "Module.UpdateAll";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @GeneratedValue(strategy = GenerationType.TABLE, generator = "Module.ID")
	// @TableGenerator(name = "Module.ID", table = "module_seq", pkColumnName =
	// "KEY", valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@NotNull
	@Size(min = 1, max = 63)
	private String provider;

	/**
	 * version of module
	 */
	@NotNull
	@Size(min = 1, max = 15)
	private String version;

	/**
	 * address of the module, used for checking module status, e.g.
	 * http://localhost:8080/Modul1/api/v1/module
	 */
	@NotNull
	@Size(min = 1, max = 255)
	@CheckUrl
	private String url;

	/**
	 * time in seconds for checking if module available, default 60s, if is set
	 * to 0s - checking is disabled
	 */
	@NotNull
	@Min(0)
	private int interval;

	@Enumerated(EnumType.STRING)
	private ModuleStatus status;

	private boolean enabled;

	// @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true, fetch =
	// FetchType.LAZY)
	// @JoinColumn(name = "module_id")

	/*
	 * @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true,
	 * fetch=FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "module_id") private Menu parentmenu;
	 */

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	@OrderColumn(name = "position")
	private List<MenuRoot> submenu;

	/**
	 * JPA consturctor
	 */
	protected Module() {
		super();
	}

	/**
	 * used for creation of new Module via REST-service
	 * 
	 * @param id
	 * @param i
	 * @param name
	 * @param provider
	 * @param version
	 * @param url
	 * @param interval
	 * @param status
	 * @param enabled
	 */
	public Module(String name, String provider, String url, int interval, boolean enabled) {
		this();
		this.name = name;
		this.provider = provider;
		this.version = "-";
		this.url = url;
		this.interval = interval;
		this.status = ModuleStatus.Stopped;
		this.enabled = enabled;
	}

	/**
	 * 
	 * used for partial updates via REST-service
	 * 
	 * @param name
	 * @param provider
	 * @param url
	 * @param checkInterval
	 * @param status
	 * @param enabled
	 */
	public Module(Long id, String name, String provider, String url, int interval, boolean enabled) {
		this();
		this.id = id;
		this.name = name;
		this.provider = provider;
		this.url = url;
		this.interval = interval;
		this.status = ModuleStatus.Stopped;
		this.enabled = enabled;
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

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public ModuleStatus getStatus() {
		return status;
	}

	public void setStatus(ModuleStatus status) {
		this.status = status;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<MenuRoot> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<MenuRoot> submenu) {
		this.submenu = submenu;
	}

	// ------------------- helper -------------------

	public String getHost() throws MalformedURLException {
		URL u = new URL(url);
		return u.getProtocol() + "://" + u.getAuthority();
	}

	public String getPath() throws MalformedURLException {
		URL u = new URL(url);
		return u.getPath();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Module) {
			Module module = (Module) obj;
			return module.getId() == this.getId();
		}

		return false;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", name=" + name + ", provider=" + provider + ", version=" + version + ", url="
				+ url + ", interval=" + interval + ", status=" + status + ", enabled=" + enabled + ", submenu="
				+ submenu + "]";
	}

}
