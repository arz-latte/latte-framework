package at.arz.latte.framework;

import java.io.Serializable;
import java.util.Set;

public interface LatteUser extends Serializable {

	String getUserId();
		
	boolean isInRole(String role);

	boolean hasPermission(String permission);

	Set<String> getAllPermissions();
}