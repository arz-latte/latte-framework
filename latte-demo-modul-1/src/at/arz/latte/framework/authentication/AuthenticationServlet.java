package at.arz.latte.framework.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static String loginPath = "http://localhost:8080/latte/login.html";

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.reset();
		res.sendRedirect(loginPath);
	}
}
