package business;

import javax.ejb.Remote;

import entities.User;

@Remote
public interface ILogIn {

	User login(String email, String password);
}
