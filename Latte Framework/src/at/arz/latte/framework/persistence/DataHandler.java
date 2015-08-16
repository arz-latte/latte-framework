package at.arz.latte.framework.persistence;

import java.util.ArrayList;
import java.util.List;

import at.arz.latte.framework.services.models.ModuleModel;
import at.arz.latte.framework.services.models.ResultModel;

public class DataHandler {

	public static List<ModuleModel> apps = new ArrayList<>();

	static {
		apps.add(new ModuleModel("Application 1"));
		apps.add(new ModuleModel("Application 2"));
	}

	public static List<ModuleModel> getApplications() {
		return apps;
	}
	
	public static ResultModel addApplication(ModuleModel app) {
		System.out.println("framework add " + app.getName());
		apps.add(app);
		
		return new ResultModel("ok");
	}

	public static ResultModel removeApplication(ModuleModel app) {		

		for (int i = 0; i < apps.size(); i++) {
			if (apps.get(i) != null && app != null && apps.get(i).getName().equals(app.getName())) {
				System.out.println("framework remove " + app.getName());
				apps.remove(i);
				return new ResultModel("ok");
			}
		}
		
		return new ResultModel("not found");
	}
}
