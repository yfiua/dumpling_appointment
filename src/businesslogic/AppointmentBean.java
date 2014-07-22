package businesslogic;

import java.util.List;

import business.IAppointmentManagement;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Appointment;

/**
 * Session Bean implementation class AppointmentBean
 */
@Stateless
@LocalBean
public class AppointmentBean implements IAppointmentManagement {

	@PersistenceContext(unitName = "cbse")
    private EntityManager entityManager;
 
    public AppointmentBean() {   }
 
    @Override
    public void saveAppointment(Appointment appointment) {
        entityManager.persist(appointment);
        entityManager.flush();
    }
 
    @Override
    public Appointment findAppointment(Appointment appointment) {
    	Appointment a = entityManager.find(Appointment.class, appointment.getAppointment_id());
        return a;
    }
 
    @Override
    public List<Appointment> retrieveAllAppointments() {
 
        String q = "SELECT a from " + Appointment.class.getName() + " a";
        Query query = entityManager.createQuery(q);
        List<Appointment> appointments = query.getResultList();
        return appointments;
    }
    @Override
    public void updateAppointment(Appointment appointment) {
        entityManager.merge(appointment);
        entityManager.flush();
    }
    
    @Override
    public Appointment findAppointmentById(int appointment_id) {
    	Appointment a = entityManager.find(Appointment.class, appointment_id);
        return a;
    }

}
