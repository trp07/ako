package com.ako.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ako.data.User;
import com.ako.service.UserService;

/**
 * User action controller responsible for serving user API
 * @author Prashant
 *
 */
@RestController
public class UserController {
	
	@Autowired
	UserService userService;

	@RequestMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping(method=RequestMethod.POST, path= "/users")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	@RequestMapping("/users/{id}")
	public User getUser(@PathVariable int id) {
		return userService.getUser(id);
	}

	@RequestMapping(method=RequestMethod.PUT, path= "/users/{id}")
	public User updateUser(@RequestBody User user, @PathVariable int id) {
		return userService.updateUser(user);
	}
}