package at.arz.latte.framework.restful.dta;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * used to transmit module data to the client, for single view or update
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "module")
@XmlAccessorType(XmlAccessType.FIELD)
public class ModuleData {

	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@NotNull
	@Size(min = 1, max = 63)
	private String provider;

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
	 * enable or disable checking of this module
	 */
	@NotNull
	private Boolean enabled;

	// --------------- only for list view ---------------

	@XmlElement(name = "lastmodified")
	private Long lastModified;

	/**
	 * currrent status of module
	 */
	private Boolean running;

	// ------- only for transmisstion to client -------

	private MenuData menu;

	public ModuleData() {
	}

	/**
	 * constructor for REST list view
	 * 
	 * @param id
	 * @param name
	 * @param provider
	 * @param enabled
	 * @param running
	 * @param lastModified
	 */
	public ModuleData(Long id, String name, String provider, Boolean running, Boolean enabled, Long lastModified) {
		this();
		this.id = id;
		this.name = name;
		this.provider = provider;
		this.running = running;
		this.enabled = enabled;
		this.lastModified = lastModified;
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

	public Boolean getRunning() {
		return running;
	}

	public void setRunning(Boolean running) {
		this.running = running;
	}

	public MenuData getMenu() {
		return menu;
	}

	public void setMenu(MenuData menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "ModuleData [id=" + id + ", name=" + name + ", provider=" + provider + ", url=" + url + ", interval="
				+ interval + ", enabled=" + enabled + ", lastModified=" + lastModified + ", running=" + running
				+ ", menu=" + menu + "]";
	}

}
