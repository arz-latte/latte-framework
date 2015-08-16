package at.arz.latte.framework.services.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ApplicationModel {
	
	private String name;

	public ApplicationModel() {
	}

	public ApplicationModel(String name) {
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
