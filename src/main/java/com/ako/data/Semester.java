package com.ako.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Semester model representing Semester table.
 * 	Entity already populated in database
 * @author noel.buruca
 */
@Entity
public class Semester {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	
	private int getId() {
		return this.id;
	}
	
	private String getName() {
		return this.name;
	}
}
