package at.arz.latte.framework.authentication;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * statische html Seiten sollen vom Browser nicht gecached werden. 
 */
@WebFilter("*.html")
public class StaticPageFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse=(HttpServletResponse) response;
		httpResponse.addHeader("Cache-Control", "no-cache, no-store");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// no action required
	}

	@Override
	public void destroy() {
		// no action required
	}

}
