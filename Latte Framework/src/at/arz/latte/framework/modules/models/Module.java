package at.arz.latte.framework.modules.models;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.modules.models.validator.CheckUrl;

/**
 * persistent entity for a module
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = Module.QUERY_GETALL_BASE, query = "SELECT new at.arz.latte.framework.modules.dta.ModuleMultipleData(m.id, m.name, m.provider, m.version, m.status, m.enabled) FROM Module m ORDER BY m.name"),
		@NamedQuery(name = Module.QUERY_GETALL, query = "SELECT m FROM Module m"),
		@NamedQuery(name = Module.UPDATE_ALL, query = "UPDATE Module m SET m.status = ModuleStatus.Unknown")
})
@XmlRootElement(name = "module")
public class Module extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL_BASE = "Module.GetAllBase";
	public static final String QUERY_GETALL = "Module.GetAll";
	public static final String UPDATE_ALL = "Module.UpdateAll";

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

	protected Module() {
		super();
		// jpa constructor
	}

	public Module(Long id, String name, String provider, String version, String url, int interval, ModuleStatus status, boolean enabled) {
		super(id);
		this.name = name;
		this.provider = provider;
		this.status = status;
		this.version = version;
		this.url = url;
		this.interval = interval;
		this.enabled = enabled;
	}
	
	/**
	 * 
	 * used for partial updates
	 * 
	 * @param name
	 * @param provider
	 * @param version
	 * @param url
	 * @param checkInterval
	 * @param status
	 * @param enabled
	 */
	public Module(Long id, String name, String provider, String url, int interval, boolean enabled) {
		super(id);
		this.name = name;
		this.provider = provider;
		this.url = url;
		this.interval = interval;
		this.enabled = enabled;
	}
	
	public Module(String name, String provider, String version, String url, int interval, ModuleStatus status, boolean enabled) {
		super();
		this.name = name;
		this.provider = provider;
		this.status = status;
		this.version = version;
		this.url = url;
		this.interval = interval;
		this.enabled = enabled;
	}

	public Module(Module m) {
		super(m.id);
		this.name = m.name;
		this.provider = m.provider;
		this.status = m.status;
		this.version = m.version;
		this.url = m.url;
		this.interval = m.interval;
		this.enabled = m.enabled;
	}

	// ------------------ helper ------------------

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

	public String getHost() throws MalformedURLException {
		URL u = new URL(url);
		return u.getProtocol() + "://" + u.getAuthority();
	}

	public String getPath() throws MalformedURLException {
		URL u = new URL(url);
		return u.getPath();
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", name=" + name + ", provider=" + provider + ", version=" + version + ", url=" + url + ", checkInterval="
				+ interval + ", status=" + status + ", enabled=" + enabled + "]";
	}

}
