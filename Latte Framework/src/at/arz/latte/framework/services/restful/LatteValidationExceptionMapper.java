package at.arz.latte.framework.services.restful;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import at.arz.latte.framework.modules.dta.ResponseData;
import at.arz.latte.framework.persistence.beans.LatteValidationException;

@Provider
public class LatteValidationExceptionMapper implements ExceptionMapper<LatteValidationException>{

	@Override
	public Response toResponse(LatteValidationException exception) {		
		ResponseData responseData = new ResponseData(exception.getValidation());
		return Response.status(400).type(MediaType.APPLICATION_JSON).entity(responseData).build();
	}

}
