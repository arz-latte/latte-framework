package at.arz.latte.framework.module;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({	@NamedQuery(name = Module.QUERY_GETALL,
							query = "SELECT m FROM Module m"),
				@NamedQuery(name = Module.QUERY_GETALL_ENABLED,
							query = "SELECT m FROM Module m WHERE m.enabled=true"),
				@NamedQuery(name = Module.QUERY_GET_BY_NAME,
							query = "SELECT m FROM Module m WHERE m.name = :name"),
				@NamedQuery(name = Module.QUERY_GET_BY_URL,
							query = "SELECT m FROM Module m WHERE m.url = :url"),
				@NamedQuery(name = Module.STOP_ALL,
							query = "UPDATE Module m SET m.running = false, m.lastModified = null"), })
@Entity
@Table(name = "modules")
public class Module implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL = "Module.GetAll";
	public static final String QUERY_GETALL_ENABLED = "Module.GetAllEnabled";
	public static final String QUERY_GET_BY_NAME = "Module.GetByName";
	public static final String QUERY_GET_BY_URL = "Module.GetByUrl";
	public static final String STOP_ALL = "Module.StopAll";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Module.ID")
	@TableGenerator(name = "Module.ID",
					table = "latte_seq",
					pkColumnName = "KEY",
					pkColumnValue = "Module.ID",
					valueColumnName = "VALUE")
	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@NotNull
	@Size(min = 1, max = 63)
	private String provider;

	/**
	 * relative address of the REST-service of the module, used for checking
	 * module status, e.g. /Modul1/api/v1/module
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
	private Boolean running = false;

	/**
	 * enable or disable checking of this module
	 */
	@NotNull
	private Boolean enabled = false;

	/**
	 * duplicate of value in current menu entity (reduces joins)
	 */
	@Column(name = "lastmodified")
	private Long lastModified;

	@Version
	private long version;

	/**
	 * JPA consturctor
	 */
	public Module() {
		super();
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

	@Override
	public String toString() {
		return "Module [id="+ id
				+ ", name="
				+ name
				+ ", provider="
				+ provider
				+ ", url="
				+ url
				+ ", interval="
				+ interval
				+ ", running="
				+ running
				+ ", enabled="
				+ enabled
				+ ", lastModified="
				+ lastModified
				+ "]";
	}

}
