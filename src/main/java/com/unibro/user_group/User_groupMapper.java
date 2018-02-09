/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.user_group;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author THOND
 */
public class User_groupMapper implements RowMapper<User_group> {

    @Override
    public User_group mapRow(ResultSet rs, int rowNum) throws SQLException {
        User_group object = new User_group();
        object.setUserid((Integer) rs.getObject("userid"));
        object.setGroupid_list((String) rs.getObject("groupid_list"));
        object.setUpdate_time((java.util.Date) rs.getObject("update_time"));
        object.setCreated_id((Integer) rs.getObject("created_id"));
        object.setDefault_groupid((String) rs.getObject("default_groupid"));
        return object;
    }
}
