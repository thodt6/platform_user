/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.group;

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
import org.apache.log4j.Logger;
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
public class GroupDAOImpl implements GroupDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    Logger logger = Logger.getLogger(this.getClass().getName());
    private String exceptionMsg = "";
    private HttpStatus exceptionCode = HttpStatus.OK;
    private String exceptionType = "";

    RestHighLevelClient client;
    RestClientBuilder builder;

    public GroupDAOImpl() {
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
    public Group createObject(final Group object) {
        try {
            final String SQL = "INSERT INTO " + Global.getConfigValue("app.dbname") + ".group(name,description,created_time,modified_time,created_id,application_id,uri) VALUES (?,?,?,?,?,?,?)";
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            int ret = jdbcTemplateObject.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) {
                    try {
                        PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"name", "description", "created_time", "modified_time", "created_id", "application_id","uri"});
                        ps.setObject(1, object.getName());
                        ps.setObject(2, object.getDescription());
                        ps.setObject(3, object.getCreated_time());
                        ps.setObject(4, object.getModified_time());
                        ps.setObject(5, object.getCreated_id());
                        ps.setObject(6, object.getApplication_id());
                        ps.setObject(7, object.getUri());
                        return ps;
                    } catch (SQLException ex) {
                        logger.error(ex);
                        exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
                        exceptionMsg = ex.toString();
                        exceptionType = "SQLException";
                        return null;
                    }
                }
            }, holder);
            if (ret > 0) {
                this.exceptionCode = HttpStatus.OK;
                this.exceptionMsg = "";
                int primaryKey = holder.getKey().intValue();
                object.setGroupid(primaryKey);
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
            exceptionType = "DataAccessException";
            return null;
        }
    }

    @Override
    public Group updateObject(final Group object) {
        try {
            final String SQL = "UPDATE " + Global.getConfigValue("app.dbname") + ".group SET name = ?,description = ?,created_time = ?,modified_time = ?,created_id = ?,application_id = ?, uri = ? WHERE groupid = ?";
            int ret = jdbcTemplateObject.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) {
                    try {
                        PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"name", "description", "created_time", "modified_time", "created_id", "application_id", "groupid","uri"});
                        ps.setObject(1, object.getName());
                        ps.setObject(2, object.getDescription());
                        ps.setObject(3, object.getCreated_time());
                        ps.setObject(4, object.getModified_time());
                        ps.setObject(5, object.getCreated_id());
                        ps.setObject(6, object.getApplication_id());
                        ps.setObject(7, object.getUri());
                        ps.setObject(8, object.getGroupid());

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
    public Integer deleteObject(Group object) {
        try {
            String SQL = "DELETE FROM " + Global.getConfigValue("app.dbname") + ".group where groupid = ?";
            int ret = jdbcTemplateObject.update(SQL, object.getGroupid());
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
    public Integer deleteObject(Integer id) {
        try {
            String SQL = "DELETE FROM " + Global.getConfigValue("app.dbname") + ".group where groupid = ?";
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
    public List<Group> loadObjects(String SQL, Object... params) {
        try {
            List<Group> objects = jdbcTemplateObject.query(SQL, params, new GroupMapper());
            this.exceptionCode = HttpStatus.OK;
            this.exceptionMsg = "";
            return objects;
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType = "DataAccessException";
            return null;
        }

    }

    @Override
    public Group queryForObject(String SQL, Object... params) {
        try {
            Group ret = this.jdbcTemplateObject.queryForObject(SQL, params, new GroupMapper());
            if (ret != null) {
                this.exceptionCode = HttpStatus.OK;
                this.exceptionMsg = "";
                return ret;
            } else {
                this.exceptionCode = HttpStatus.NO_CONTENT;
                this.exceptionMsg = "Object is not found";
                return null;
            }
        } catch (EmptyResultDataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.NO_CONTENT;
            this.exceptionMsg = "Object is not found";
            exceptionType = "EmptyResultDataAccessException";
            return null;
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType = "DataAccessException";
            return null;
        }
    }

    @Override
    public Integer getRowCount(String SQL, Object... params) {
        try {
            this.exceptionCode = HttpStatus.OK;
            this.exceptionMsg = "";
            return jdbcTemplateObject.queryForObject(SQL, params, Integer.class);
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType = "DataAccessException";
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

    @Override
    public Group getObject(Integer id) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".group";
                String index_type = "group";
                GetRequest request = new GetRequest(index_name, index_type, String.valueOf(id));
                GetResponse response = client.get(request);
                if (!response.isSourceEmpty()) {
                    rest.close();
                    Gson gson = Global.getGsonObject("yyyy-MM-dd'T'HH:mm:ss.Z");
                    return gson.fromJson(response.getSourceAsString(), Group.class);
                } else {
                    rest.close();
                    return null;
                }
            }
        } catch (JsonSyntaxException | IOException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType = "JsonSyntaxException | IOException";
            return null;
        }
    }

    public Group getObjectByUserField(String fieldname, String value) {
        List filter = new ArrayList();
        RequestFilter rf = new RequestFilter();
        rf.setName(fieldname);
        rf.setRequired(true);
        rf.setType(RequestFilter.EQUAL);
        rf.setValue(value);
        filter.add(rf);
        List<Group> ret = this.filterObjects(0, 1, "groupid", 0, filter);
        if (ret != null && !ret.isEmpty()) {
            return ret.get(0);
        }
        return null;
    }

    @Override
    public List<Group> getAllObjects() {
        return this.filterObjects(0, 0, "", 0, null);
    }

    @Override
    public Integer getTotalRow() {
        return this.filterObjectsSize(new ArrayList());
    }

    @Override
    public Boolean checkObjectExist(Integer id) {
        return !(this.getObject(id) == null);
    }

    @Override
    public List<Group> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".group";
                String index_type = "group";
                SearchRequest searchRequest = new SearchRequest(index_name);
                searchRequest.types(index_type);
                SearchSourceBuilder searchBuilder = ElasticUtils.getSourceBuilder(first, pageSize, sortField, sortOrder, filters);
                searchRequest.source(searchBuilder);
                logger.info(searchBuilder.toString());
                SearchResponse response = client.search(searchRequest);
                SearchHits hits = response.getHits();
                Gson gson = Global.getGsonObject("yyyy-MM-dd'T'HH:mm:ss.Z");
                List ret = new ArrayList();
                if (hits.getTotalHits() > 0) {
                    for (SearchHit hit : hits.getHits()) {
                        Group item = gson.fromJson(hit.getSourceAsString(), Group.class);
                        ret.add(item);
                    }
                    this.exceptionCode = HttpStatus.OK;
                    this.exceptionMsg = "";
                    return ret;
                } else {
                    this.exceptionCode = HttpStatus.OK;
                    this.exceptionMsg = "Not found";
                }
                rest.close();
                return new ArrayList();
            }
        } catch (JsonSyntaxException | IOException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType = "JsonSyntaxException | IOException";
            return new ArrayList();
        }
    }

    @Override
    public int filterObjectsSize(List<RequestFilter> filters) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".group";
                String index_type = "group";
                SearchRequest searchRequest = new SearchRequest(index_name);
                searchRequest.types(index_type);
                SearchSourceBuilder searchBuilder = ElasticUtils.getSourceBuilder(filters);
                searchRequest.source(searchBuilder);
                logger.info(searchBuilder.toString());
                SearchResponse response = client.search(searchRequest);
                SearchHits hits = response.getHits();
                rest.close();
                if (hits.getTotalHits() > 0) {
                    this.exceptionCode = HttpStatus.OK;
                    this.exceptionMsg = "";
                } else {
                    this.exceptionCode = HttpStatus.OK;
                    this.exceptionMsg = "Not found";
                }
                return Long.valueOf(hits.getTotalHits()).intValue();
            }
        } catch (IOException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType = "IOException";
            return 0;
        }
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
