package at.arz.latte.framework.services.restful.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import at.arz.latte.framework.persistence.beans.MyValidationException;

//@Provider
public class ValidationExceptionMapper implements
		ExceptionMapper<MyValidationException> {

	@Override
	public Response toResponse(MyValidationException ex) {
		System.out.println(Response.status(Status.BAD_REQUEST)
				.entity("MESAAAGGGEE")
				.type(MediaType.APPLICATION_JSON).build());
		
		
		return Response.status(Status.BAD_REQUEST)
				.entity("MESAAAGGGEE")
				.type(MediaType.APPLICATION_JSON).build();
	}

}