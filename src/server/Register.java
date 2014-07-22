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
 * Servlet implementation class Register
 */
@WebServlet(
		urlPatterns = { "/doRegister" }, 
		initParams = { 
				@WebInitParam(name = "username", value = ""), 
				@WebInitParam(name = "email", value = ""), 
				@WebInitParam(name = "password", value = ""), 
				@WebInitParam(name = "re_password", value = "")
		})
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB businesslogic.RegisterBean bean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String re_password = request.getParameter("re_password");
		
		if (!password.equals(re_password)) {
			PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('password != re_password!');");  
			out.println("window.location.href='register.jsp';");
			out.println("</script>");
			return;
		}
		
		User u = bean.registerUser(username, email, password);

		if (u != null) { // Registered successfully
			// Log in
			HttpSession session = request.getSession();
			session.setAttribute("userID", u.getUser_id());
			session.setAttribute("username", u.getUser_name());
			session.setAttribute("email", u.getEmail());
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Registered successfully!');");
			out.println("window.location.href='mainform.jsp';");
			out.println("</script>");
			return;
		} else {				// Registered not successfully
			PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Registered not successfully! The email is already in use.');");  
			out.println("window.location.href='register.jsp';");
			out.println("</script>");
			return;
		}
	}

}
