package at.arz.latte.framework.authentication;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login {

	private static final String ATTR_REQUEST_URI = "javax.servlet.error.request_uri";

	private static final String REDIRECT_COOKIE_NAME = "_redirect_dst";

	private HttpServletRequest request;
	private HttpServletResponse response;

	private String currentRequestURI;

	private Cookie redirectCookie;

	public Login(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void showForm(String loginFormURI) throws IOException {
		initialize();
		if (protectedPageHasBeenRequested()) {
			response.reset();
			addRedirectCookie(currentRequestURI);
		} else {
			resetRedirectCookie();
		}
		redirectTo(loginFormURI);
	}
	
	public void processLogin() throws IOException, ServletException{
		initialize();
		logoutBeforeLogin();
		String username=getUserName();
		String password=getPassword();
		request.login(username, password);
		resetRedirectCookie();
		redirectTo(getDestinationAfterLogin());
	}
	
	public String getDestinationAfterLogin() {
		if(redirectCookie == null){
					return "/latte/index.html";
		}
		return redirectCookie.getValue(); 
	}
	

	private void logoutBeforeLogin() throws ServletException {
		if(request.getUserPrincipal() != null ){
			request.logout();
		}
	}

	private String getPassword() {
		return Objects.requireNonNull(request.getParameter("password"));
	}

	private String getUserName() {
		return Objects.requireNonNull(request.getParameter("username"));
	}

	private void redirectTo(String loginFormURI) throws IOException {
		response.addDateHeader("Date", System.currentTimeMillis());
		response.addDateHeader("Expires", 786297600000l);
		response.addHeader("Cache-Control", "no-cache, no-store");
		response.setContentType("text/plain");
		response.sendRedirect(loginFormURI);
	}

	private boolean protectedPageHasBeenRequested() {
		return currentRequestURI != null;
	}

	private void initialize() {
		currentRequestURI = getRequestedURI();
		redirectCookie = getLatteRedirectCookie();
	}

	public Cookie getLatteRedirectCookie() {
		Cookie[] cookies = request.getCookies();
		if(cookies==null){
			return null;
		}
		for (Cookie cookie : cookies) {
			if (REDIRECT_COOKIE_NAME.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

	public String getRequestedURI() {
		return (String) request.getAttribute(ATTR_REQUEST_URI);
	}

	public void addRedirectCookie(String redirectURI) {
		Cookie cookie = new Cookie(REDIRECT_COOKIE_NAME, redirectURI);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public void resetRedirectCookie() {
		if (redirectCookie != null) {
			Cookie cookie = new Cookie(REDIRECT_COOKIE_NAME, "");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}

}
