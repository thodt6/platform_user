/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.func;

import com.unibro.utils.RequestFilter;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author THOND
 */
public interface FuncDAO {

    public void setDataSource(DataSource ds);

    public Func createObject(Func object);

    public Func getObject(String id);

    public Func updateObject(Func object);

    public Integer deleteObject(Func object);

    public List<Func> getAllObjects();

    public List<Func> loadObjects(String SQL, Object... params);

    public Integer getRowCount(String SQL, Object... params);

    public Func queryForObject(String SQL, Object... params);

    public Integer getTotalRow();

    public Integer deleteObject(String id);

    public Boolean checkObjectExist(String id);

    public List<Func> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters);

    public int filterObjectsSize(List<RequestFilter> filters);
}
