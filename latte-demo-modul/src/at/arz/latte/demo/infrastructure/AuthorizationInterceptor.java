package at.arz.latte.demo.infrastructure;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import at.arz.latte.framework.authorization.LatteAuthorization;

/**
 * demo class for testing authorization based on user permission "admin"
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@WebFilter(urlPatterns = { "/api/demo/adminonly.json" })
public class AuthorizationInterceptor implements Filter {

	@Context
	protected SecurityContext sc;

	@Inject
	private LatteAuthorization authorization;

	@Override
	public void doFilter(	ServletRequest request,
							ServletResponse response,
							FilterChain chain)	throws IOException,
												ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();

		Set<String> permissions = authorization.getPermissions();
		if (permissions.contains("admin")) {
			chain.doFilter(request, response);
			return;
		}

		((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}