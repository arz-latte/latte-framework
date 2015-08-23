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
		System.out.println("demo init");

		List<MenuRootData> menu = new ArrayList<>();
		
		MenuRootData menu1 = new MenuRootData(new MenuEntryData("Menu 1", "url", 10), new ArrayList<>());
		MenuLeafData menu11 = new MenuLeafData(new MenuEntryData("SubMenu 1", "url", 13), "admin");
		MenuLeafData menu12 = new MenuLeafData(new MenuEntryData("SubMenu 2", "url", 15), "admin");
		menu1.getChildren().add(menu11);
		menu1.getChildren().add(menu12);
		
		MenuRootData menu2 = new MenuRootData(new MenuEntryData("Menu 2", "url", 20), null);
		
		menu.add(menu1);
		menu.add(menu2);

		MODULE = new ModuleUpdateData("v1.00", menu);
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