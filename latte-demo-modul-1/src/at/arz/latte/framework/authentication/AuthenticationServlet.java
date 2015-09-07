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

	private final static String loginPath = "http://localhost:8080/latte/login.html";

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String currentURL = (String) req.getAttribute("javax.servlet.error.request_uri");
		System.out.println("demo1 got unauthenticated request:" + currentURL);
		res.reset();
		Cookie cookie = new Cookie(REDIRECT_COOKIE_NAME, currentURL);
		cookie.setMaxAge(-1);
		res.addCookie(cookie);
		res.sendRedirect(loginPath);
	}
}
