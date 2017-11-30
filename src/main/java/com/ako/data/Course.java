package com.ako.data;

import java.time.LocalDate;
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

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name="Course")
@JsonIgnoreProperties(value = {"id", "createDate"}, allowGetters = true)
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private LocalDate createDate;
	
	@Column(nullable = false)
	String shortName;
	
	@Column(nullable = false)
	String year;
	
	@Column(nullable = false)
	int semesterId;
	
	@Column(nullable = false)
	String section;
	
	@Column(nullable = false)
	String description;
	
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Course_user",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> courseUsers;
    
    @JsonGetter("id")
	public int getId() {
		return id;
	}
    @JsonGetter("createDate")
	public LocalDate getCreateDate() {
		return createDate;
	}
    @JsonGetter("shortName")
    public String getShortName(){
    	return shortName;
    }
    @JsonSetter("shortName")
    public void setShortName(String shortName) {
    	this.shortName = shortName;
    }
    
    @JsonGetter("year")
    public String getYear(){
    	return year;
    }
    @JsonSetter("year")
    public void setYear(String year) {
    	this.year = year;
    }
    
    @JsonGetter("semesterId")
    public int getSemesterId(){
    	return semesterId;
    }
    @JsonSetter("semesterId")
    public void setSemesterId(int semesterId) {
    	this.semesterId = semesterId;
    }
    @JsonGetter("section")
    public String getSection(){
    	return section;
    }
    @JsonSetter("section")
    public void setSection(String section) {
    	this.section = section;
    }
    @JsonGetter("description")
    public String getDescription(){
    	return description;
    }
    @JsonSetter("description")
    public void setDescription(String description) {
    	this.description = description;
    }
    public List<User> getCourseUsers() {
    	return this.courseUsers;
    }
    public void setCourseUsers(List<User> courseUsers){
    	this.courseUsers = courseUsers;
    }
}
