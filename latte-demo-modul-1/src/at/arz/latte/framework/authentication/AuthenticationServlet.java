package at.arz.latte.framework.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String REDIRECT_COOKIE_NAME = "_redirect_dst";

	private final static String LOGIN_FORM_URI = "/latte/login.html";

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String currentURL = (String) req.getAttribute("javax.servlet.error.request_uri");
		res.reset();
		Cookie cookie = new Cookie(REDIRECT_COOKIE_NAME, currentURL);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		res.addCookie(cookie);
		res.addDateHeader("Date", System.currentTimeMillis());
		res.addDateHeader("Expires", 786297600000l);
		res.addHeader("Cache-Control", "no-cache, no-store");
		res.setContentType("text/plain");
		res.sendRedirect(LOGIN_FORM_URI);
	}
	
}
