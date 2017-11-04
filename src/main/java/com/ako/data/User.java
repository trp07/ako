package com.ako.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * User model representing User table
 * @author Prashant
 *
 */
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private DateTime createDate;
	private DateTime lastModifiedDate;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate  birthDate;
	private String email;
	private String password;
	private String userTypeId; /* Create the user type object */
	private boolean hasMfaActive;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public DateTime getCreateDate() {
		return createDate;
	}

	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}
	
	public boolean getHasMfaActive() {
		return hasMfaActive;
	}

	public void setHasMfaActive(boolean hasMfaActive) {
		this.hasMfaActive = hasMfaActive;
	}
}
