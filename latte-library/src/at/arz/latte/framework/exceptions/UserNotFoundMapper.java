package at.arz.latte.framework.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserNotFoundMapper implements ExceptionMapper<UserNotFound> {

	@Override
	public Response toResponse(UserNotFound exception) {
		return Response.status(Response.Status.NOT_FOUND)
						.entity(exception.getMessage())
						.build();
	}

}
