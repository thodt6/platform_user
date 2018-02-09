/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.group;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author THOND
 */
public class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        Group object = new Group();
        object.setGroupid((Integer) rs.getObject("groupid"));
        object.setName((String) rs.getObject("name"));
        object.setDescription((String) rs.getObject("description"));
        object.setCreated_time((java.util.Date) rs.getObject("created_time"));
        object.setModified_time((java.util.Date) rs.getObject("modified_time"));
        object.setCreated_id((Integer) rs.getObject("created_id"));
        object.setApplication_id((String) rs.getObject("application_id"));
        object.setUri((String) rs.getObject("uri"));
        return object;
    }
}
