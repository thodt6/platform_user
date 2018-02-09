/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.func;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author THOND
 */
public class FuncMapper implements RowMapper<Func> {

    @Override
    public Func mapRow(ResultSet rs, int rowNum) throws SQLException {
        Func object = new Func();
        object.setFunc_id((String) rs.getObject("func_id"));
        object.setName((String) rs.getObject("name"));
        object.setDescription((String) rs.getObject("description"));
        object.setUri((String) rs.getObject("uri"));
        object.setCreated_id((Integer) rs.getObject("created_id"));
        object.setUpdate_time((java.util.Date) rs.getObject("update_time"));
        object.setApplication_id((String) rs.getObject("application_id"));

        return object;
    }
}
