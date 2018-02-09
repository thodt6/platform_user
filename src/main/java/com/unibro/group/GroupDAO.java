/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.group;

import com.unibro.utils.RequestFilter;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author THOND
 */
public interface GroupDAO {

    public void setDataSource(DataSource ds);

    public Group createObject(Group object);

    public Group getObject(Integer id);

    public Group updateObject(Group object);

    public Integer deleteObject(Group object);

    public List<Group> getAllObjects();

    public List<Group> loadObjects(String SQL, Object... params);

    public Integer getRowCount(String SQL, Object... params);

    public Group queryForObject(String SQL, Object... params);

    public Integer getTotalRow();

    public Integer deleteObject(Integer id);

    public Boolean checkObjectExist(Integer id);

    public List<Group> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters);

    public int filterObjectsSize(List<RequestFilter> filters);
}
