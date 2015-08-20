package at.arz.latte.framework.services.schedule;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 * timer for periodic checking of module status
 */
@Singleton
public class ModuleTimerService {

	@EJB
	private ModuleStatusService moduleStatusService;

	private int counter = 0;

	@Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
	private void checkModules() {

		counter++;
/*
		for (Module module : DataHandler.getApplications()) {

			int checkInterval = module.getCheckInterval();
			if (checkInterval > 0 && counter % checkInterval == 0) {
				moduleStatusService.checkStatus(module);
			}
		}
*/
	}
}
