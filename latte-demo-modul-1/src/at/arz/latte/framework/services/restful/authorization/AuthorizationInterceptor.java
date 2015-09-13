package at.arz.latte.framework.services.restful.authorization;

import java.io.IOException;
import java.util.List;

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

@WebFilter(urlPatterns = { "/api/v1/demo/useronly.json" })
public class AuthorizationInterceptor implements Filter {

	@Context
	protected SecurityContext sc;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();

		System.out.println("demo: " + session.getId());
		chain.doFilter(request, response); // permission ok
		
		/*
		// check permissions of user
		List<String> permissions = (List<String>) session.getAttribute("permissions");
		for (String p : permissions) {
			if (p.equals("user")) {
				chain.doFilter(request, response); // permission ok
				return;
			}
		}

		((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);*/
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