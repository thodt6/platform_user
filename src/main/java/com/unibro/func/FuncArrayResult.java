/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.func;

import java.util.List;

/**
 *
 * @author THOND
 */
public class FuncArrayResult {

    private List<Func> data;
    private Integer size;

    public FuncArrayResult(List<Func> data, Integer size) {
        this.data = data;
        this.size = size;
    }

    /**
     * @return the data
     */
    public List<Func> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<Func> data) {
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
