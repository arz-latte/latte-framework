package at.arz.latte.framework.services.restful.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.services.models.ApplicationModel;
import at.arz.latte.framework.services.models.ResultModel;
import at.arz.latte.framework.persistence.DataHandler;

@Path("framework")
public class FrameworkRestfulService {
	
	@GET
	@Path("list")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ApplicationModel> getApplicationList() {
		return DataHandler.getApplications();	
	}
	
	@POST
	@Path("add")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultModel addApplication(ApplicationModel app) {
		return DataHandler.addApplication(app);
	}
	
	
}