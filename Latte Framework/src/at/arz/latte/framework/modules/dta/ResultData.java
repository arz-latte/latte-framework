package at.arz.latte.framework.modules.dta;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.modules.models.Module;

/**
 * used to transmit information about newly created or updated object
 * id and validation messages
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultData {

	private Long id;

	private HashMap<String, String> validation;

	public ResultData() {
	}

	public ResultData(Module module) {
		id = module.getId();
		validation = module.getValidation();
	}

	public long getId() {
		return id;
	}

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public boolean isSaveable() {
		return validation == null || validation.isEmpty();
	}

}
