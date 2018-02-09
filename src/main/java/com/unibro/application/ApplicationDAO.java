/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.application;

import com.unibro.utils.RequestFilter;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author THOND
 */
public interface ApplicationDAO {

    public void setDataSource(DataSource ds);

    public Application createObject(Application object);

    public Application getObject(String id);

    public Application updateObject(Application object);

    public Integer deleteObject(Application object);

    public List<Application> getAllObjects();

    public List<Application> loadObjects(String SQL, Object... params);

    public Integer getRowCount(String SQL, Object... params);

    public Application queryForObject(String SQL, Object... params);

    public Integer getTotalRow();

    public Integer deleteObject(String id);

    public Boolean checkObjectExist(String id);

    public List<Application> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters);

    public int filterObjectsSize(List<RequestFilter> filters);
}
