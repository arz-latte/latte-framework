package at.arz.latte.framework.services.restful.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.arz.latte.framework.modules.models.Module;

public class CustomNotFoundException extends WebApplicationException {
	 
	  /**
	  * Create a HTTP 404 (Not Found) exception.
	  */
	  public CustomNotFoundException() {
	    super(404);
	  }
	 
	  /**
	  * Create a HTTP 404 (Not Found) exception.
	  * @param message the String that is the entity of the 404 response.
	  */
	  public CustomNotFoundException(String message) {
	    super(Response.status(404).
	    entity(new Module()).type(MediaType.APPLICATION_JSON).build());
	  }
	}