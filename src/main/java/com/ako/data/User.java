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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.LocalDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonSetter;

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
	private LocalDate createDate;
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private LocalDate lastModifyDate;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String middleName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private LocalDate  birthDate;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@Column(nullable = false)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USER_TYPE_ID", nullable=true) // TODO need to verify that this syntax is correct
	private UserType userType;
	
	@Column(nullable = false)
    private boolean hasMfaActive;
    
    @Column(nullable = false)
	private String secret;
	
	@Column(nullable = false)
	private String userName;
	
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;
    
    @JsonGetter("id")
	public int getId() {
		return id;
	}
    @JsonGetter("createDate")
	public LocalDate getCreateDate() {
		return createDate;
	}
    @JsonGetter("lastModifyDate")
	public LocalDate getLastModifyDate() {
		return lastModifyDate;
	}
    @JsonGetter("firstName")
	public String getFirstName() {
		return firstName;
	}
    @JsonSetter("firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@JsonGetter("middleName")
	public String getMiddleName() {
		return middleName;
	}
	@JsonSetter("middleName")
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	@JsonGetter("lastName")
	public String getLastName() {
		return lastName;
	}
	@JsonSetter("lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@JsonGetter("birthDate")
	public LocalDate getBirthDate() {
		return birthDate;
	}
	@JsonSetter("birthDate")
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	@JsonGetter("email")
	public String getEmail() {
		return email;
	}
	@JsonSetter("email")
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonGetter("password")
	public String getPassword() {
		return password;
	}
	@JsonSetter("password")
	public void setPassword(String password) {
		this.password = password;
	}
	@JsonGetter("userType") //TODO Verify if we want to return the user_type entity or simply the user_type_id
	public UserType getUserType () {
		return userType;
	}
	@JsonSetter("userType")
	public void setUserType(UserType userType){
		this.userType = userType;
	}
	
	@JsonGetter("hasMfaActive")
	public boolean getHasMfaActive() {
		return hasMfaActive;
	}
	@JsonSetter("hasMfaActive")
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
	@JsonGetter("userName")
	public String getUserName() {
		return userName;
	}
	@JsonSetter("userName")
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
