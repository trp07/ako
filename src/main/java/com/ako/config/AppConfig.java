package com.ako.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    public static String APP_NAME;
    public static String QR_PREFIX;
    public static String SECRET;
    public static int EXPIRES_IN;
    public static int MOBILE_EXPIRES_IN;
    public static String AUTH_HEADER;

    @Value("${app.name}")
	public void setAPP_NAME(String aPP_NAME) {
		APP_NAME = aPP_NAME;
	}

	@Value("${app.qr_prefix}")
	public void setQR_PREFIX(String qR_PREFIX) {
		QR_PREFIX = qR_PREFIX;
	}

    @Value("${jwt.secret}")
	public void setSECRET(String sECRET) {
		SECRET = sECRET;
	}

    @Value("${jwt.expires_in}")
	public void setEXPIRES_IN(int eXPIRES_IN) {
		EXPIRES_IN = eXPIRES_IN;
	}

    @Value("${jwt.mobile_expires_in}")
	public void setMOBILE_EXPIRES_IN(int mOBILE_EXPIRES_IN) {
		MOBILE_EXPIRES_IN = mOBILE_EXPIRES_IN;
	}

    @Value("${jwt.header}")
	public void setAUTH_HEADER(String aUTH_HEADER) {
		AUTH_HEADER = aUTH_HEADER;
	}
}
