package com.ako.data;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * User model representing User table
 * @author Prashant
 *
 */
@Entity
@Table(name = "User")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createDate;
	
	//@Column(nullable = false, updatable = false)
    //@Temporal(TemporalType.TIMESTAMP)
	//@LastModifiedDate
	//private Date lastModifyDate;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String middleName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private Date  birthDate;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@Column(nullable = false)
	private int userTypeId;
	
	@Column(nullable = false)
    private boolean hasMfaActive;
    
    @Column(nullable = false)
	private String secret;
	
	@Column(nullable = false)
	private String userName;
	
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "User_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Course_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private List<Course> userCourses;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Group_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private List<Group> userGroups;
    

    
	public int getId() {
		return id;
	}
	
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}
	/*
	public Date getLastModifyDate() {
		return lastModifyDate;
	}*/
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
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
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
	public int getUserTypeId () {
		return userTypeId;
	}
	public void setUserType(int userTypeId){
		this.userTypeId = userTypeId;
	}
	
	public boolean getHasMfaActive() {
		return hasMfaActive;
	}
	public void setHasMfaActive(boolean hasMfaActive) {
		this.hasMfaActive = hasMfaActive;
	}
	
	@JsonIgnore
	public String getSecret() {
		return secret;
	}
	@JsonIgnore
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		// TODO Auto-generated method stub
		this.authorities = (List<Authority>) authorities;
	}

	@JsonIgnore
	public List<Course> getUserCourses() {
		return userCourses;
	}

	@JsonIgnore
	public List<Group> getUserGroups() {
		return userGroups;
	}
	
	public void setUserGroups(List<Group> userGroups) {
		this.userGroups = userGroups;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}