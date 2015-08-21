package at.arz.latte.framework.modules.dta;

import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.modules.models.Module;

/**
 * used to transmit all module data to client
 * 
 * @author Dominik
 *
 */
@XmlRootElement(name = "module_full")
public class ModuleFullData extends ModuleBaseData {

	private String url;

	private int checkInterval;

	public ModuleFullData() {
		super();
	}

	public ModuleFullData(Module m) {
		super(m.getId(), m.getName(), m.getProvider(), m.getVersion(), m.getStatus(), m.getEnabled());
		this.checkInterval = m.getCheckInterval();
		this.url = m.getUrl();
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



}
