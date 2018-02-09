/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.user;

/**
 *
 * @author THOND
 */
public class BasicUser {
    private String username;
    private String password_hash;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password_hash
     */
    public String getPassword_hash() {
        return password_hash;
    }

    /**
     * @param password_hash the password_hash to set
     */
    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
}
