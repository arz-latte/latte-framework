package at.arz.latte.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import at.arz.latte.framework.modules.models.ModuleStatus;


@WebServlet("/init")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InitServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");

		JSONObject json = new JSONObject();

		try {
			json.put("status", ModuleStatus.StartedActive);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		PrintWriter out = resp.getWriter();
		out.print(json.toString());
	}

}
