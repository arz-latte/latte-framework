package at.arz.latte.framework.modules.models;

import java.util.HashMap;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractEntity {
	
	@Id
	protected Long id;
	
	public AbstractEntity() {
		// jpa constructor
	}

	public AbstractEntity(Long id) {
		this.id = id;
	}

	@Transient
	private HashMap<String, String> validation;

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public void setValidation(HashMap<String, String> validation) {
		this.validation = validation;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + "]";
	}
}
