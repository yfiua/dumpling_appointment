package businesslogic;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import business.IAddAppointment;
import business.IAppointmentManagement;
import business.IUserManagement;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NamingException;

import clientutility.JNDILookupClass;

import entities.Appointment;
import entities.User;
import entities.Appointment.AppointmentType;

/**
 * Session Bean implementation class AddAppointment
 */
@Stateless
@LocalBean
public class AddAppointmentBean implements IAddAppointment {

	//private IUserManagement userManager = doLookupUser();
	//private IAppointmentManagement appointmentManager = doLookupAppointment();
	@EJB businesslogic.UserBean userManager;
	@EJB businesslogic.AppointmentBean appointmentManager;
	
	public AddAppointmentBean() {
	}

	@Override
	public Map<Integer, String> tryToAddAppointment(User user,
			List<User> additionalUsers, String title, String notes,
			Calendar start_date, Calendar end_date, AppointmentType type,
			boolean is_private) {
		List<User> users = new LinkedList<User>();
		users.add(user);
		if (additionalUsers != null)
			users.addAll(additionalUsers);
		Map<Integer, String> conflicts = new HashMap<Integer, String>();
		if (type != AppointmentType.FREE) {
			for (User u : users) {
				for (Appointment a : u.getAppointments()) {
					if (!a.getApp_type()
							.equals(AppointmentType.FREE.toString())
							&& checkForOverlapping(a.getStart_date(),
									a.getEnd_date(), start_date, end_date)) {
						for (User conflictUser : a.getUsers())
							conflicts.put(a.getAppointment_id(),
									conflictUser.getEmail());
					}
				}
			}
		}
		if (!conflicts.isEmpty())
			return conflicts;
		else
			return null;
	}

	@Override
	public Appointment getAppointmentDetail(int appointment_id) {
		Appointment app =  appointmentManager.findAppointmentById(appointment_id);
		if(app.isIs_private())
			return null;
		else return app;
	}

	@Override
	public boolean addAppointment(User user, List<User> additionalUsers,
			String title, String notes, Calendar start_date, Calendar end_date,
			AppointmentType type, boolean is_private) {
		Appointment app = new Appointment();
		app.setTitle(title);
		app.setNotes(notes);
		app.setStart_date(start_date);
		app.setEnd_date(end_date);
		app.setApp_type(type.toString());
		app.setIs_private(is_private); 
		//appointmentManager.saveAppointment(app); // maped by user???

		List<User> users = new LinkedList<User>();
		users.add(user);
		if (additionalUsers != null)
			users.addAll(additionalUsers);
		for (User u : users) {
			userManager.addAppointment(u, app);
			userManager.updateUser(u);
		}
		return true;
	}

	// returns true if there is overlapping
	private boolean checkForOverlapping(Calendar c1_start, Calendar c1_end,
			Calendar c2_start, Calendar c2_end) {
		if (c2_start.getTime().before(c1_start.getTime())
				&& c2_end.getTime().before(c1_start.getTime())
				|| c2_start.getTime().after(c1_end.getTime())
				&& c2_end.getTime().after(c1_end.getTime()))
			return false;
		else
			return true;
	}

	private static IUserManagement doLookupUser() {
		Context context = null;
		IUserManagement bean = null;
		try {
			// 1. Obtaining Context
			context = JNDILookupClass.getInitialContext();
			// 2. Generate JNDI Lookup name
			String lookupName = getLookupNameUser();
			// 3. Lookup and cast
			bean = (IUserManagement) context.lookup(lookupName);

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return bean;
	}

	private static String getLookupNameUser() {
		/*
		 * The app name is the EAR name of the deployed EJB without .ear suffix.
		 * Since we haven't deployed the application as a .ear, the app name for
		 * us will be an empty string
		 */
		String appName = "";

		/*
		 * The module name is the JAR name of the deployed EJB without the .jar
		 * suffix.
		 */
		String moduleName = "CBSEProject";

		/*
		 * AS7 allows each deployment to have an (optional) distinct name. This
		 * can be an empty string if distinct name is not specified.
		 */
		String distinctName = "";

		// The EJB bean implementation class name
		String beanName = UserBean.class.getSimpleName();

		// Fully qualified remote interface name
		final String interfaceName = IUserManagement.class.getName();

		// Create a look up string name
		String name = "ejb:" + appName + "/" + moduleName + "/" + distinctName
				+ "/" + beanName + "!" + interfaceName;
		return name;
	}

	private static IAppointmentManagement doLookupAppointment() {
		Context context = null;
		IAppointmentManagement bean = null;
		try {
			// 1. Obtaining Context
			context = JNDILookupClass.getInitialContext();
			// 2. Generate JNDI Lookup name
			String lookupName = getLookupNameAppointment();
			// 3. Lookup and cast
			bean = (IAppointmentManagement) context.lookup(lookupName);

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return bean;
	}

	private static String getLookupNameAppointment() {
		/*
		 * The app name is the EAR name of the deployed EJB without .ear suffix.
		 * Since we haven't deployed the application as a .ear, the app name for
		 * us will be an empty string
		 */
		String appName = "";

		/*
		 * The module name is the JAR name of the deployed EJB without the .jar
		 * suffix.
		 */
		String moduleName = "CBSEProject";

		/*
		 * AS7 allows each deployment to have an (optional) distinct name. This
		 * can be an empty string if distinct name is not specified.
		 */
		String distinctName = "";

		// The EJB bean implementation class name
		String beanName = AppointmentBean.class.getSimpleName();

		// Fully qualified remote interface name
		final String interfaceName = IAppointmentManagement.class.getName();

		// Create a look up string name
		String name = "ejb:" + appName + "/" + moduleName + "/" + distinctName
				+ "/" + beanName + "!" + interfaceName;
		return name;
	}

}
