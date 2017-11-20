package com.ako.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ako.data.User;
import com.ako.data.UserTokenState;
import com.ako.security.TokenBasedAuthentication;
import com.ako.security.TokenHelper;
import com.warrenstrange.googleauth.GoogleAuthenticator;

@Service
public class MFAAuthenticationService {
	final GoogleAuthenticator gAuth = new GoogleAuthenticator();

	@Autowired
	UserService userService;
	

	@Autowired
	TokenHelper tokenHelper;

	/**
	 * returns true if the given otp code is valid
	 * @param device 
	 */
	public UserTokenState verify(String userName, int verificationCode, Device device) {
		boolean isCodeValid = this.checkCode(userName, verificationCode);
		
		return grantAuthority(device, isCodeValid);
	}

	public boolean checkCode(String userName, int verificationCode) {
		String userSecret = this.getUserSecret(userName);
		boolean isCodeValid = gAuth.authorize(userSecret, verificationCode);
		return isCodeValid;
	}

	public UserTokenState grantAuthority(Device device, boolean isCodeValid) {
		// Fetch from security context
		TokenBasedAuthentication authentication = (TokenBasedAuthentication) SecurityContextHolder.getContext()
				.getAuthentication();

		// token creation
		User user = (User) authentication.getPrincipal();
		
		//create new mfauth token
		String jws = tokenHelper.generateToken(user, device, isCodeValid);
		int expiresIn = tokenHelper.getExpiredIn(device);
		
		// Return the token
		return new UserTokenState(jws, expiresIn, isCodeValid);
	}

	public String getUserSecret(String userName) {
		return userService.findByUserName(userName).getSecret();
	}
}