package com.ako.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ako.core.DeviceProvider;
import com.ako.data.User;
import com.ako.data.UserTokenState;
import com.ako.security.JwtAuthenticationRequest;
import com.ako.security.TokenHelper;

/**
 * A authentication business service
 * 
 * @author Prashant
 *
 */
@Service
public class AuthenticationService {

	@Autowired
	TokenHelper tokenHelper;
	@Autowired
	private DeviceProvider deviceProvider;
	@Autowired
	private UserService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Check user credentials and create a token
	 * 
	 * @param id
	 * @return Auth token
	 */
	public UserTokenState login(JwtAuthenticationRequest authenticationRequest, Device device) {
		// Perform the security
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		// Inject into security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// token creation
		User user = (User) authentication.getPrincipal();
		String jws = tokenHelper.generateToken(user, device);
		int expiresIn = tokenHelper.getExpiredIn(device);
		// Return the token

		return new UserTokenState(jws, expiresIn);
	}

	/**
	 * Refresh auth token
	 * 
	 * @return Auth token
	 */
	public UserTokenState rereshToken(String authToken, Device device, Principal principal) {
		// TODO check user password last update
		String refreshedToken = tokenHelper.refreshToken(authToken, device);
		int expiresIn = tokenHelper.getExpiredIn(device);
		return new UserTokenState(refreshedToken, expiresIn);
	}

	/**
	 * Change user password
	 * 
	 * @return
	 */
	public void changePassword(String oldPassword, String newPassword) {
		this.userDetailsService.changePassword(oldPassword, newPassword);
	}
}
