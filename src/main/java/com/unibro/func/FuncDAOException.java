/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.func;

/**
 *
 * @author THOND
 */
public class FuncDAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int httpCode = 200;

    public FuncDAOException(String ex, int httpCode) {
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
