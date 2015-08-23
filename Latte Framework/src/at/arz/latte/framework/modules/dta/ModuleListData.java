package at.arz.latte.framework.modules.dta;

import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.modules.models.ModuleStatus;

/**
 * used to transmit module data to the client, for list view
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "modules")
public class ModuleListData {

	private Long id;

	private String name;

	private String provider;

	private String version;

	private ModuleStatus status;

	private boolean enabled;

	public ModuleListData() {
	}

	public ModuleListData(Long id, String name, String provider, String version, ModuleStatus status,
			boolean enabled) {
		super();
		this.id = id;
		this.name = name;
		this.provider = provider;
		this.version = version;
		this.status = status;
		this.enabled = enabled;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

}
