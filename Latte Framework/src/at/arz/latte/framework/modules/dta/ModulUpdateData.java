package at.arz.latte.framework.modules.dta;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * used to transmit module update data to the client
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@XmlRootElement(name = "module_update")
public class ModulUpdateData {

	private String version;

	public ModulUpdateData() {
	}

	public ModulUpdateData(String version) {
		super();
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
