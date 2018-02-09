/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.access_list;

import com.unibro.utils.RequestFilter;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author THOND
 */
public interface Access_listDAO {

    public void setDataSource(DataSource ds);

    public Access_list createObject(Access_list object);

    public Access_list getObject(Integer id);

    public Access_list updateObject(Access_list object);

    public Integer deleteObject(Access_list object);

    public List<Access_list> getAllObjects();

    public List<Access_list> loadObjects(String SQL, Object... params);

    public Integer getRowCount(String SQL, Object... params);

    public Access_list queryForObject(String SQL, Object... params);

    public Integer getTotalRow();

    public Integer deleteObject(Integer id);

    public Boolean checkObjectExist(Integer id);

    public List<Access_list> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters);

    public int filterObjectsSize(List<RequestFilter> filters);
}
