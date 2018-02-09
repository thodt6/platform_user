/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.property;

import com.unibro.utils.RequestFilter;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author THOND
 */
public interface PropertyDAO {

    public void setDataSource(DataSource ds);

    public Property createObject(Property object);

    public Property getObject(String id);

    public Property updateObject(Property object);

    public Integer deleteObject(Property object);

    public List<Property> getAllObjects();

    public List<Property> loadObjects(String SQL, Object... params);

    public Integer getRowCount(String SQL, Object... params);

    public Property queryForObject(String SQL, Object... params);

    public Integer getTotalRow();

    public Integer deleteObject(String id);

    public Boolean checkObjectExist(String id);

    public List<Property> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters);

    public int filterObjectsSize(List<RequestFilter> filters);
}
