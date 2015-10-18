package at.arz.latte.framework.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/securitycheck")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public	void
			doGet(HttpServletRequest req, HttpServletResponse res)	throws IOException,
																	ServletException {
		Login login = new Login(req, res);
		login.showForm("/latte/login.html");
	}

	public	void
			doPost(HttpServletRequest req, HttpServletResponse res)	throws IOException,
																	ServletException {
		Login login = new Login(req, res);
		login.processLogin();
	}

}
