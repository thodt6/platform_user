/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.func;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.unibro.application.Application;
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

/**
 *
 * @author THOND
 */
public class FuncDAOImpl implements FuncDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    Logger logger = LogManager.getLogger(this.getClass().getName());
    private String exceptionMsg = "";
    private HttpStatus exceptionCode = HttpStatus.OK;
    private String exceptionType = "";

    RestHighLevelClient client;
    RestClientBuilder builder;

    public FuncDAOImpl() {
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
    public Func createObject(final Func object) {
        try {
            final String SQL = "INSERT INTO " + Global.getConfigValue("app.dbname") + ".func(func_id,name,description,uri,created_id,update_time,application_id) VALUES (?,?,?,?,?,?,?)";
            int ret = jdbcTemplateObject.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) {
                    try {
                        PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"func_id", "name", "description", "uri", "created_id", "update_time", "application_id"});
                        ps.setObject(1, object.getFunc_id());
                        ps.setObject(2, object.getName());
                        ps.setObject(3, object.getDescription());
                        ps.setObject(4, object.getUri());
                        ps.setObject(5, object.getCreated_id());
                        ps.setObject(6, object.getUpdate_time());
                        ps.setObject(7, object.getApplication_id());

                        return ps;
                    } catch (SQLException ex) {
                        logger.error(ex);
                        exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
                        exceptionMsg = ex.toString();
                        exceptionType = "SQLException";
                        return null;
                    }
                }
            });
            if (ret > 0) {
                this.exceptionCode = HttpStatus.OK;
                this.exceptionMsg = "";
                return object;
            } else {
                this.exceptionCode = HttpStatus.NO_CONTENT;
                this.exceptionMsg = "No object is created";
                return null;
            }
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType = "SQLExDataAccessExceptionception";
            return null;
        }
    }

    @Override
    public Func updateObject(final Func object) {
        try {
            final String SQL = "UPDATE " + Global.getConfigValue("app.dbname") + ".func SET func_id = ?,name = ?,description = ?,uri = ?,created_id = ?,update_time = ?,application_id = ? WHERE func_id = ?";
            int ret = jdbcTemplateObject.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) {
                    try {
                        PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"func_id", "name", "description", "uri", "created_id", "update_time", "application_id", "func_id"});
                        ps.setObject(1, object.getFunc_id());
                        ps.setObject(2, object.getName());
                        ps.setObject(3, object.getDescription());
                        ps.setObject(4, object.getUri());
                        ps.setObject(5, object.getCreated_id());
                        ps.setObject(6, object.getUpdate_time());
                        ps.setObject(7, object.getApplication_id());
                        ps.setObject(8, object.getFunc_id());

                        return ps;
                    } catch (SQLException ex) {
                        logger.error(ex);
                        exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
                        exceptionMsg = ex.toString();
                        exceptionType = "SQLException";
                        return null;
                    }
                }
            });
            if (ret > 0) {
                this.exceptionCode = HttpStatus.OK;
                this.exceptionMsg = "";
                return object;
            } else {
                this.exceptionCode = HttpStatus.NO_CONTENT;
                this.exceptionMsg = "No object is updated";
                return null;
            }
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType = "DataAccessException";
            return null;
        }

    }

    @Override
    public Integer deleteObject(Func object) {
        try {
            String SQL = "DELETE FROM " + Global.getConfigValue("app.dbname") + ".func where func_id = ?";
            int ret = jdbcTemplateObject.update(SQL, object.getFunc_id());
            if (ret > 0) {
                this.exceptionCode = HttpStatus.OK;
                this.setExceptionMsg("");
                return ret;
            } else {
                this.exceptionCode = HttpStatus.NO_CONTENT;
                this.setExceptionMsg("No object is deleted");
                return 0;
            }
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.setExceptionMsg(ex.toString());
            exceptionType = "DataAccessException";
            return 0;
        }
    }

    @Override
    public Integer deleteObject(String id) {
        try {
            String SQL = "DELETE FROM " + Global.getConfigValue("app.dbname") + ".func where func_id = ?";
            int ret = jdbcTemplateObject.update(SQL, id);
            if (ret > 0) {
                this.exceptionCode = HttpStatus.OK;
                this.exceptionMsg = "";
                return ret;
            } else {
                this.exceptionCode = HttpStatus.NO_CONTENT;
                this.exceptionMsg = "No object is deleted";
                return 0;
            }
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType = "DataAccessException";
            return 0;
        }
    }

    @Override
    public List<Func> loadObjects(String SQL, Object... params) {
        try {
            List<Func> objects = jdbcTemplateObject.query(SQL, params, new FuncMapper());
            this.exceptionCode = HttpStatus.OK;
            this.setExceptionMsg("");
            return objects;
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.setExceptionMsg(ex.toString());
            exceptionType = "DataAccessException";
            return null;
        }

    }

    @Override
    public Func queryForObject(String SQL, Object... params) {
        try {
            Func ret = this.jdbcTemplateObject.queryForObject(SQL, params, new FuncMapper());
            if (ret != null) {
                this.exceptionCode = HttpStatus.OK;
                this.setExceptionMsg("");
                return ret;
            } else {
                this.exceptionCode = HttpStatus.NO_CONTENT;
                this.setExceptionMsg("Object is not found");
                return null;
            }
        } catch (EmptyResultDataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.NO_CONTENT;
            this.setExceptionMsg("Object is not found");
            exceptionType = "EmptyResultDataAccessException";
            return null;
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.setExceptionMsg(ex.toString());
            exceptionType = "DataAccessException";
            return null;
        }
    }

    @Override
    public Integer getRowCount(String SQL, Object... params) {
        try {
            this.exceptionCode = HttpStatus.OK;
            this.setExceptionMsg("");
            return jdbcTemplateObject.queryForObject(SQL, params, Integer.class);
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.setExceptionMsg(ex.toString());
            exceptionType = "DataAccessException";
            return 0;
        }
    }

    @Override
    public Func getObject(String func_id) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".func";
                String index_type = "func";
                GetRequest request = new GetRequest(index_name, index_type, func_id);
                GetResponse response = client.get(request);
                if (!response.isSourceEmpty()) {
                    rest.close();
                    Gson gson = Global.getGsonObject("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    return gson.fromJson(response.getSourceAsString(), Func.class);
                } else {
                    rest.close();
                    return null;
                }
            }
        } catch (JsonSyntaxException | IOException ex) {
            logger.error(ex);
            this.setExceptionCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.setExceptionMsg(ex.toString());
            exceptionType = "JsonSyntaxException | IOException";
            return null;
        }
    }

    public Func getObjectByUserField(String fieldname, String value) {
        List filter = new ArrayList();
        RequestFilter rf = new RequestFilter();
        rf.setName(fieldname);
        rf.setRequired(true);
        rf.setType(RequestFilter.EQUAL);
        rf.setValue(value);
        filter.add(rf);
        List<Func> ret = this.filterObjects(0, 1, "func_id", 0, filter);
        if (ret != null && !ret.isEmpty()) {
            return ret.get(0);
        }
        return null;
    }

    @Override
    public List<Func> getAllObjects() {
        return this.filterObjects(0, 0, "func_id", 0, null);
    }

    @Override
    public Integer getTotalRow() {
        return this.filterObjectsSize(new ArrayList());
    }

    @Override
    public Boolean checkObjectExist(String func_id) {
        return !(this.getObject(func_id) == null);
    }

    @Override
    public List<Func> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".func";
                String index_type = "func";
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
                        Application item = gson.fromJson(hit.getSourceAsString(), Application.class);
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
            exceptionType = "JsonSyntaxException | IOException";
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
            exceptionType = "IOException";
            return 0;
        }
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
