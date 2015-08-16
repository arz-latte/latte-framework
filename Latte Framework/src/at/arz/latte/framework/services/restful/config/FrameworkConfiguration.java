package at.arz.latte.framework.services.restful.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api/v1")
public class FrameworkConfiguration extends Application {

	static {
		System.out.println("framework init static");
	}
}
