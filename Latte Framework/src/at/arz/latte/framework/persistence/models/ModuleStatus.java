package at.arz.latte.framework.persistence.models;

/**
 * Current status of a module
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
