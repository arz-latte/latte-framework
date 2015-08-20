package at.arz.latte.framework.modules.dta;

import java.util.HashMap;

import at.arz.latte.framework.modules.models.Module;

public class FullModuleData {

	private String url;

	private int checkInterval;

	private HashMap<String, String> violationMessages;

	public FullModuleData() {
		super();
	}

	public FullModuleData(Module m) {
		super();
		/*super(m.getId(), m.getName(), m.getVersion(), m.getStatus(), m
				.getEnabled());
		this.url = m.getUrl();
		this.checkInterval = m.getCheckInterval();*/
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

	public HashMap<String, String> getViolationMessages() {
		return violationMessages;
	}

	public void setViolationMessages(HashMap<String, String> violationMessages) {
		this.violationMessages = violationMessages;
	}

}
