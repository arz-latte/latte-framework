package at.arz.latte.framework.services.models;

import java.net.URL;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "module")
public class ModuleModel {

	/**
	 * module name
	 */
	private String name;

	/**
	 * current status of module
	 */
	private ModuleStatus status;

	/**
	 * version of module
	 */
	private int version;

	/**
	 * address of the module, used for checking module status, e.g.
	 * http://localhost:8080/Modul1/api/v1/module
	 */
	private URL url;

	/**
	 * time in seconds for checking if module available, default 60s, if is set
	 * to 0s - checking is disabled
	 */
	private int checkInterval;

	public ModuleModel() {
	}

	public ModuleModel(String name, int version, URL url) {
		super();
		this.name = name;
		this.status = ModuleStatus.UNKNOWN;
		this.version = version;
		this.url = url;
		this.checkInterval = 60;
	}

	public ModuleModel(String name, int version, URL url, int checkInterval) {
		super();
		this.name = name;
		this.status = ModuleStatus.UNKNOWN;
		this.version = version;
		this.url = url;
		this.checkInterval = checkInterval;
	}

	public ModuleModel(String name, ModuleStatus status, int version, URL url,
			int checkInterval) {
		super();
		this.name = name;
		this.status = status;
		this.version = version;
		this.url = url;
		this.checkInterval = checkInterval;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
	public String getHost() {
		return url.getProtocol() + "://" + url.getAuthority();
	}
	
	public String getPath() {
		return url.getPath();
	}
	
	public int getCheckInterval() {
		return checkInterval;
	}

	public void setCheckInterval(int checkInterval) {
		this.checkInterval = checkInterval;
	}

	@Override
	public String toString() {
		return "ModuleModel [name=" + name + ", status=" + status
				+ ", version=" + version + ", url=" + url + ", checkInterval="
				+ checkInterval + "]";
	}

}
