package at.arz.latte.framework.util;

import java.util.Collection;

public class JPA {

	public static void fetchAll(Collection<?> list) {
		for (@SuppressWarnings("unused")
		Object o : list) {
			// do nothing
		}
	}

}
