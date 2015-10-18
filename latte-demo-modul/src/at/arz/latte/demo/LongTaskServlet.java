package at.arz.latte.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * simualte long running task
 */
@WebServlet("/longtask")
public class LongTaskServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void
			doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException,
																			IOException {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		response.getWriter()
				.append("Served at: ")
				.append(request.getContextPath());
	}

}
