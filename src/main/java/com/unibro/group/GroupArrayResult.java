/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.group;

import java.util.List;

/**
 *
 * @author THOND
 */
public class GroupArrayResult {

    private List<Group> data;
    private Integer size;

    public GroupArrayResult(List<Group> data, Integer size) {
        this.data = data;
        this.size = size;
    }

    /**
     * @return the data
     */
    public List<Group> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<Group> data) {
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
