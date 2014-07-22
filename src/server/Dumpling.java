package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import businesslogic.UserBean;

import entities.Appointment;
import entities.User;
import entities.Appointment.AppointmentType;

/**
 * Servlet implementation class Dumpling
 */
@WebServlet("/cookDumpling")
public class Dumpling extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB businesslogic.AddAppointmentBean addBean;
	@EJB businesslogic.ViewAppointmentsBean viewBean;
	@EJB businesslogic.UserBean userBean;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dumpling() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Check whether the user is the logged in user
		String email = request.getParameter("email");
		HttpSession session = request.getSession();
		if (null == session.getAttribute("username") || !session.getAttribute("email").equals(email)) {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('This user is currently not logged in!');");
			out.println("window.location.href='login.jsp';");
			out.println("</script>");
			return;
		}
		
		String action = request.getParameter("action");
		if ("view".equals(action)) {
			StringBuffer json = new StringBuffer("{\"appointments\":[");
			User self = userBean.findUserByEmail(email);
			Calendar date = Calendar.getInstance();
			Map<Integer, String> list = viewBean.viewAppointments(self, date);
			for (Map.Entry<Integer, String> entry : list.entrySet()) {
				Appointment a = viewBean.getAppointmentDetail(entry.getKey());
				json.append("{\"id\":"	+ a.getAppointment_id()
						+ ", \"title\":\""	+ a.getTitle()
						+ "\", \"start\":\""	+ a.getStart_date().getTimeInMillis()
						+ "\", \"end\":\""	+ a.getEnd_date().getTimeInMillis()
						+ "\", \"type\":\""	+ a.getApp_type()
						+ "\", \"notes\":\""	+ a.getNotes()
						+ "\", \"is_private\":"	+ a.isIs_private()
						+ " , \"users\":[");
				for (User u : a.getUsers()) {
					json.append(" {\"name\":\""	+ u.getUser_name()
							+ "\", \"email\":\"" + u.getEmail() + "\"},");
				}
				json.deleteCharAt(json.length() - 1);
				json.append("]},");
			}
			json.deleteCharAt(json.length() - 1);
			json.append("]}");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
		} else if ("users".equals(action)) {
			StringBuffer json = new StringBuffer("{\"users\":[");
			List<User> l = userBean.retrieveAllUsers();
			for (User u : l) {
				json.append("{\"id\":"		+ u.getUser_id()
						+ ", \"name\":\""	+ u.getUser_name()
						+ "\", \"email\":\""+ u.getEmail() + "\"},");
			}
			json.deleteCharAt(json.length() - 1);
			json.append("]}");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
		} else if ("check".equals(action)) {
			// Get parameters from frontend
			String title = request.getParameter("title");
			String str_start_time = request.getParameter("start_time");
			String str_end_time = request.getParameter("end_time");
			String[] users = request.getParameterValues("involvers");
			String str_type = request.getParameter("type");
			String notes = request.getParameter("notes");
			String str_is_private = request.getParameter("is_private");
			
			// Prepare parameters
			User self = userBean.findUserByEmail(email);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Calendar start_date = new GregorianCalendar();
			Calendar end_date = new GregorianCalendar();
			try {
				start_date.setTime(df.parse(str_start_time));
				end_date.setTime(df.parse(str_end_time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<User> additionalUsers = userBean.retrieveAllUsers();
			additionalUsers.clear();
			if (users != null) {
				int id;
				for (String str_id : users) {
					id = Integer.parseInt(str_id); //TODO: try...catch NumberFormatException
					//System.out.println(id);
					additionalUsers.add(userBean.findUserById(id));
				}
			}
			AppointmentType type = AppointmentType.BLOCKED;
			if ("BLOCKED".equals(str_type)) {
				type = AppointmentType.BLOCKED;
			} else if ("FREE".equals(str_type)) {
				type = AppointmentType.FREE;
			} else if ("PONTETIALLY_BLOCKED".equals(str_type)) {
				type = AppointmentType.PONTETIALLY_BLOCKED;
			} else if ("AWAY".equals(str_type)) {
				type = AppointmentType.AWAY;
			}
			boolean is_private = (str_is_private == null) ? false : true;
			Map<Integer, String> conflicts = addBean.tryToAddAppointment(self,
					additionalUsers, title, notes, start_date, end_date, type,
					is_private);
			
			// Preparing json
			StringBuffer json = new StringBuffer();
			if (conflicts == null) {
				json.append("{\"ok\": 1}");
			} else {
				json.append("{\"ok\": 0, \"conflicts\": [");
				for (Map.Entry<Integer, String> c: conflicts.entrySet()) {
					User u = userBean.findUserByEmail(c.getValue());
					Appointment a = addBean.getAppointmentDetail(c.getKey());
					json.append("{\"user\": \"" + u.getUser_name() + "\", \"appointment\": ");
					if (a != null) {	// is public
						json.append("{\"is_private\": 0, "
								+ "\"title\":\""	+ a.getTitle()
								+ "\", \"start\":\""	+ a.getStart_date().getTimeInMillis()
								+ "\", \"end\":\""	+ a.getEnd_date().getTimeInMillis()
								+ "\", \"type\":\""	+ a.getApp_type()
								+ "\", \"notes\":\""	+ a.getNotes() + "\"}");
					} else {			// is private
						json.append("{\"is_private\": 1}");
					}
					json.append("},");
				}
				json.deleteCharAt(json.length() - 1);
				json.append("]}");
			}
			
			// Response back to frontend
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
		} else if ("add".equals(action)) {
			// Get parameters from frontend
			String title = request.getParameter("title");
			String str_start_time = request.getParameter("start_time");
			String str_end_time = request.getParameter("end_time");
			String[] users = request.getParameterValues("involvers");
			String str_type = request.getParameter("type");
			String notes = request.getParameter("notes");
			String str_is_private = request.getParameter("is_private");
			
			// Prepare parameters
			User self = userBean.findUserByEmail(email);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Calendar start_date = new GregorianCalendar();
			Calendar end_date = new GregorianCalendar();
			try {
				start_date.setTime(df.parse(str_start_time));
				end_date.setTime(df.parse(str_end_time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<User> additionalUsers = userBean.retrieveAllUsers();
			additionalUsers.clear();
			if (users != null) {
				int id;
				for (String str_id : users) {
					id = Integer.parseInt(str_id); //TODO: try...catch NumberFormatException
					//System.out.println(id);
					additionalUsers.add(userBean.findUserById(id));
				}
			}
			AppointmentType type = AppointmentType.BLOCKED;
			if ("BLOCKED".equals(str_type)) {
				type = AppointmentType.BLOCKED;
			} else if ("FREE".equals(str_type)) {
				type = AppointmentType.FREE;
			} else if ("PONTETIALLY_BLOCKED".equals(str_type)) {
				type = AppointmentType.PONTETIALLY_BLOCKED;
			} else if ("AWAY".equals(str_type)) {
				type = AppointmentType.AWAY;
			}
			boolean is_private = (str_is_private == null) ? false : true;
			Map<Integer, String> conflicts = addBean.tryToAddAppointment(self,
					additionalUsers, title, notes, start_date, end_date, type,
					is_private);
			
			boolean success = false;
			if (conflicts == null) {
				success = addBean.addAppointment(self,
					additionalUsers, title, notes, start_date, end_date, type,
					is_private);
			}
			
			String json = "{\"success\": " + (success ? 1 : 0) + "}";
			// Response back to frontend
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
		}
		
		return;
	}
}
