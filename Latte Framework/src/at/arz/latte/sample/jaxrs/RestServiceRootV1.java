package at.arz.latte.sample.jaxrs;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import at.arz.latte.framework.modules.models.Module;


/**
 * The root resource class for latte rest api v1. 
 * 
 * @author mrodler
 *
 */
@RequestScoped
@Path("/")
public class RestServiceRootV1 {

	private static final String API_URL = "http://localhost:8080/jaxrs/api/v1/version.json";

	@Path("/call")
	@GET
	public String call() {
		WebClient client = WebClient.create(API_URL);

		ApiVersion result = client.get(ApiVersion.class);

		return result.toString();
	}

	private ApiVersion getCurrentVersion() {
		return new ApiVersion();
	}

	@Path("/version.json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Module getCurrentVersionAsJSON() {
		return new Module();
		
		//return Arrays.asList(new ApiVersion[]{ new ApiVersion("latte rest api", "v1.0", "ARZ"), new ApiVersion("latte rest api", "v1.0", "ARZ")})		
	}
	
	
	@POST
	@Path("create")
	@Consumes({ "application/xml", "application/json" })
	@Produces(MediaType.APPLICATION_JSON)
	public void createModule(@Valid ApiVersion module) {	
		
		
		System.out.println("create: " + module);
	}
	
	
	
	
	@Path("/version.xml")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public ApiVersion getCurrentVersionAsXML() {
		return getCurrentVersion();
	}
}
