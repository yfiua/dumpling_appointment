package businesslogic;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import business.IAppointmentManagement;
import business.IUserManagement;
import business.IViewAppointments;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.Query;
import javax.print.attribute.HashAttributeSet;

import clientutility.JNDILookupClass;

import entities.Appointment;
import entities.User;


@Stateless
@LocalBean
public class ViewAppointmentsBean implements IViewAppointments {

	//private IUserManagement userManager = doLookupUser();
	//private IAppointmentManagement appointmentManager = doLookupAppointment();
	@EJB businesslogic.UserBean userManager;
	@EJB businesslogic.AppointmentBean appointmentManager;
	
    public ViewAppointmentsBean() {    }
    
    @Override
    public Map<Integer, String> viewAppointments(User user, Calendar date){
    	List<Appointment> ls = new LinkedList<Appointment>();
    	Map<Integer, String> hm = new HashMap<Integer, String>();
    	ls.addAll(user.getAppointments());
    	for(Appointment app : ls)
    		//if(app.getStart_date().get(Calendar.YEAR) == (date.get(Calendar.YEAR)) && 
    		//		app.getStart_date().get(Calendar.MONTH) == (date.get(Calendar.MONTH)) &&
    		//		app.getStart_date().get(Calendar.DAY_OF_MONTH) == (date.get(Calendar.DAY_OF_MONTH)))
    			hm.put(app.getAppointment_id(), app.getTitle());
    	return hm;
    }
    
    @Override
    public Appointment getAppointmentDetail(int appointment_id){
    	return appointmentManager.findAppointmentById(appointment_id);
    }
    
    //could add a function to check if two dates are the same

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
        /*The app name is the EAR name of the deployed EJB without .ear
        suffix. Since we haven't deployed the application as a .ear, the app
        name for us will be an empty string */
        String appName = "";
 
        /* The module name is the JAR name of the deployed EJB without the
        .jar suffix.*/
        String moduleName = "CBSEProject";
 
        /* AS7 allows each deployment to have an (optional) distinct name.
        This can be an empty string if distinct name is not specified.*/
        String distinctName = "";
 
        // The EJB bean implementation class name
        String beanName = UserBean.class.getSimpleName();
 
        // Fully qualified remote interface name
        final String interfaceName = IUserManagement.class.getName();
 
        // Create a look up string name
        String name = "ejb:" + appName + "/" + moduleName + "/" +
                distinctName    + "/" + beanName + "!" + interfaceName;
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
