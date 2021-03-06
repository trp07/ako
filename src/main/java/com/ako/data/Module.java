package com.ako.data;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name="Module")
public class Module {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createDate;
	
	@Column(nullable = false)
	private int courseId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private boolean isPublished;;
	
	@OneToMany(mappedBy = "module")
	private List<File> moduleFiles;
	
	@JsonGetter("id")
	public int getId(){
    	return id;
    }
	@JsonSetter("id")
	public void setId(int id){
    	this.id = id;
    }
	@JsonGetter("createDate")
	public Date getCreateDate(){
    	return createDate;
    }
	@JsonSetter("createDate")
	public void setCreateDate(Date createDate){
    	this.createDate = createDate;
    }
	@JsonGetter("courseId")
    public int getCourseId(){
    	return courseId;
    }
	@JsonSetter("courseId")
    public void setCourseId(int courseId) {
    	this.courseId = courseId;
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
    @JsonGetter("isPublished")
    public boolean getIsPublished(){
    	return isPublished;
    }
    @JsonSetter("isPublished")
    public void setIsPublished(boolean isPublished) {
    	this.isPublished = isPublished;
    }
    public List<File> getModuleFiles() {
    	return this.moduleFiles;
    }
    public void setModuleFiles(List<File> moduleFiles){
    	this.moduleFiles = moduleFiles;
    }
	
}
