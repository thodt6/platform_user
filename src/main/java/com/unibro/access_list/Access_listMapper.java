/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.access_list;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author THOND
 */
public class Access_listMapper implements RowMapper<Access_list> {

    @Override
    public Access_list mapRow(ResultSet rs, int rowNum) throws SQLException {
        Access_list object = new Access_list();
        object.setAccess_id((Integer) rs.getObject("id"));
        object.setFunc_id((String) rs.getObject("func_id"));
        object.setProp_id((String) rs.getObject("prop_id"));
        object.setPrinciple_type((String) rs.getObject("principle_type"));
        object.setPrinciple_id((Integer) rs.getObject("principle_id"));
        object.setPermission((Integer) rs.getObject("permission"));
        object.setUpdate_time((java.util.Date) rs.getObject("update_time"));
        object.setUpdate_id((Integer) rs.getObject("update_id"));
        object.setApplication_id((String) rs.getObject("application_id"));
        return object;
    }
}
