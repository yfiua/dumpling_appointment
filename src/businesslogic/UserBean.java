package businesslogic;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import business.IUserManagement;
import entities.Appointment;
import entities.User;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class UserBean
 */
@Stateless
@LocalBean
public class UserBean implements IUserManagement {

	 @PersistenceContext(unitName = "cbse")
	    private EntityManager entityManager;
	 
	    public UserBean() {   }
	 
	    @Override
	    public void saveUser(User user) {
	        entityManager.persist(user);
	        entityManager.flush();
	    }
	 
	    @Override
	    public User findUser(User user) {
	    	User u = entityManager.find(User.class, user.getUser_id());
	        return u;
	    }
	 
	    @Override
	    public List<User> retrieveAllUsers() {
	 
	        String q = "SELECT u from " + User.class.getName() + " u";
	        Query query = entityManager.createQuery(q);
	        List<User> users = query.getResultList();
	        return users;
	    }
	    @Override
	    public void updateUser(User user) {
	        entityManager.merge(user);
	        entityManager.flush();
	    }
	    @Override
	    public User findUserById(int id) {
	    	String q = "SELECT u from " + User.class.getName() + " u where u.user_id = " + Integer.toString(id);
	        Query query = entityManager.createQuery(q);
	        if(query.getResultList().isEmpty())
	        	return null;
	        User user = (User) query.getResultList().toArray()[0];
	        return user;
	    }
	    @Override
	    public User findUserByEmail(String email){
	    	String q = "SELECT u from " + User.class.getName() + " u where STRCMP(u.email, '" + email + "') = 0";
	        Query query = entityManager.createQuery(q);
	        if(query.getResultList().isEmpty())
	        	return null;
	        User user = (User) query.getResultList().toArray()[0];
	        return user;
	    }
	    @Override
	    public void addAppointment(User user, Appointment appointment){
	    	Set<Appointment> appointments = new HashSet<Appointment>();
	    	appointments = user.getAppointments();
	    	appointments.add(appointment);
	    	user.setAppointments(appointments);
	    	updateUser(user);	    	
	    }
}
