package at.arz.latte.framework.modules.models;

import java.util.HashMap;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractEntity {

	@Transient
	private HashMap<String, String> validation;

	public abstract Long getId();

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public void setValidation(HashMap<String, String> validation) {
		this.validation = validation;
	}

	@Override
	public String toString() {
		return "Entity [id=" + getId() + "]";
	}
}
