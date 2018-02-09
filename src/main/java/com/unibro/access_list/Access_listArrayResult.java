/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.access_list;

import java.util.List;

/**
 *
 * @author THOND
 */
public class Access_listArrayResult {

    private List<Access_list> data;
    private Integer size;

    public Access_listArrayResult(List<Access_list> data, Integer size) {
        this.data = data;
        this.size = size;
    }

    /**
     * @return the data
     */
    public List<Access_list> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<Access_list> data) {
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
