/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.property;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author THOND
 */
public class PropertyMapper implements RowMapper<Property> {

    @Override
    public Property mapRow(ResultSet rs, int rowNum) throws SQLException {
        Property object = new Property();
        object.setProp_id((String) rs.getObject("prop_id"));
        object.setName((String) rs.getObject("name"));
        object.setUri((String) rs.getObject("uri"));
        object.setType((Integer) rs.getObject("type"));
        object.setFunc_id((String) rs.getObject("func_id"));
        object.setUpdate_time((java.util.Date) rs.getObject("update_time"));
        object.setCreated_id((Integer) rs.getObject("created_id"));
        object.setApplication_id((String) rs.getObject("application_id"));

        return object;
    }
}
