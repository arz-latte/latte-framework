package at.arz.latte.framework.modules.dta;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * used to transmit validation informations about newly created or updated
 * objects
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "response")
public class ResponseData {

	private HashMap<String, String> validation;

	public ResponseData() {
		super();
	}

	public ResponseData(HashMap<String, String> validation) {
		super();
		this.validation = validation;
	}

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public void setValidation(HashMap<String, String> validation) {
		this.validation = validation;
	}

}
