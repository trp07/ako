package com.ako.security;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ako.config.AppConfig;
import com.ako.core.TimeProvider;
import com.ako.data.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * A helper class to handle JWT token common functions
 * @author Prashant
 */

@Component
public class TokenHelper {

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    @Autowired
    TimeProvider timeProvider;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public String refreshToken(String token, Device device) {
        String refreshedToken;
        Date a = timeProvider.now();
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(a);
            refreshedToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate(device))
                .signWith( SIGNATURE_ALGORITHM, AppConfig.SECRET )
                .compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public String generateToken(User user, Device device) {
        String audience = generateAudience(device);
        
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("mfaAuth", false);
        
        return Jwts.builder()
        		.setClaims(claims)
                .setIssuer( AppConfig.APP_NAME )
                .setAudience(audience)
                .setIssuedAt(timeProvider.now())
                .setExpiration(generateExpirationDate(device))
                .signWith( SIGNATURE_ALGORITHM, AppConfig.SECRET )
                .compact();
    }
    
    public String generateToken(User user, Device device, boolean mfaAuth) {
        String audience = generateAudience(device);
        
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("mfaAuth", mfaAuth);
        
        return Jwts.builder()
        		.setClaims(claims)
                .setIssuer( AppConfig.APP_NAME )
                .setAudience(audience)
                .setIssuedAt(timeProvider.now())
                .setExpiration(generateExpirationDate(device))
                .signWith( SIGNATURE_ALGORITHM, AppConfig.SECRET )
                .compact();
    }
    
    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(AppConfig.SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(Device device) {
        long expiresIn = device.isTablet() || device.isMobile() ? AppConfig.MOBILE_EXPIRES_IN : AppConfig.EXPIRES_IN;
        return new Date(timeProvider.now().getTime() + expiresIn * 1000);
    }

    public int getExpiredIn(Device device) {
        return device.isMobile() || device.isTablet() ? AppConfig.MOBILE_EXPIRES_IN : AppConfig.EXPIRES_IN;
    }

    public Boolean validateToken(String token, User user) {
    	
    	String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        
        boolean isValidUsername = username != null && username.equals(user.getUsername());
        
        return isValidUsername;
    }
    public void checkAuthority(String token, User user) {
    	boolean mfaAuth;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            mfaAuth = claims.get("mfaAuth", Boolean.class);
        } catch (Exception e) {
            mfaAuth = false;
        }
        
        boolean isValidMfaAuth = user.hasMfaActive() && mfaAuth;
        if(!isValidMfaAuth) {
        	user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_PRE_AUTH_USER")));
        }
    }
    

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String getToken( HttpServletRequest request ) {
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        String authHeader = getAuthHeaderFromHeader( request );
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader( HttpServletRequest request ) {
        return request.getHeader(AppConfig.AUTH_HEADER);
    }

}