package business;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import entities.Appointment;
import entities.User;

@Remote
public interface IUserManagement {
	
	void saveUser(User user);
	User findUser (User user);
    List<User> retrieveAllUsers();
    void updateUser(User user);
    User findUserByEmail(String email);
    void addAppointment(User user, Appointment appointment);
	User findUserById(int id);
}
