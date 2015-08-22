package at.arz.latte.modules.services.restful.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import at.arz.latte.framework.modules.dta.MenuItemData;
import at.arz.latte.framework.modules.dta.ModulUpdateData;
import at.arz.latte.modules.services.restful.service.DemoModuleRestfulService;

@ApplicationScoped
@ApplicationPath("/api/v1")
public class DemoModuleConfiguration extends Application {

	public static ModulUpdateData MODULE = null;

	static {
		System.out.println("demo init");

		List<MenuItemData> menu = new ArrayList<>();
		MenuItemData menu1 = new MenuItemData("Menu 1", null, 1, new ArrayList<>());
		MenuItemData menu11 = new MenuItemData("SubMenu 1", null, 1, null);
		MenuItemData menu12 = new MenuItemData("SubMenu 2", null, 1, null);
		menu1.getChildren().add(menu11);
		menu1.getChildren().add(menu12);
		MenuItemData menu2 = new MenuItemData("Menu 2", null, 2, null);

		menu.add(menu1);
		menu.add(menu2);

		MODULE = new ModulUpdateData("v1.00", menu);
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