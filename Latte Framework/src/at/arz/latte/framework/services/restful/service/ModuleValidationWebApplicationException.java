package at.arz.latte.framework.services.restful.service;

import java.util.HashMap;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import at.arz.latte.framework.modules.dta.ModuleFullData;
import at.arz.latte.framework.modules.models.Module;

public class ModuleValidationWebApplicationException extends
		WebApplicationException {

	private static final long serialVersionUID = 1L;

	public ModuleValidationWebApplicationException(
			HashMap<String, String> violationMessages, Module m)  {	    
		super(Response.status(Response.Status.OK).entity(new ModuleFullData(m, violationMessages)).type(MediaType.APPLICATION_JSON).build());
		
	}
}