package com.ako.data;

/**
 * UserTokenState model is used to issue access tokens
 * @author Prashant
 */
public class UserTokenState {
    private String access_token;
    private Long expires_in;
    private boolean mfaAuth;

    public UserTokenState() {
        this.access_token = null;
        this.expires_in = null;
        this.mfaAuth = false;
    }

	public UserTokenState(String access_token, long expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.mfaAuth = false;
    }
    
    public UserTokenState(String access_token, long expires_in, boolean mfaAuth) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.mfaAuth = mfaAuth;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public boolean isMfaAuth() {
		return mfaAuth;
	}

	public void setMfaAuth(boolean mfaAuth) {
		this.mfaAuth = mfaAuth;
	}
}
