package com.ako.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ako.data.User;
import com.ako.service.UserService;

/**
 * Filters requests and check auth token validity
 * 
 * @author Prashant
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	private TokenHelper tokenHelper;
	private UserService userService;

	public TokenAuthenticationFilter(TokenHelper tokenHelper, UserService userDetailsService) {
		this.tokenHelper = tokenHelper;
		this.userService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;
		String authToken = tokenHelper.getToken(request);

		if (authToken != null) {
			// get username from token
			username = tokenHelper.getUsernameFromToken(authToken);
			if (username != null) {
				// get user
				User userDetails = userService.findByUserName(username);
				
				if (tokenHelper.validateToken(authToken, userDetails)) {
					//create authority for a second step if needed
					//TODO: move this to TOTP filter
					 
			        if(userDetails.hasMfaActive()) {
			        	tokenHelper.checkAuthority(authToken, userDetails);
			        }
					// create authentication
					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(authToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		chain.doFilter(request, response);
	}

}