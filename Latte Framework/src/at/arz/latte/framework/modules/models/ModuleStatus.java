package at.arz.latte.framework.modules.models;

/**
 * current status of a module
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public enum ModuleStatus {

	/**
	 * unknown status, no contact to framework since start
	 */
	Unknown,

	/**
	 * module has lost contact to framework
	 */
	StartedInactive,

	/**
	 * module available and running
	 */
	StartedActive,

	/**
	 * module stopped
	 */
	Stopped;
}
