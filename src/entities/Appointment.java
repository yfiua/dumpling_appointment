package entities;

import java.io.Serializable;

import java.util.Calendar;
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

@Entity(name = "appointment")
public class Appointment implements Serializable{
	
	public enum AppointmentType{
		BLOCKED, FREE, PONTETIALLY_BLOCKED, AWAY
	}
	private static final long serialVersionUID = 1L;
	
	public Appointment() {
        super();
    }
	
	
	private int appointment_id;
	private Calendar start_date;;
	private Calendar end_date;
	private String title;
	private String notes;
	private String app_type;
	private boolean is_private;
	
	private Set<User> users = new HashSet<User>(0);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}

	public Calendar getStart_date() {
		return start_date;
	}

	public void setStart_date(Calendar start_date) {
		this.start_date = start_date;
	}

	public Calendar getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Calendar end_date) {
		this.end_date = end_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	public boolean isIs_private() {
		return is_private;
	}

	public void setIs_private(boolean is_private) {
		this.is_private = is_private;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy="appointments")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
    public String toString() {
        return "Appointment [start date=" + start_date.getTime().toGMTString()
        		+ ", end date=" + end_date.getTime().toGMTString()
                + ", title=" + title + ", notes=" + notes + ", type=" + app_type + "]";
    }
	
	

}
