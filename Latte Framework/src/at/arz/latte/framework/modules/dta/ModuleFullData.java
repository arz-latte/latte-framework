package at.arz.latte.framework.modules.dta;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.modules.models.Module;

@XmlRootElement(name = "module_full")
public class ModuleFullData extends ModuleBaseData {

	private String url;

	private int checkInterval;

	private HashMap<String, String> validation;

	public ModuleFullData() {
		super();
	}

	public ModuleFullData(String url, int checkInterval, HashMap<String, String> validation) {
		super();
		this.url = url;
		this.checkInterval = checkInterval;
		this.validation = validation;
	}

	public ModuleFullData(Module m) {
		super(m.getId(), m.getName(), m.getVersion(), m.getStatus(), m.getEnabled());
		this.checkInterval = m.getCheckInterval();
	}

	public void setModule(Module m) {
		this.id = m.getId();
		this.name = m.getName();
		this.version = m.getVersion();
		this.status = m.getStatus();
		this.enabled = m.getEnabled();
		this.url = m.getUrl();
		this.checkInterval = m.getCheckInterval();
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

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public void setValidation(HashMap<String, String> validation) {
		this.validation = validation;
	}

}
