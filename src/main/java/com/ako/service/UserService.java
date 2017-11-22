package com.ako.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ako.data.IUserRepository;
import com.ako.data.User;

/**
 * A user business service
 * 
 * @author Prashant
 *
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	IUserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Fetch all the users
	 * 
	 * @return Return a list of all users
	 */
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		this.repository.findAll().forEach(users::add);
		return users;
	}

	/**
	 * Fetch user by id
	 * 
	 * @param id
	 * @return The user identified by given id
	 */
	public User getUser(int id) {
		return this.repository.findOne(id);
	}

	/**
	 * Add an user
	 * 
	 * @param user
	 * @return The added user
	 */
	public User addUser(User user) {
		if (this.repository.exists(user.getId())) {
			return this.updateUser(user);
		}
		return this.repository.save(user);
	}

	/**
	 * Update the user
	 * 
	 * @param user
	 * @return The updated user
	 */
	public User updateUser(User user) {
		return this.repository.save(user);
	}

	public User findByEmail(String email) {
		return this.repository.findByEmail(email);
	}
	
	public User findByUserName(String userName) {
		return this.repository.findByUserName(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.repository.findByUserName(username);
	}

	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			return;
		}

		User user = this.findByUserName(username);
		user.setPassword(passwordEncoder.encode(newPassword));
		this.repository.save(user);
	}

}
