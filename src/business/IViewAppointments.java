package business;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import entities.Appointment;
import entities.User;

@Remote
public interface IViewAppointments {

	Map<Integer, String> viewAppointments(User user, Calendar date);
	Appointment getAppointmentDetail(int appointent_id);
}
