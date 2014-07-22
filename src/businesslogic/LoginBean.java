package businesslogic;

import business.ILogIn;
import business.IUserManagement;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NamingException;

import clientutility.JNDILookupClass;

import entities.User;

@Stateless
@LocalBean
public class LoginBean implements ILogIn {

	//private IUserManagement userManager = doLookupUser();
	@EJB businesslogic.UserBean userManager;
	
	public LoginBean() {	}
    
    public User login(String email, String password){
    	User u = userManager.findUserByEmail(email);
    	if(u == null)
    		return null;
    	if(!u.getPass().equals(password))
    		return null;
    	return u;
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

}
