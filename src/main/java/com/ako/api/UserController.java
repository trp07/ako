package com.ako.api;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping( value = "/users", produces = MediaType.APPLICATION_JSON_VALUE )
public class UserController {
	
	@Autowired
	UserService userService;

	@RequestMapping("/")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping(method=RequestMethod.POST, path= "/")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	@RequestMapping("/{id}")
	public User getUser(@PathVariable int id) {
		return userService.getUser(id);
	}

	@RequestMapping(method=RequestMethod.PUT, path= "/{id}")
	public User updateUser(@RequestBody User user, @PathVariable int id) {
		return userService.updateUser(user);
	}
	
	@RequestMapping("/whoami")
    public User user(Principal user) {
        return this.userService.findByEmail(user.getName());
    }
}