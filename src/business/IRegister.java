package business;

import javax.ejb.Remote;
import entities.User;

@Remote
public interface IRegister {

	User registerUser(String name, String email, String password);
}
