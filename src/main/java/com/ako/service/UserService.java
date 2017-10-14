package com.ako.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ako.data.IUserRepository;
import com.ako.data.User;

/**
 * 	A user business service
 * @author Prashant
 *
 */
@Service
public class UserService {
	
	@Autowired
	IUserRepository repository;
	
	/**
	 * Fetch all the users
	 * @return Return a list of all users
	 */
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>();
		this.repository.findAll().forEach(users::add);
		return users;
	}
	
	/**
	 * Fetch user by id
	 * @param id
	 * @return The user identified by given id
	 */
	public User getUser(int id) {
		return this.repository.findOne(id);
	}

	/**
	 * Add an user
	 * @param user
	 * @return The added user
	 */
	public User addUser(User user) {
		if(this.repository.exists(user.getId())) {
			return this.updateUser(user);
		}
		return this.repository.save(user);
	}
	
	/**
	 * Update the user
	 * @param user
	 * @return The updated user
	 */
	public User updateUser(User user) {
		return this.repository.save(user);
	}
}
