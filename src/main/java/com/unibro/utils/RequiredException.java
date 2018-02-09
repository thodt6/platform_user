/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.utils;

import org.apache.log4j.Logger;

/**
 *
 * @author THOND
 */
public class RequiredException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(RequiredException.class.getName());

    public RequiredException(String msg) {
        super(msg);
    }

    public static String checkRequired(String[] name, String[] params) {
        String ret = "";
        for (int i = 0; i < name.length; i++) {
            if (params[i] == null || params[i].equals("")) {
                ret += "," + name[i] + " is not valid";
            }
        }
        if (ret.length() > 0) {
            ret = ret.substring(1);
        }
        return ret;
    }
}
