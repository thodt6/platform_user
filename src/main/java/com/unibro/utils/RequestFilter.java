/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author THOND
 */
public class RequestFilter {

    public static final String EQUAL = "EQUAL";
    public static final String CONTAIN = "CONTAIN";
    public static final String GREATER = "GREATER";
    public static final String LESS = "LESS";
    public static final String IN = "IN";
    public static final String REGEX = "REGEX";

    private String name;
    private String type = EQUAL;
    private Object value;
    private Boolean required = true;
    private String function = "";

    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * @return the required
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * @param required the required to set
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    public static List<RequestFilter> fromHashMap(Map<String, Object> map) {
        List ret = new ArrayList();
        for (Map.Entry pairs : map.entrySet()) {
            String key = pairs.getKey().toString();
            Object value = pairs.getValue();
            RequestFilter filter = new RequestFilter();
            filter.name = key;
            filter.value = value;
            filter.type = RequestFilter.CONTAIN;
            ret.add(filter);
        }
        return ret;
    }
}
