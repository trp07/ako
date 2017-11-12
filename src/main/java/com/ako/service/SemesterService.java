package com.ako.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ako.data.ISemesterRepository;
import com.ako.data.Semester;

/**
 * 	A Semester business service
 * @author noel.buruca
 *
 */
@Service
public class SemesterService {
	
	@Autowired
	ISemesterRepository repository;
	
	/**
	 * Fetch all the semesters
	 * @return Return a list of all semesters
	 */
	public List<Semester> getAllSemesters(){
		List<Semester> semesters = new ArrayList<>();
		this.repository.findAll().forEach(semesters::add);
		return semesters;
	}
	
	/**
	 * Fetch semester by id
	 * @param id
	 * @return The semester identified by given id
	 */
	public Semester getSemester(int id) {
		return this.repository.findOne(id);
	}
}
