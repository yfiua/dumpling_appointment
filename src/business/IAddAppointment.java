package business;

import javax.ejb.Remote;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import entities.Appointment;
import entities.User;

import entities.Appointment.AppointmentType;

@Remote
public interface IAddAppointment {

	Map<Integer, String> tryToAddAppointment(User user, List<User> additionalUsers, String title,
			String notes, Calendar start_date, Calendar end_date,
			AppointmentType type, boolean is_private);
	Appointment getAppointmentDetail(int appointment_id);
	boolean addAppointment(User user, List<User> additionalUsers, String title,
			String notes, Calendar start_date, Calendar end_date,
			AppointmentType type, boolean is_private);
}
