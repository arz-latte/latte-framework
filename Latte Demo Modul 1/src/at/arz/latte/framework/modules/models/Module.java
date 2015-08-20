package at.arz.latte.framework.modules.models;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
		@NamedQuery(name = Module.QUERY_GETALL_BASE, query = "SELECT new at.arz.latte.framework.modules.dta.ModuleBaseData(m.id, m.name, m.version, m.status, m.enabled) FROM Module m ORDER BY m.name"),
		@NamedQuery(name = Module.QUERY_GETALL, query = "SELECT m FROM Module m") })
@XmlRootElement(name = "module")
public class Module extends AEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL_BASE = "Module.GetAllBase";
	public static final String QUERY_GETALL = "Module.GetAll";

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

	@Transient
	private HashMap<String, String> validation;

	public Module() {
		super();
	}

	public Module(int id, String name, String version, String url, int checkInterval, ModuleStatus status,
			boolean enabled) {
		super(id);
		this.name = name;
		this.status = status;
		this.version = version;
		this.url = url;
		this.checkInterval = checkInterval;
		this.enabled = enabled;
	}

	public Module(Module m) {
		super(m.id);
		this.name = m.name;
		this.status = m.status;
		this.version = m.version;
		this.url = m.url;
		this.checkInterval = m.checkInterval;
		this.enabled = m.enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCheckInterval() {
		return checkInterval;
	}

	public void setCheckInterval(int checkInterval) {
		this.checkInterval = checkInterval;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	// ------------------ helper ------------------

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public void setValidation(HashMap<String, String> validation) {
		this.validation = validation;
	}

	public boolean isSaveable() {
		return validation == null || validation.isEmpty();
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
		return "Module [id=" + id + ", name=" + name + ", version=" + version + ", url=" + url + ", checkInterval="
				+ checkInterval + ", status=" + status + ", enabled=" + enabled + "]";
	}

}
