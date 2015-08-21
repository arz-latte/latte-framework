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
 * module entity
 * 
 * @author Dominik
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = Module.QUERY_GETALL_BASE, query = "SELECT new at.arz.latte.framework.modules.dta.ModuleBaseData(m.id, m.name, m.provider, m.version, m.status, m.enabled) FROM Module m ORDER BY m.name"),
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
	private int checkInterval;

	@Enumerated(EnumType.STRING)
	private ModuleStatus status;

	private boolean enabled;

	protected Module() {
		super();
		// jpa constructor
	}

	public Module(Long id, String name, String provider, String version, String url, int checkInterval, ModuleStatus status, boolean enabled) {
		super(id);
		this.name = name;
		this.provider = provider;
		this.status = status;
		this.version = version;
		this.url = url;
		this.checkInterval = checkInterval;
		this.enabled = enabled;
	}

	public Module(String name, String provider, String version, String url, int checkInterval, ModuleStatus status, boolean enabled) {
		super();
		this.name = name;
		this.provider = provider;
		this.status = status;
		this.version = version;
		this.url = url;
		this.checkInterval = checkInterval;
		this.enabled = enabled;
	}

	public Module(Module m) {
		super(m.id);
		this.name = m.name;
		this.provider = m.provider;
		this.status = m.status;
		this.version = m.version;
		this.url = m.url;
		this.checkInterval = m.checkInterval;
		this.enabled = m.enabled;
	}

	public String getName() {
		return name;
	}

	public String getProvider() {
		return provider;
	}
	
	public ModuleStatus getStatus() {
		return status;
	}

	public void setStatus(ModuleStatus status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public String getUrl() {
		return url;
	}

	public int getCheckInterval() {
		return checkInterval;
	}

	public boolean getEnabled() {
		return enabled;
	}

	// ------------------ helper ------------------

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
				+ checkInterval + ", status=" + status + ", enabled=" + enabled + "]";
	}

}
