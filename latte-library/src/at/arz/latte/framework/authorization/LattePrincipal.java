package at.arz.latte.framework.authorization;

import java.security.Principal;

enum LattePrincipal implements Principal {
	UNAUTHENTICATED;

	@Override
	public String getName() {
		return name();
	}

}