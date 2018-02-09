/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.user;

import java.util.List;

/**
 *
 * @author THOND
 */
public class UserArrayResult {

    private List<User> data;
    private Integer size;

    public UserArrayResult(List<User> data, Integer size) {
        this.data = data;
        this.size = size;
    }

    /**
     * @return the data
     */
    public List<User> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<User> data) {
        this.data = data;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }
}
