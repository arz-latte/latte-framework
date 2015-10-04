package at.arz.latte.framework.services.restful.authorization;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
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

import at.arz.latte.framework.persistence.beans.PermissionManagementBean;

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
	
	@EJB
	private PermissionManagementBean permissionBean;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest) request).getSession();

		// get permisssion of current user
		List<String> permissions = (List<String>) session.getAttribute("permissions");
		if(session.getAttribute("permissions") == null) {

			permissions = permissionBean.getUserPermissions(sc.getUserPrincipal().getName());
			session.setAttribute("permissions", permissions);
		}
		
		// check permissions of user
		for (String p : permissions) {
			if (p.equals("admin")) {
				System.out.println("permission ok");
				chain.doFilter(request, response); // permission ok
				return;
			}
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