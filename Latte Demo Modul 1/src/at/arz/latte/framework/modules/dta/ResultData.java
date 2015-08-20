package at.arz.latte.framework.modules.dta;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.framework.modules.models.Module;

/**
 * used to transmit information about newly created or updated object
 * id and validation messages
 * @author Dominik
 *
 */
@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultData {

	private long id;

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

	public void setId(long id) {
		this.id = id;
	}

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public void setValidation(HashMap<String, String> validation) {
		this.validation = validation;
	}

	public boolean isSaveable() {
		return validation == null || validation.isEmpty();
	}

}
