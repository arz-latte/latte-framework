package at.arz.latte.framework.module.services;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import at.arz.latte.framework.restapi.MenuData;

@ApplicationScoped
public class FrameworkNavigation  {

	private Map<Long, MenuData> menus = new HashMap<Long, MenuData>();

	public MenuData get(Long moduleId) {
		return menus.get(moduleId);
	}

	public void update(Long moduleId, MenuData menuData) {
		menus.put(moduleId, menuData);
	}

}
