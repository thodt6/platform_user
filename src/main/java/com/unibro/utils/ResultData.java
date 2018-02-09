/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.utils;

import java.util.Date;

/**
 *
 * @author THOND
 */
public class ResultData {

    public Date timestamp =new java.util.Date();
    public int status=200;
    public String error="";
    public String exception="";
    public String message="Success";
    public String path="";
    public Object result;
}
