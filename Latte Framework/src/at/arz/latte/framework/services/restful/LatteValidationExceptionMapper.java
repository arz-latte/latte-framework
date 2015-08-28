package at.arz.latte.framework.services.restful;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import at.arz.latte.framework.restful.dta.ValidationData;

@Provider
public class LatteValidationExceptionMapper implements ExceptionMapper<LatteValidationException>{

	@Override
	public Response toResponse(LatteValidationException ex) {		
		ValidationData validationData = new ValidationData(ex.getValidation());
		return Response.status(ex.getStatus()).type(MediaType.APPLICATION_JSON).entity(validationData).build();
	}

}
