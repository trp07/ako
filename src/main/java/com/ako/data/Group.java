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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name="Group")
@JsonIgnoreProperties(value = {"id", "createDate"}, allowGetters = true)
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createDate;
	
	@OneToOne(optional = false)
	@JoinColumn(name="id")
	private Course course;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Group_user",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> groupUsers;
    
    @JsonGetter("id")
	public int getId() {
		return id;
	}
    @JsonGetter("createDate")
	public Date getCreateDate() {
		return createDate;
	}
    public Course getCourse(){
    	return course;
    }
    public void setCourseId(Course course) {
    	this.course = course;
    }
    @JsonGetter("name")
    public String getName(){
    	return name;
    }
    @JsonSetter("name")
    public void setName(String name) {
    	this.name = name;
    }
    @JsonGetter("description")
    public String getDescription(){
    	return description;
    }
    @JsonSetter("description")
    public void setDescription(String description) {
    	this.description = description;
    }
    public List<User> getGroupUsers() {
    	return this.groupUsers;
    }
    public void setGroupUsers(List<User> groupUsers){
    	this.groupUsers = groupUsers;
    }
}
