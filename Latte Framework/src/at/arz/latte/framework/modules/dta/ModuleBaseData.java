package at.arz.latte.framework.modules.dta;

import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.modules.models.ModuleStatus;

@XmlRootElement(name = "base_module")
public class ModuleBaseData {

	protected int id;

	protected String name;

	protected String version;

	protected ModuleStatus status;

	protected boolean enabled;

	public ModuleBaseData() {
		super();
	}

	public ModuleBaseData(int id, String name, String version, ModuleStatus status, boolean enabled) {
		super();
		this.id = id;
		this.name = name;
		this.version = version;
		this.status = status;
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
