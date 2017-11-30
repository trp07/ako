package com.ako.data;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name="File")
@JsonIgnoreProperties(value = {"id", "createDate"}, allowGetters = true)
public class File {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private LocalDate createDate;
	
	@Column(nullable = true) // Schema does not require for a file to have a module id. File could be used in a Message
	private int moduleId;
	
	@Column(nullable  = false)
	private String name;
	
	@Column(nullable  = false)
	private String description;
	
	@Column(nullable  = false)
	private String fileS3Url;
	
    @JsonGetter("id")
	public int getId() {
		return id;
	}
    @JsonGetter("createDate")
	public LocalDate getCreateDate() {
		return createDate;
	}
    
    @JsonGetter("moduleId")
    public int getModuleId() {
    	return moduleId;
    }
    @JsonSetter("moduleId")
    public void setModuleId(int moduleId) {
    	this.moduleId = moduleId;
    }
    @JsonGetter("name")
    public String getName() {
    	return name;
    }
    @JsonSetter("name")
    public void setName(String name) {
    	this.name = name;
    }
    @JsonGetter("description")
    public String getDescription() {
    	return description;
    }
    @JsonSetter("description")
    public void setDescription(String description) {
    	this.description = description;
    }
    @JsonGetter("fileS3Url")
    public String getFileS3Url() {
    	return description;
    }
    @JsonSetter("fileS3Url")
    public void setFileS3Url(String fileS3Url) {
    	this.fileS3Url = fileS3Url;
    }
    
}
