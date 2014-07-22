package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.CollectionType;

@Entity(name = "user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	 
    public User() {
        super();
    }

    
    
    private int user_id;
    
    private String user_name;
    private String email;
    
    private String pass;
    
    private Set<Appointment> appointments = new HashSet<Appointment>(0);

    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name="user_appointment", 
                joinColumns={@JoinColumn(name="user_id")}, 
                inverseJoinColumns={@JoinColumn(name="appointment_id")})
	public Set<Appointment> getAppointments() {
		return appointments;
	}
	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	@Override
    public String toString() {
        return "User [name=" + user_name + ", email=" + email
                + ", password=" + pass + "]";
    }
}
