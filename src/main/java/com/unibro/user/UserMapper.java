/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author THOND
 */
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User object = new User();
        object.setUserid((Integer) rs.getObject("userid"));
        object.setOauth_provider((String) rs.getObject("oauth_provider"));
        object.setUid((String) rs.getObject("uid"));
        object.setUsername((String) rs.getObject("username"));
        object.setPassword_hash((String) rs.getObject("password_hash"));
        object.setEmail((String) rs.getObject("email"));
        object.setPhone((String) rs.getObject("phone"));
        object.setFirst_name((String) rs.getObject("first_name"));
        object.setLast_name((String) rs.getObject("last_name"));
        object.setGender((String) rs.getObject("gender"));
        object.setPicture((String) rs.getObject("picture"));
        object.setCountry_code((String) rs.getObject("country_code"));
        object.setState((String) rs.getObject("state"));
        object.setCity((String) rs.getObject("city"));
        object.setPost_code((String) rs.getObject("post_code"));
        object.setCreated_time((java.util.Date) rs.getObject("created_time"));
        object.setModified_time((java.util.Date) rs.getObject("modified_time"));
        object.setLast_login((java.util.Date) rs.getObject("last_login"));
        object.setLast_logout((java.util.Date) rs.getObject("last_logout"));
        object.setStatus((Integer) rs.getObject("status"));
        object.setLogin_token((String) rs.getObject("login_token"));
        object.setExpired_time((Long) rs.getObject("expired_time"));
        object.setPassword_reset_token((String) rs.getObject("password_reset_token"));
        object.setApplication_id((String) rs.getObject("application_id"));

        return object;
    }
}
