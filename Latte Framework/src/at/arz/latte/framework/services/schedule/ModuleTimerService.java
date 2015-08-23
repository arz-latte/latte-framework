package at.arz.latte.framework.services.schedule;

import java.awt.MenuItem;
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

import at.arz.latte.framework.modules.dta.MenuEntryData;
import at.arz.latte.framework.modules.dta.MenuLeafData;
import at.arz.latte.framework.modules.dta.MenuRootData;
import at.arz.latte.framework.modules.dta.ModulUpdateData;
import at.arz.latte.framework.modules.models.MenuRoot;
import at.arz.latte.framework.modules.models.MenuEntry;
import at.arz.latte.framework.modules.models.MenuLeaf;
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
		// System.out.println("check module status: " + module.getName());

		try {
			WebClient client = WebClient.create(module.getHost()).path(module.getPath() + "/status");
			HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
			conduit.getClient().setReceiveTimeout(2000);
			conduit.getClient().setConnectionTimeout(2000);

			ModulUpdateData resp = client.accept(MediaType.APPLICATION_JSON).get(ModulUpdateData.class);
			System.out.println(resp);

			// set module as active
			// if (module.getStatus() != ModuleStatus.Started) {
			module.setStatus(ModuleStatus.Started);
			module.setVersion(resp.getVersion());

			// update menu...
			List<MenuRoot> root = new ArrayList<>();

			for (MenuRootData r : resp.getMenu()) {

				// leafs
				List<MenuLeaf> leaf = new ArrayList<>();
				for (MenuLeafData tmp : r.getChildren()) {
					MenuEntryData e = tmp.getEntry();
					MenuEntry entry = new MenuEntry(e.getValue(), e.getUrl(), e.getPosition());
					MenuLeaf L = new MenuLeaf(entry, tmp.getPermission());

					leaf.add(L);
				}

				Collections.sort(leaf);

				// root menu entry
				MenuEntryData tmp = r.getEntry();
				MenuEntry entry = new MenuEntry(tmp.getValue(), tmp.getUrl(), tmp.getPosition());
				MenuRoot rootEntry = new MenuRoot(entry, leaf);
				root.add(rootEntry);
			}

			Collections.sort(root);

			if (root.hashCode() != module.getSubmenu().hashCode()) {
				System.out.println("mo: " + root.hashCode());
				System.out.println("db: " + module.getSubmenu().hashCode());
				System.out.println(root);
				System.out.println(module.getSubmenu());

				module.setSubmenu(root);
			}

			bean.updateModule(module);
			websocket.chat(new WebsocketMessage("active", "server"));
			// }

		} catch (WebApplicationException | ClientWebApplicationException ex) {
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
