package com.ako.api;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ako.core.DeviceProvider;
import com.ako.data.UserTokenState;
import com.ako.security.JwtAuthenticationRequest;
import com.ako.security.TokenHelper;
import com.ako.service.AuthenticationService;

/**
 * @author Prashant
 */

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	TokenHelper tokenHelper;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private DeviceProvider deviceProvider;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response, Device device) throws AuthenticationException, IOException {

		UserTokenState token = authenticationService.login(authenticationRequest, device);

		// Return the token
		return ResponseEntity.ok(token);
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response,
			Principal principal) {
		ResponseEntity<?> httpResponse = null;
		String authToken = tokenHelper.getToken(request);
		Device device = deviceProvider.getCurrentDevice(request);
		if (authToken != null && principal != null) {
			UserTokenState userTokenState = authenticationService.rereshToken(authToken, device, principal);
			httpResponse = ResponseEntity.ok(userTokenState);
		} else {
			UserTokenState userTokenState = new UserTokenState();
			httpResponse = ResponseEntity.accepted().body(userTokenState);
		}
		return httpResponse;
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		authenticationService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
}