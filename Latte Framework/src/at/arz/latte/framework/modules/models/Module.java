package at.arz.latte.framework.modules.models;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * module entity
 * 
 * @author Dominik
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = Module.QUERY_GETALL_BASE, query = "SELECT new at.arz.latte.framework.modules.dta.ModuleBaseData(m.id, m.name, m.version, m.status, m.enabled) FROM Module m ORDER BY m.name"),
		@NamedQuery(name = Module.QUERY_GETALL, query = "SELECT m FROM Module m") })
@XmlRootElement(name = "module")
public class Module extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL_BASE = "Module.GetAllBase";
	public static final String QUERY_GETALL = "Module.GetAll";

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Module.ID")
	@TableGenerator(name = "Module.ID", table = "LATTESEQ", pkColumnName = "KEY", valueColumnName = "VALUE")
	protected Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

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
		// jpa constructor
	}

	public Module(Long id, String name, String version, String url, int checkInterval, ModuleStatus status, boolean enabled) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.version = version;
		this.url = url;
		this.checkInterval = checkInterval;
		this.enabled = enabled;
	}

	public Module(String name, String version, String url, int checkInterval, ModuleStatus status, boolean enabled) {
		this.name = name;
		this.status = status;
		this.version = version;
		this.url = url;
		this.checkInterval = checkInterval;
		this.enabled = enabled;
	}

	public Module(Module m) {
		this.id = m.id;
		this.name = m.name;
		this.status = m.status;
		this.version = m.version;
		this.url = m.url;
		this.checkInterval = m.checkInterval;
		this.enabled = m.enabled;
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
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
		return "Module [id=" + id + ", name=" + name + ", version=" + version + ", url=" + url + ", checkInterval="
				+ checkInterval + ", status=" + status + ", enabled=" + enabled + "]";
	}

}
