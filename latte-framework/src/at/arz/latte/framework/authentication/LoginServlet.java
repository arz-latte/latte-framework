package at.arz.latte.framework.authentication;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/securitycheck")
public class LoginServlet extends HttpServlet {

	private static final String REDIRECT_COOKIE_NAME = "_redirect_dst";
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		if(isUnauthenticated(res)){
			String currentURL = (String) req.getAttribute("javax.servlet.error.request_uri");
			res.reset();
			res.addCookie(new Cookie(REDIRECT_COOKIE_NAME, currentURL));
			res.sendRedirect("/latte/login.html");
		}else{
			String destination = evaluateRedirectDestination(req);
			res.sendRedirect(destination);
		}
	}

	private boolean isUnauthenticated(HttpServletResponse res) {
		return res.getStatus() == 401;
	}

	private String evaluateRedirectDestination(HttpServletRequest req) {
		Cookie redirectCookie = findRedirectCookie(req);
		if(redirectCookie == null){
					return "/latte/index.html";
		}
		return redirectCookie.getValue(); 
	}

	private Cookie findRedirectCookie(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if(REDIRECT_COOKIE_NAME.equals(cookie.getName())){
				return cookie;
			}
		}
		return null;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("got login post to:" + req.getRequestURL());

		String username=Objects.requireNonNull(req.getParameter("username"));
		String password=Objects.requireNonNull(req.getParameter("password"));
		req.login(username, password);
		Cookie cookie = new Cookie(REDIRECT_COOKIE_NAME, "");
		cookie.setMaxAge(0);
		res.addCookie(cookie);
		res.sendRedirect(evaluateRedirectDestination(req));
	}
	
}
