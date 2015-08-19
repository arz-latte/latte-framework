package at.arz.latte.framework.persistence;

import java.util.ArrayList;
import java.util.List;

import at.arz.latte.framework.modules.models.Module;


public class DataHandler {

	/*public static List<Module> apps = new ArrayList<>();

	static {
	//	apps.add(new Module("Application 1", 1, "local", "path"));
	//	apps.add(new Module("Application 2", ModuleStatus.UNKNOWN, 1, "local", "path", 45));
	}

	public static List<Module> getApplications() {
		return apps;
	}
	
	public static ResultModel addApplication(Module app) {
		System.out.println("framework add " + app);
		apps.add(app);
		
		return new ResultModel("ok");
	}

	public static ResultModel updateApplication(Module app) {
		for (int i = 0; i < apps.size(); i++) {
			if (apps.get(i) != null && app != null && apps.get(i).getName().equals(app.getName())) {
				System.out.println("framework update " + app);
				apps.set(i, app);
				return new ResultModel("ok");
			}
		}
		
		return addApplication(app);
	}

	public static ResultModel removeApplication(Module app) {		

		for (int i = 0; i < apps.size(); i++) {
			if (apps.get(i) != null && app != null && apps.get(i).getName().equals(app.getName())) {
				System.out.println("framework remove " + app);
				apps.remove(i);
				return new ResultModel("ok");
			}
		}
		
		return new ResultModel("not found");
	}*/
}
