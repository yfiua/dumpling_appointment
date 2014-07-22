package business;

import java.util.List;

import javax.ejb.Remote;

import entities.Appointment;

@Remote
public interface IAppointmentManagement {

	void saveAppointment(Appointment appointment);
	Appointment findAppointment (Appointment appointment);
    List<Appointment> retrieveAllAppointments();
    void updateAppointment(Appointment appointment);
    Appointment findAppointmentById(int appointment_id);
}
