package at.arz.latte.framework.modules.models;

import java.util.HashMap;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * superclass for all persistent entities, manages id and validation messages
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@MappedSuperclass
public abstract class AbstractEntity {

	@Transient
	private HashMap<String, String> validation;

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public void setValidation(HashMap<String, String> validation) {
		this.validation = validation;
	}

	public abstract Long getId();

	@Override
	public String toString() {
		return "Entity [id=" + getId() + "]";
	}
}
