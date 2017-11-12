package com.ako.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * User Type model representing UserType table
 * 	Table already populated in database
 * @author noel.buruca
 */

@Entity
public class UserType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String type;
	
	private int getId() {
		return this.id;
	}
	
	private String getType() {
		return this.type;
	}
}
