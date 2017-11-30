package com.ako.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * User Type model representing UserType table
 * 	Table already populated in database
 * @author noel.buruca
 */

@Entity
@Table(name = "User_type")
@JsonIgnoreProperties(value = {"id", "type"}, allowGetters = true)
public class UserType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String type;
	
	@JsonGetter("id")
	private int getId() {
		return this.id;
	}
	@JsonGetter("type")
	private String getType() {
		return this.type;
	}
}
