package com.ako.data;

import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	private Date createDate;

	@Column(nullable = false)
	String shortName;

	@Column(nullable = false)
	String year;

	@OneToOne(optional = false)
	@JoinColumn(name="id")
	Semester semester;

	@Column(nullable = false)
	String section;

	@Column(nullable = false)
	String description;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	/*	@JoinTable(name = "Course_user",
	joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))*/
	private List<User> courseUsers;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<Module> courseModules;
	
	// Needed to complete the one to one mapping. Does not need to be stored in the database
    @OneToOne(mappedBy = "course")
    private Module module;

	@JsonGetter("id")
	public int getId() {
		return id;
	}
	@JsonGetter("createDate")
	public Date getCreateDate() {
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

	public Semester getSemester(){
		return semester;
	}
	public void setSemester(Semester semester) {
		this.semester = semester;
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
	
    public List<Module> getCourseModules() {
    	return this.courseModules;
    }
    public void setCourseModules(List<Module> courseModules){
    	this.courseModules = courseModules;
    }
}
