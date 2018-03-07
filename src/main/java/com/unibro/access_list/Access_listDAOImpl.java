/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.access_list;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.unibro.utils.ElasticUtils;
import com.unibro.utils.Global;
import com.unibro.utils.RequestFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

/**
 *
 * @author THOND
 */
public class Access_listDAOImpl implements Access_listDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    Logger logger = LogManager.getLogger(this.getClass().getName());
    private String exceptionMsg = "";
    private HttpStatus exceptionCode = HttpStatus.OK;
    private String exceptionType = "";

    RestHighLevelClient client;
    RestClientBuilder builder;

    public Access_listDAOImpl() {
        JsonParser parser = new JsonParser();
        JsonArray host_list = (JsonArray) parser.parse(Global.getConfigValue("app.elastic.hosts"));
        logger.info(host_list.size());
        HttpHost[] list = new HttpHost[host_list.size()];
        for (int i = 0; i < list.length; i++) {
            JsonObject obj = host_list.get(i).getAsJsonObject();
            HttpHost host = new HttpHost(obj.get("host").getAsString(), obj.get("port").getAsInt(), obj.get("protocol").getAsString());
            list[i] = host;
        }
//        builder = RestClient.builder(new HttpHost(Global.getConfigValue("app.elastic.host"), Integer.valueOf(Global.getConfigValue("app.elastic.port")), Global.getConfigValue("app.elastic.protocol")));
        builder = RestClient.builder(list);
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
    }

    @Override
    public Access_list createObject(final Access_list object) {
        try {
            final String SQL = "INSERT INTO " + Global.getConfigValue("app.dbname") + ".access_list(func_id,prop_id,principle_type,principle_id,permission,update_time,update_id,application_id) VALUES (?,?,?,?,?,?,?,?)";
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            int ret = jdbcTemplateObject.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) {
                    try {
                        PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"func_id", "prop_id", "principle_type", "principle_id", "permission", "update_time", "update_id", "application_id"});
                        ps.setObject(1, object.getFunc_id());
                        ps.setObject(2, object.getProp_id());
                        ps.setObject(3, object.getPrinciple_type());
                        ps.setObject(4, object.getPrinciple_id());
                        ps.setObject(5, object.getPermission());
                        ps.setObject(6, object.getUpdate_time());
                        ps.setObject(7, object.getUpdate_id());
                        ps.setObject(8, object.getApplication_id());
                        return ps;
                    } catch (SQLException ex) {
                        logger.error(ex);
                        setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        setExceptionMsg(ex.toString());
                        exceptionType="SQLException";
                        return null;
                    }
                }
            }, holder);
            if (ret > 0) {
                this.setExceptionCode(HttpStatus.OK);
                this.setExceptionMsg("");
                int primaryKey = holder.getKey().intValue();
                object.setAccess_id(primaryKey);
                return object;
            } else {
                this.setExceptionCode(HttpStatus.NO_CONTENT);
                this.setExceptionMsg("No object is created");
                return null;
            }
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType="DataAccessException";
            return null;
        }
    }

    @Override
    public Access_list updateObject(final Access_list object) {
        try {
            final String SQL = "UPDATE " + Global.getConfigValue("app.dbname") + ".access_list SET func_id = ?,prop_id = ?,principle_type = ?,principle_id = ?,permission = ?,update_time = ?,update_id = ?, application_id=? WHERE access_id = ?";
            int ret = jdbcTemplateObject.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) {
                    try {
                        PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"func_id", "prop_id", "principle_type", "principle_id", "permission", "update_time", "update_id", "application_id", "access_id"});
                        ps.setObject(1, object.getFunc_id());
                        ps.setObject(2, object.getProp_id());
                        ps.setObject(3, object.getPrinciple_type());
                        ps.setObject(4, object.getPrinciple_id());
                        ps.setObject(5, object.getPermission());
                        ps.setObject(6, object.getUpdate_time());
                        ps.setObject(7, object.getUpdate_id());
                        ps.setObject(8, object.getApplication_id());
                        ps.setObject(9, object.getAccess_id());

                        return ps;
                    } catch (SQLException ex) {
                        logger.error(ex);
                        setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        setExceptionMsg(ex.toString());
                        exceptionType="SQLException";
                        return null;
                    }
                }
            });
            if (ret > 0) {
                this.setExceptionCode(HttpStatus.OK);
                this.setExceptionMsg("");
                return object;
            } else {
                this.setExceptionCode(HttpStatus.NO_CONTENT);
                this.setExceptionMsg("No object is updated");
                return null;
            }
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType="DataAccessException";
            return null;
        }

    }

    @Override
    public Integer deleteObject(Access_list object) {
        try {
            String SQL = "DELETE FROM " + Global.getConfigValue("app.dbname") + ".access_list where access_id = ?";
            int ret = jdbcTemplateObject.update(SQL, object.getAccess_id());
            if (ret > 0) {
                this.setExceptionCode(HttpStatus.OK);
                this.setExceptionMsg("");
                return ret;
            } else {
                this.setExceptionCode(HttpStatus.NO_CONTENT);
                this.setExceptionMsg("No object is deleted");
                return 0;
            }
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType="DataAccessException";
            return 0;
        }
    }

    @Override
    public Integer deleteObject(Integer id) {
        try {
            String SQL = "DELETE FROM " + Global.getConfigValue("app.dbname") + ".access_list where access_id = ?";
            int ret = jdbcTemplateObject.update(SQL, id);
            if (ret > 0) {
                this.setExceptionCode(HttpStatus.OK);
                this.setExceptionMsg("");
                return ret;
            } else {
                this.setExceptionCode(HttpStatus.NO_CONTENT);
                this.setExceptionMsg("No object is deleted");
                return 0;
            }
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType="DataAccessException";
            return 0;
        }
    }

    @Override
    public List<Access_list> loadObjects(String SQL, Object... params) {
        try {
            List<Access_list> objects = jdbcTemplateObject.query(SQL, params, new Access_listMapper());
            this.setExceptionCode(HttpStatus.OK);
            this.setExceptionMsg("");
            return objects;
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType="DataAccessException";
            return null;
        }

    }

    @Override
    public Access_list queryForObject(String SQL, Object... params) {
        try {
            Access_list ret = this.jdbcTemplateObject.queryForObject(SQL, params, new Access_listMapper());
            if (ret != null) {
                this.setExceptionCode(HttpStatus.OK);
                this.setExceptionMsg("");
                return ret;
            } else {
                this.setExceptionCode(HttpStatus.NO_CONTENT);
                this.setExceptionMsg("Object is not found");
                return null;
            }
        } catch (EmptyResultDataAccessException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.NO_CONTENT);
            this.setExceptionMsg("Object is not found");
            exceptionType="EmptyResultDataAccessException";
            return null;
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType="DataAccessException";
            return null;
        }
    }

    @Override
    public Integer getRowCount(String SQL, Object... params) {
        try {
            this.setExceptionCode(HttpStatus.OK);
            this.setExceptionMsg("");
            return jdbcTemplateObject.queryForObject(SQL, params, Integer.class);
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType="DataAccessException";
            return 0;
        }
    }

    @Override
    public Access_list getObject(Integer access_id) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".access_list";
                String index_type = "access_list";
                GetRequest request = new GetRequest(index_name, index_type, String.valueOf(access_id));
                GetResponse response = client.get(request);
                if (!response.isSourceEmpty()) {
                    rest.close();
                    Gson gson = Global.getGsonObject("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    return gson.fromJson(response.getSourceAsString(), Access_list.class);
                } else {
                    rest.close();
                    return null;
                }
            }
        } catch (JsonSyntaxException | IOException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType="JsonSyntaxException | IOException";
            return null;
        }
    }

    public Access_list getObjectByUserField(String fieldname, String value) {
        List filter = new ArrayList();
        RequestFilter rf = new RequestFilter();
        rf.setName(fieldname);
        rf.setRequired(true);
        rf.setType(RequestFilter.EQUAL);
        rf.setValue(value);
        filter.add(rf);
        List<Access_list> ret = this.filterObjects(0, 1, "access_id", 0, filter);
        if (ret != null && !ret.isEmpty()) {
            return ret.get(0);
        }
        return null;
    }

    @Override
    public List<Access_list> getAllObjects() {
        return this.filterObjects(0, 0, "access_id", 0, null);
    }

    @Override
    public Integer getTotalRow() {
        return this.filterObjectsSize(new ArrayList());
    }

    @Override
    public Boolean checkObjectExist(Integer access_id) {
        return !(this.getObject(access_id) == null);
    }

    @Override
    public List<Access_list> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".access_list";
                String index_type = "access_list";
                SearchRequest searchRequest = new SearchRequest(index_name);
                searchRequest.types(index_type);
                SearchSourceBuilder searchBuilder = ElasticUtils.getSourceBuilder(first, pageSize, sortField, sortOrder, filters);
                searchRequest.source(searchBuilder);
                logger.info(searchBuilder.toString());
                SearchResponse response = client.search(searchRequest);
                SearchHits hits = response.getHits();
                Gson gson = Global.getGsonObject("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                List ret = new ArrayList();
                if (hits.getTotalHits() > 0) {
                    for (SearchHit hit : hits.getHits()) {
                        Access_list item = gson.fromJson(hit.getSourceAsString(), Access_list.class);
                        ret.add(item);
                    }
                    this.setExceptionCode(HttpStatus.OK);
                    this.setExceptionMsg("");
                    return ret;
                } else {
                    this.setExceptionCode(HttpStatus.OK);
                    this.setExceptionMsg("Not found");
                }
                rest.close();
                return new ArrayList();
            }
        } catch (JsonSyntaxException | IOException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType="JsonSyntaxException | IOException";
            return new ArrayList();
        }
    }

    @Override
    public int filterObjectsSize(List<RequestFilter> filters) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".access_list";
                String index_type = "access_list";
                SearchRequest searchRequest = new SearchRequest(index_name);
                searchRequest.types(index_type);
                SearchSourceBuilder searchBuilder = ElasticUtils.getSourceBuilder(filters);
                searchRequest.source(searchBuilder);
                logger.info(searchBuilder.toString());
                SearchResponse response = client.search(searchRequest);
                SearchHits hits = response.getHits();
                rest.close();
                if (hits.getTotalHits() > 0) {
                    this.setExceptionCode(HttpStatus.OK);
                    this.setExceptionMsg("");
                } else {
                    this.setExceptionCode(HttpStatus.OK);
                    this.setExceptionMsg("Not found");
                }
                return Long.valueOf(hits.getTotalHits()).intValue();
            }
        } catch (IOException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType="IOException";
            return 0;
        }
    }

    /**
     * @return the exceptionMsg
     */
    public String getExceptionMsg() {
        return exceptionMsg;
    }

    /**
     * @param exceptionMsg the exceptionMsg to set
     */
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    /**
     * @return the exceptionCode
     */
    public HttpStatus getExceptionCode() {
        return exceptionCode;
    }

    /**
     * @param exceptionCode the exceptionCode to set
     */
    public void setExceptionCode(HttpStatus exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
    
    /**
     * @return the exceptionType
     */
    public String getExceptionType() {
        return exceptionType;
    }

    /**
     * @param exceptionType the exceptionType to set
     */
    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

}
