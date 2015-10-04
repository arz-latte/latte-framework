package at.arz.latte.framework.persistence.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * persistent entity for a module
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@NamedQueries({
		@NamedQuery(name = Module.QUERY_GETALL_BASE, query = "SELECT new at.arz.latte.framework.restful.dta.ModuleData(m.id, m.name, m.provider, m.running, m.enabled, m.lastModified) FROM Module m LEFT JOIN m.menu me ORDER BY me.order"),
		@NamedQuery(name = Module.QUERY_GETALL, query = "SELECT m FROM Module m"),
		@NamedQuery(name = Module.QUERY_GETALL_ENABLED, query = "SELECT m FROM Module m WHERE m.enabled=true AND m.id > 0"),
		@NamedQuery(name = Module.QUERY_GETALL_ENABLED_SORTED, query = "SELECT m FROM Module m WHERE m.enabled=true ORDER BY m.menu.order"),
		@NamedQuery(name = Module.STOP_ALL, query = "UPDATE Module m SET m.running = false"), })
@Entity
@Table(name = "modules")
public class Module implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL_BASE = "Module.GetAllBase";
	public static final String QUERY_GETALL = "Module.GetAll";
	public static final String QUERY_GETALL_ENABLED = "Module.GetAllEnabled";
	public static final String QUERY_GETALL_ENABLED_SORTED = "Module.GetAllEnabledSorted";
	public static final String STOP_ALL = "Module.StopAll";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Module.ID")
	@TableGenerator(name = "Module.ID", table = "latte_seq", pkColumnName = "KEY", pkColumnValue = "Module.ID", valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@NotNull
	@Size(min = 1, max = 63)
	private String provider;

	/**
	 * relative address of the REST-service of the module, used for checking module
	 * status, e.g. /Modul1/api/v1/module
	 */
	@NotNull
	@Size(max = 511)
	private String url;

	/**
	 * time in seconds for checking if module available, default 60s, if is set
	 * to 0s - checking is disabled
	 */
	@Min(1)
	private Integer interval;

	/**
	 * currrent status of module
	 */
	@NotNull
	private Boolean running;

	/**
	 * enable or disable checking of this module
	 */
	@NotNull
	private Boolean enabled;

	/**
	 * duplicate of value in current menu entity (reduces joins)
	 */
	@Column(name = "lastmodified")
	private Long lastModified;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@Version
	private long version;

	/**
	 * JPA consturctor
	 */
	public Module() {
		super();
	}

	/**
	 * used for creation via REST-service or JUnit
	 */
	public Module(String name, String provider, String url, int interval, Boolean enabled) {
		this();
		this.name = name;
		this.provider = provider;
		this.url = url;
		this.interval = interval;
		this.enabled = enabled;
		this.running = false;
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

	public Boolean getRunning() {
		return running;
	}

	public void setRunning(Boolean running) {
		this.running = running;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", name=" + name + ", provider=" + provider + ", url=" + url + ", interval="
				+ interval + ", running=" + running + ", enabled=" + enabled + ", lastModified=" + lastModified
				+ ", menu=" + menu + "]";
	}

}
