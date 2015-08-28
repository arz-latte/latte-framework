package at.arz.latte.framework.services.interceptors;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "AddHeaderFilter", urlPatterns = { "/*" })
public class CorsInterceptor implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		Map<String, String[]> parms = httpRequest.getParameterMap();

		if (parms.containsKey("callback")) {
			OutputStream out = httpResponse.getOutputStream();

			GenericResponseWrapper wrapper = new GenericResponseWrapper(httpResponse);

			chain.doFilter(request, wrapper);

			out.write(new String(parms.get("callback")[0] + "(").getBytes());
			out.write(wrapper.getData());
			out.write(new String(");").getBytes());

			wrapper.setContentType("text/javascript;charset=UTF-8");

			out.close();
		} else {
			chain.doFilter(request, response);
		}
		/*
		 * if (response instanceof HttpServletResponse) { HttpServletResponse
		 * http = (HttpServletResponse) response;
		 * http.addHeader("Access-Control-Allow-Origin", "*");
		 * http.addHeader("Access-Control-Allow-Credentials", "true");
		 * http.addHeader("Access-Control-Allow-Methods",
		 * "GET, POST, DELETE, PUT"); }
		 * System.out.println(response.getWriter().toString());
		 * 
		 * chain.doFilter(request, response);
		 */
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