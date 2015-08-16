package at.arz.latte.framework.persistence;

import java.util.ArrayList;
import java.util.List;

import at.arz.latte.framework.services.models.ApplicationModel;
import at.arz.latte.framework.services.models.ResultModel;

public class DataHandler {

	public static List<ApplicationModel> apps = new ArrayList<>();

	static {
		apps.add(new ApplicationModel("Application 1"));
		apps.add(new ApplicationModel("Application 2"));
	}

	public static List<ApplicationModel> getApplications() {
		return apps;
	}
	
	public static ResultModel addApplication(ApplicationModel app) {
		System.out.println("framework add " + app.getName());
		apps.add(app);
		
		return new ResultModel("ok");
	}
}
