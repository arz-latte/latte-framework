package at.arz.latte.framework.modules.models;

/**
 * current status of a module
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public enum ModuleStatus {

	/**
	 * unknown status, no contact to framework
	 */
	Unknown,

	/**
	 * module available and running
	 */
	Started,

	/**
	 * module stopped
	 */
	Stopped;
}
