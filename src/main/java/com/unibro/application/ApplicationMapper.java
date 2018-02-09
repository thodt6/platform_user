/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.application;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author THOND
 */
public class ApplicationMapper implements RowMapper<Application> {

    @Override
    public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
        Application object = new Application();
        object.setAppid((String) rs.getObject("id"));
        object.setName((String) rs.getObject("name"));
        object.setDescription((String) rs.getObject("description"));
        object.setUpdate_id((Integer) rs.getObject("update_id"));
        object.setUpdate_time((java.util.Date) rs.getObject("update_time"));

        return object;
    }
}
