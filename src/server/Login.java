package server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entities.*;

/**
 * Servlet implementation class Login
 */
@WebServlet(urlPatterns = { "/doLogin" }, initParams = {
		@WebInitParam(name = "email", value = ""),
		@WebInitParam(name = "password", value = "") })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	businesslogic.LoginBean bean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User u = bean.login(email, password);
		if (u != null) {	// Log in successfully
			// Set session values
			HttpSession session = request.getSession(); 
			session.setAttribute("userID", u.getUser_id());
			session.setAttribute("username", u.getUser_name());
			session.setAttribute("email", u.getEmail());
			// To main form
			response.sendRedirect("mainform.jsp");
		} else {			// Log in not successfully
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Sorry, wrong email or password. Please try again.');");
			out.println("window.location.href='login.jsp';");
			out.println("</script>");
			return;
		}
	}
}
