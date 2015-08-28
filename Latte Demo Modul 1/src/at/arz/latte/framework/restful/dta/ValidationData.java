package at.arz.latte.framework.restful.dta;

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
public class ValidationData {

	private HashMap<String, String> validation;

	public ValidationData() {
		super();
	}

	public ValidationData(HashMap<String, String> validation) {
		super();
		this.validation = validation;
	}

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public void setValidation(HashMap<String, String> validation) {
		this.validation = validation;
	}

	@Override
	public String toString() {
		return "ValidationData [validation=" + validation + "]";
	}

}