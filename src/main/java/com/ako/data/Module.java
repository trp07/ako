package com.ako.data;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name="Module")
@JsonIgnoreProperties(value = {"id", "createDate"}, allowGetters = true)
public class Module {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createDate;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name="module_id")
	private Course course;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	
	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
	private List<File> moduleFiles;
	
    public Course getCourse(){
    	return course;
    }
    public void setCourse(Course courseId) {
    	this.course = courseId;
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
    public List<File> getModuleFiles() {
    	return this.moduleFiles;
    }
    public void setModuleFiles(List<File> moduleFiles){
    	this.moduleFiles = moduleFiles;
    }
	
}
