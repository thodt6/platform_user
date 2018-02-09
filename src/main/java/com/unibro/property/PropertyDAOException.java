/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.property;

/**
 *
 * @author THOND
 */
public class PropertyDAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int httpCode = 200;

    public PropertyDAOException(String ex, int httpCode) {
        super(ex);
        this.httpCode = httpCode;
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
