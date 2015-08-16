package at.arz.latte.framework.services.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultModel {

	private String success;

	public ResultModel() {
	}

	public ResultModel(String success) {
		super();
		this.success = success;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

}
