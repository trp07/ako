package com.ako.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import com.ako.config.AppConfig;
import com.ako.core.DeviceProvider;
import com.ako.data.UserTokenState;
import com.ako.security.JwtAuthenticationRequest;
import com.ako.security.TokenHelper;
import com.ako.service.AuthenticationService;
import com.ako.service.MFAAuthenticationService;
import com.ako.service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;

/**
 * @author Prashant
 */

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenHelper tokenHelper;

	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private MFAAuthenticationService mfaService;

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

	@RequestMapping(value = "/get-qr-code")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getQRCode(Principal user) throws UnsupportedEncodingException {
		String url = this.generateQRUrl(user);
		Map<String, String> result = new HashMap<>();
		result.put("result", url);
		return ResponseEntity.accepted().body(result);
	}

	@RequestMapping(value = "/verify-code", method = RequestMethod.POST)
	@PreAuthorize("hasRole('PRE_AUTH_USER')")
	public ResponseEntity<?> verifyCode(@RequestBody AuthCode code, Principal user, Device device) {
		UserTokenState token = mfaService.verify(user.getName(), code.code, device);

		// Return the token
		return ResponseEntity.ok(token);
	}

	private String generateQRUrl(Principal user) throws UnsupportedEncodingException {
		String secret = mfaService.getUserSecret(user.getName());
		return AppConfig.QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", AppConfig.APP_NAME,
				user.getName(), secret, AppConfig.APP_NAME), "UTF-8");
	}

	private static class AuthCode {
		public int code;
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
}