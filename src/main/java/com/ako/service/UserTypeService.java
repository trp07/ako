package com.ako.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ako.data.IUserTypeRepository;
import com.ako.data.UserType;

/**
 * 	A user type business service
 * @author noel.buruca
 *
 */
@Service
public class UserTypeService {
	
	@Autowired
	IUserTypeRepository repository;
	
	/**
	 * Fetch all the user types
	 * @return Return a list of all user types
	 */
	public List<UserType> getAllUserTypes(){
		List<UserType> userTypes = new ArrayList<>();
		this.repository.findAll().forEach(userTypes::add);
		return userTypes;
	}
	
	/**
	 * Fetch user types by id
	 * @param id
	 * @return The user type identified by given id
	 */
	public UserType getUserType(int id) {
		return this.repository.findOne(id);
	}
}
