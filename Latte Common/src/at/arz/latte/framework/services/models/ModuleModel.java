package at.arz.latte.framework.services.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="module")
public class ModuleModel {

	/**
	 * module name
	 */
	private String name;

	private ModuleStatus status;

	private int version;

	public ModuleModel() {
	}

	public ModuleModel(String name, ModuleStatus status, int version) {
		super();
		this.name = name;
		this.status = status;
		this.version = version;
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

	@Override
	public String toString() {
		return "ModuleModel [name=" + name + ", status=" + status
				+ ", version=" + version + "]";
	}

}
