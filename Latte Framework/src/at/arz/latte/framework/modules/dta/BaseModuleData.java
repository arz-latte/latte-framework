package at.arz.latte.framework.modules.dta;

import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;

public class BaseModuleData {

	private int id;

	private String name;

	private String version;

	private ModuleStatus status;

	private boolean enabled;

	public BaseModuleData(Module m) {
	/*	this.id = m.getId();
		this.name = m.getName();
		this.version = m.getVersion();
		//this.status = m.getStatus();
		this.enabled = m.getEnabled();*/
	}

}
