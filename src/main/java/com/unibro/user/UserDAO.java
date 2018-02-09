/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.user;

import com.unibro.utils.RequestFilter;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author THOND
 */
public interface UserDAO {

    public void setDataSource(DataSource ds);

    public User createObject(User object);

    public User getObject(Integer id);

    public User updateObject(User object);

    public Integer deleteObject(User object);

    public List<User> getAllObjects();

    public List<User> loadObjects(String SQL, Object... params);

    public Integer getRowCount(String SQL, Object... params);

    public User queryForObject(String SQL, Object... params);

    public Integer getTotalRow();

    public Integer deleteObject(Integer id);

    public Boolean checkObjectExist(Integer id);

    public List<User> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters);

    public int filterObjectsSize(List<RequestFilter> filters);
}
