package at.arz.latte.framework.modules.dta;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;
import at.arz.latte.framework.modules.models.validator.CheckUrl;
import at.arz.latte.framework.modules.models.validator.CheckUrl;

/**
 * used to transmit module data to the client, for single view or update
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "module")
public class ModuleData {

	private Long id;

	@NotNull
	@Size(min = 1, max = 63)
	private String name;

	@NotNull
	@Size(min = 1, max = 63)
	private String provider;

	@NotNull
	@CheckUrl
	private String url;

	/**
	 * time in seconds for checking if module available, default 60s, if is set
	 * to 0s - checking is disabled
	 */
	@NotNull
	@Min(1)
	private int interval;

	@NotNull
	private boolean enabled;

	public ModuleData() {
	}

	public ModuleData(Long id, String name, String provider, String url, int interval, boolean enabled) {
		super();
		this.id = id;
		this.name = name;
		this.provider = provider;
		this.url = url;
		this.interval = interval;
		this.enabled = enabled;
	}

	/**
	 * convert persistent module entity to REST entity
	 * 
	 * @param m
	 * @throws MalformedURLException 
	 */
	public ModuleData(Module m) {
		this.id = m.getId();
		this.name = m.getName();
		this.provider = m.getProvider();
		this.url = m.getUrl();
		this.interval = m.getInterval();
		this.enabled = m.getEnabled();
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

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
