/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.utils;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

/**
 *
 * @author THOND
 */
public class AuthException extends RuntimeException {

    @Autowired
    private static Environment env;

    private static final long serialVersionUID = 1L;
    private int httpCode = HttpStatus.OK.value();

    public AuthException(String authCode, int httpCode) {
        super("Your authCode = " + authCode + " is not valid");
        this.httpCode = httpCode;
    }

    public static boolean checkAuth(String auth) {
        return auth.equals(Global.getConfigValue("app.internalkey"));
    }

    public static boolean checkValidEndPoint(HttpServletRequest request) {
        String valid_ip = Global.getConfigValue("app.accessIps");

        if (valid_ip.equals("any")) {
            return true;
        }
        return valid_ip.contains(request.getRemoteAddr());
    }

    /**
     * @return the httpCode
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * @param httpCode the httpCode to set
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
}
