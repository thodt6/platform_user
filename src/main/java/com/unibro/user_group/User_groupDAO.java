/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.user_group;

import com.unibro.utils.RequestFilter;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author THOND
 */
public interface User_groupDAO {

    public void setDataSource(DataSource ds);

    public User_group createObject(User_group object);

    public User_group getObject(Integer id);

    public User_group updateObject(User_group object);

    public Integer deleteObject(User_group object);

    public List<User_group> getAllObjects();

    public List<User_group> loadObjects(String SQL, Object... params);

    public Integer getRowCount(String SQL, Object... params);

    public User_group queryForObject(String SQL, Object... params);

    public Integer getTotalRow();

    public Integer deleteObject(Integer id);

    public Boolean checkObjectExist(Integer id);

    public List<User_group> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters);

    public int filterObjectsSize(List<RequestFilter> filters);
}
