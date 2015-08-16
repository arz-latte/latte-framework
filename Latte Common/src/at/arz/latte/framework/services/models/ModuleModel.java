package at.arz.latte.framework.services.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModuleModel {
	
	private String name;

	public ModuleModel() {
	}

	public ModuleModel(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
