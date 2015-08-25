package at.arz.latte.modules.services.restful.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import at.arz.latte.framework.modules.dta.MenuRootData;
import at.arz.latte.framework.modules.dta.ModuleUpdateData;
import at.arz.latte.framework.modules.dta.MenuLeafData;
import at.arz.latte.framework.modules.dta.MenuEntryData;
import at.arz.latte.modules.services.restful.service.DemoModuleRestfulService;

@ApplicationScoped
@ApplicationPath("/api/v1")
public class DemoModuleConfiguration extends Application {

	public static ModuleUpdateData MODULE = null;

	static {
		List<MenuRootData> subMenu = new ArrayList<>();
		
		MenuRootData menu1 = new MenuRootData(new MenuEntryData("Menu 1", "#", 10), new ArrayList<>());
		MenuLeafData menu11 = new MenuLeafData(new MenuEntryData("SubMenu 1", "#", 13), "user");
		MenuLeafData menu12 = new MenuLeafData(new MenuEntryData("SubMenu 2", "#", 17), "user");
		menu1.getChildren().add(menu11);
		menu1.getChildren().add(menu12);
		
		MenuRootData menu2 = new MenuRootData(new MenuEntryData("Menu 2", "#", 20), null);
		
		subMenu.add(menu1);
		subMenu.add(menu2);

		MenuLeafData mainMenu = new MenuLeafData(new MenuEntryData("Demo Modul 1", "http://localhost:8081/demo1/index.html", 3), "user");
		
		MODULE = new ModuleUpdateData("v1.00", mainMenu, subMenu);
	}

	private Set<Class<?>> applicationClasses;

	@Override
	public Set<Class<?>> getClasses() {
		if (applicationClasses == null) {
			initApplicationClasses();
		}
		return applicationClasses;
	}

	private void initApplicationClasses() {
		applicationClasses = new HashSet<Class<?>>();
		applicationClasses.add(DemoModuleRestfulService.class);
	}
}