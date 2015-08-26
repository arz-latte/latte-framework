package at.arz.latte.framework.services.schedule;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.ClientWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;

import at.arz.latte.framework.modules.dta.MenuLeafData;
import at.arz.latte.framework.modules.dta.MenuRootData;
import at.arz.latte.framework.modules.dta.ModuleUpdateData;
import at.arz.latte.framework.modules.models.MenuEntry;
import at.arz.latte.framework.modules.models.MenuLeaf;
import at.arz.latte.framework.modules.models.MenuRoot;
import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.ModuleStatus;
import at.arz.latte.framework.persistence.beans.ModuleManagementBean;
import at.arz.latte.framework.websockets.WebsocketEndpoint;
import at.arz.latte.framework.websockets.models.WebsocketMessage;

/**
 * timer for periodic checking of module status
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Singleton
@DependsOn({ "ModuleManagementBean", "WebsocketEndpoint" })
public class ModuleTimerService {

	@EJB
	private ModuleManagementBean bean;

	@EJB
	private WebsocketEndpoint websocket;

	private int counter = 0;

	@Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
	private void checkModules() {

		counter++;

		for (Module module : bean.getAllModules()) {

			int checkInterval = module.getInterval();
			if (module.getEnabled() && checkInterval > 0 && counter % checkInterval == 0) {
				checkStatus(module);
			}
		}
	}

	private void checkStatus(Module module) {
		try {
			WebClient client = WebClient.create(module.getUrlHost()).path(module.getUrlPath() + "/status.json");
			HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
			conduit.getClient().setReceiveTimeout(2000);
			conduit.getClient().setConnectionTimeout(2000);

			// todo: send version and hashcode of menu to client via post...
			ModuleUpdateData resp = client.accept(MediaType.APPLICATION_JSON).get(ModuleUpdateData.class);

			// set module as active
			if (module.getStatus() != ModuleStatus.Started || !module.getVersion().equals(resp.getVersion())) {
											
				module.setStatus(ModuleStatus.Started);
				module.setVersion(resp.getVersion());
				
				// get main menu
				MenuLeaf mainMenu = new MenuLeaf(resp.getMainMenu());

				// get sub menu
				List<MenuRoot> subMenu = new ArrayList<>();
				for (MenuRootData menuRootData : resp.getSubMenu()) {

					// sub menu - bottom menu entries (leafs)
					List<MenuLeaf> leafs = new ArrayList<>();
					for (MenuLeafData menuLeafData : menuRootData.getChildren()) {
						MenuLeaf L = new MenuLeaf(menuLeafData);
						leafs.add(L);
					}
					Collections.sort(leafs);

					// sub menu - top menu entry (root)
					MenuEntry e = new MenuEntry(menuRootData.getEntry());
					subMenu.add(new MenuRoot(e, leafs));
				}
				Collections.sort(subMenu);

				// compare stored version of main menu with response
				if (mainMenu.hashCode() != module.getMainMenu().hashCode()) {
					System.out.println("main menu changed");
					System.out.println("mo: " + mainMenu.hashCode());
					System.out.println("db: " + module.getMainMenu().hashCode());
					System.out.println(mainMenu);
					System.out.println(module.getMainMenu());
					module.setMainMenu(mainMenu);
				}

				// compare stored version of sub menu with response
				//System.out.println(module.getSubMenu());		eager nullpointer ejb...
				if (subMenu.hashCode() != module.getSubMenu().hashCode()) {
					System.out.println("sub menu changed");
					System.out.println("mo: " + subMenu.hashCode());
					System.out.println("db: " + module.getSubMenu().hashCode());
					System.out.println(subMenu);
					System.out.println(module.getSubMenu());
					module.setSubMenu(subMenu);
				}
				
				// todo validate 
				
				bean.updateModule(module);
				
				websocket.chat(new WebsocketMessage("update", "server"));
			}
			
			/* todo inklusive menü??
			// get main menu
			MenuLeaf mainMenu = new MenuLeaf(resp.getMainMenu());

			// get sub menu
			List<MenuRoot> subMenu = new ArrayList<>();
			for (MenuRootData menuRootData : resp.getSubMenu()) {

				// sub menu - bottom menu entries (leafs)
				List<MenuLeaf> leafs = new ArrayList<>();
				for (MenuLeafData menuLeafData : menuRootData.getChildren()) {
					MenuLeaf L = new MenuLeaf(menuLeafData);
					leafs.add(L);
				}
				Collections.sort(leafs);

				// sub menu - top menu entry (root)
				MenuEntry e = new MenuEntry(menuRootData.getEntry());
				subMenu.add(new MenuRoot(e, leafs));
			}
			Collections.sort(subMenu);

			boolean changed = false;

			// compare stored version of main menu with response
			if (mainMenu.hashCode() != module.getMainMenu().hashCode()) {
				System.out.println("main menu changed");
				System.out.println("mo: " + mainMenu.hashCode());
				System.out.println("db: " + module.getMainMenu().hashCode());
				System.out.println(mainMenu);
				System.out.println(module.getMainMenu());
				module.setMainMenu(mainMenu);
				changed = true;
			}

			// compare stored version of sub menu with response
			//System.out.println(module.getSubMenu());		eager nullpointer ejb...
			if (subMenu.hashCode() != module.getSubMenu().hashCode()) {
				System.out.println("sub menu changed");
				System.out.println("mo: " + subMenu.hashCode());
				System.out.println("db: " + module.getSubMenu().hashCode());
				System.out.println(subMenu);
				System.out.println(module.getSubMenu());
				module.setSubMenu(subMenu);
				changed = true;
			}

			// set module as active
			if (module.getStatus() != ModuleStatus.Started || !module.getVersion().equals(resp.getVersion())
					|| changed) {
							
				module.setStatus(ModuleStatus.Started);
				module.setVersion(resp.getVersion());

				bean.updateModule(module);
				
				websocket.chat(new WebsocketMessage("update", "server"));
			}
			
			*/

		} catch (WebApplicationException | ClientWebApplicationException ex) {
			System.out.println("Check module: " + module);
			System.out.println("WebApplicationException: " + ex.getMessage());

			// set module as inactive
			if (module.getStatus() == ModuleStatus.Started) {
				module.setStatus(ModuleStatus.Unknown);
				bean.updateModule(module);
				
				websocket.chat(new WebsocketMessage("inactive", "server"));
			}

			// todo: stopped status signal

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
