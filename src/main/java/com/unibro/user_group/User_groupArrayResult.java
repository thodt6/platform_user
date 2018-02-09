/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.user_group;

import java.util.List;

/**
 *
 * @author THOND
 */
public class User_groupArrayResult {

    private List<User_group> data;
    private Integer size;

    public User_groupArrayResult(List<User_group> data, Integer size) {
        this.data = data;
        this.size = size;
    }

    /**
     * @return the data
     */
    public List<User_group> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<User_group> data) {
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
