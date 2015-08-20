package at.arz.latte.framework.modules.models;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Size(min = 2, max = 63)
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
	@Size(max = 255)
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

	//@TypeConverter(dataType = Integer.class, name = "enabled")
	private boolean enabled;

	@Transient
	private HashMap<String, String> violationMessages;

	public Module() {
		super();
	}

	public Module(int id, String name, String version, String url,
			int checkInterval, ModuleStatus status, boolean enabled) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.version = version;
		this.url = url;
		this.checkInterval = checkInterval;
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public HashMap<String, String> getViolationMessages() {
		return violationMessages;
	}

	public void setViolationMessages(HashMap<String, String> violationMessages) {
		this.violationMessages = violationMessages;
	}

	public String getHost() throws MalformedURLException {
		URL u = new URL(url);
		return u.getProtocol() + "://" + u.getAuthority();
	}

	public String getPath() throws MalformedURLException {
		URL u = new URL(url);
		return u.getPath();
	}
}