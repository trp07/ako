package com.ako.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private Date createDate;
	
	@ManyToOne(optional = true) // Schema allows this to be null
	@JoinColumn(name="module_id")
	private Module module;
	
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
	public Date getCreateDate() {
		return createDate;
	}
    
    public Module getModule() {
    	return module;
    }
    public void setModule(Module module) {
    	this.module = module;
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
    
    @Override
    public boolean equals(Object object) {
    	if (object == this) {
			// The objects are of the same reference
			return true;
		} else if (object instanceof File) {
			//Two files are the same if they have the same id
			return (((File) object).getId() == this.id);
		}
    	return false;
    }
    
}
