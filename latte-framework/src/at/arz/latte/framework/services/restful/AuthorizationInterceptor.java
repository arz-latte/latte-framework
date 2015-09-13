package at.arz.latte.framework.services.restful;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import at.arz.latte.framework.persistence.beans.UserManagementBean;

@WebFilter(urlPatterns = { "/api/v1/modules/*", "/api/v1/users/*", "/api/v1/roles/*" })
public class AuthorizationInterceptor implements Filter {

	@EJB
	private UserManagementBean userBean;

	@Context
	protected SecurityContext sc;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		boolean hasPermission = userBean.checkUserPermission(sc.getUserPrincipal().getName(), "admin");

		if (hasPermission) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
		}
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