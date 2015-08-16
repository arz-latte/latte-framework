package at.arz.latte.framework.services.models;

/**
 * Current status of a module
 */
public enum ModulStatus {

	/**
	 * unknown status, no contact to framework since start
	 */
	UNKNOWN,

	/**
	 * module has lost contact to framework
	 */
	STARTED_INACTIVE,

	/**
	 * module available and running
	 */
	STARTED_ACTIVE,

	/**
	 * module stopped
	 */
	STOPPED;
}
