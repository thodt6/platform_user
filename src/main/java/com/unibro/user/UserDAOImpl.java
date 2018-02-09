/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.user;

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
public class UserDAOImpl implements UserDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    Logger logger = Logger.getLogger(this.getClass().getName());
    private String exceptionMsg = "";
    private HttpStatus exceptionCode = HttpStatus.OK;
    private String exceptionType = "";

    RestHighLevelClient client;
    RestClientBuilder builder;

    public UserDAOImpl() {
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
    public User createObject(final User object) {
        try {
            final String SQL = "INSERT INTO " + Global.getConfigValue("app.dbname") + ".user(oauth_provider,uid,username,password_hash,email,phone,first_name,last_name,gender,picture,country_code,state,city,post_code,created_time,modified_time,last_login,last_logout,status,login_token,expired_time,password_reset_token,application_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            int ret = jdbcTemplateObject.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) {
                    try {
                        PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"oauth_provider", "uid", "username", "password_hash", "email", "phone", "first_name", "last_name", "gender", "picture", "country_code", "state", "city", "post_code", "created_time", "modified_time", "last_login", "last_logout", "status", "login_token", "expired_time", "password_reset_token", "application_id"});
                        ps.setObject(1, object.getOauth_provider());
                        ps.setObject(2, object.getUid());
                        ps.setObject(3, object.getUsername());
                        ps.setObject(4, object.getPassword_hash());
                        ps.setObject(5, object.getEmail());
                        ps.setObject(6, object.getPhone());
                        ps.setObject(7, object.getFirst_name());
                        ps.setObject(8, object.getLast_name());
                        ps.setObject(9, object.getGender());
                        ps.setObject(10, object.getPicture());
                        ps.setObject(11, object.getCountry_code());
                        ps.setObject(12, object.getState());
                        ps.setObject(13, object.getCity());
                        ps.setObject(14, object.getPost_code());
                        ps.setObject(15, object.getCreated_time());
                        ps.setObject(16, object.getModified_time());
                        ps.setObject(17, object.getLast_login());
                        ps.setObject(18, object.getLast_logout());
                        ps.setObject(19, object.getStatus());
                        ps.setObject(20, object.getLogin_token());
                        ps.setObject(21, object.getExpired_time());
                        ps.setObject(22, object.getPassword_reset_token());
                        ps.setObject(23, object.getApplication_id());

                        return ps;
                    } catch (SQLException ex) {
                        logger.error(ex);
                        exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
                        exceptionMsg = ex.toString();
                        return null;
                    }
                }
            }, holder);
            if (ret > 0) {
                this.exceptionCode = HttpStatus.OK;
                this.exceptionMsg = "";
                int primaryKey = holder.getKey().intValue();
                object.setUserid(primaryKey);
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
            this.exceptionType="DataAccessException";
            return null;
        }
    }

    @Override
    public User getObject(Integer id) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".user";
                String index_type = "user";
                GetRequest request = new GetRequest(index_name, index_type, String.valueOf(id));
                GetResponse response = client.get(request);
                if (!response.isSourceEmpty()) {
                    rest.close();
                    Gson gson = Global.getGsonObject("yyyy-MM-dd'T'HH:mm:ss.Z");
                    return gson.fromJson(response.getSourceAsString(), User.class);
                } else {
                    rest.close();
                    
                    return null;
                }
            }
        } catch (JsonSyntaxException | IOException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            this.exceptionType="JsonSyntaxException | IOException";
            return null;
        }
    }

    public User getObjectByUserField(String fieldname, String value) {
        List filter = new ArrayList();
        RequestFilter rf = new RequestFilter();
        rf.setName(fieldname);
        rf.setRequired(true);
        rf.setType(RequestFilter.EQUAL);
        rf.setValue(value);
        filter.add(rf);
        List<User> ret = this.filterObjects(0, 1, "id", 0, filter);
        if (ret != null && !ret.isEmpty()) {
            return ret.get(0);
        }
        return null;
    }

    @Override
    public User updateObject(final User object) {
        try {
            final String SQL = "UPDATE " + Global.getConfigValue("app.dbname") + ".user SET oauth_provider = ?,uid = ?,username = ?,password_hash = ?,email = ?,phone = ?,first_name = ?,last_name = ?,gender = ?,picture = ?,country_code = ?,state = ?,city = ?,post_code = ?,created_time = ?,modified_time = ?,last_login = ?,last_logout = ?,status = ?,login_token = ?,expired_time = ?,password_reset_token = ?,application_id = ? WHERE userid = ?";
            int ret = jdbcTemplateObject.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) {
                    try {
                        PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"oauth_provider", "uid", "username", "password_hash", "email", "phone", "first_name", "last_name", "gender", "picture", "country_code", "state", "city", "post_code", "created_time", "modified_time", "last_login", "last_logout", "status", "login_token", "expired_time", "password_reset_token", "application_id", "userid"});
                        ps.setObject(1, object.getOauth_provider());
                        ps.setObject(2, object.getUid());
                        ps.setObject(3, object.getUsername());
                        ps.setObject(4, object.getPassword_hash());
                        ps.setObject(5, object.getEmail());
                        ps.setObject(6, object.getPhone());
                        ps.setObject(7, object.getFirst_name());
                        ps.setObject(8, object.getLast_name());
                        ps.setObject(9, object.getGender());
                        ps.setObject(10, object.getPicture());
                        ps.setObject(11, object.getCountry_code());
                        ps.setObject(12, object.getState());
                        ps.setObject(13, object.getCity());
                        ps.setObject(14, object.getPost_code());
                        ps.setObject(15, object.getCreated_time());
                        ps.setObject(16, object.getModified_time());
                        ps.setObject(17, object.getLast_login());
                        ps.setObject(18, object.getLast_logout());
                        ps.setObject(19, object.getStatus());
                        ps.setObject(20, object.getLogin_token());
                        ps.setObject(21, object.getExpired_time());
                        ps.setObject(22, object.getPassword_reset_token());
                        ps.setObject(23, object.getApplication_id());
                        ps.setObject(24, object.getUserid());

                        return ps;
                    } catch (SQLException ex) {
                        logger.error(ex);
                        exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
                        exceptionMsg = ex.toString();
                        exceptionType="SQLException";
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
            exceptionType="DataAccessException";
            return null;
        }

    }

    @Override
    public Integer deleteObject(User object) {
        try {
            String SQL = "DELETE FROM " + Global.getConfigValue("app.dbname") + ".user where userid = ?";
            int ret = jdbcTemplateObject.update(SQL, object.getUserid());
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
            exceptionType="DataAccessException";
            return 0;
        }
    }

    @Override
    public Integer deleteObject(Integer id) {
        try {
            String SQL = "DELETE FROM " + Global.getConfigValue("app.dbname") + ".user where userid = ?";
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
            exceptionType="DataAccessException";
            return 0;
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
            exceptionType="DataAccessException";
            return 0;
        }
    }

    @Override
    public List<User> loadObjects(String SQL, Object... params) {
        try {
            List<User> objects = jdbcTemplateObject.query(SQL, params, new UserMapper());
            this.exceptionCode = HttpStatus.OK;
            this.exceptionMsg = "";
            return objects;
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType="DataAccessException";
            return null;
        }

    }

    @Override
    public User queryForObject(String SQL, Object... params) {
        try {
            User ret = this.jdbcTemplateObject.queryForObject(SQL, params, new UserMapper());
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
            exceptionType="EmptyResultDataAccessException";
            return null;
        } catch (DataAccessException ex) {
            logger.error(ex);
            this.exceptionCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.exceptionMsg = ex.toString();
            exceptionType="DataAccessException";
            return null;
        }
    }

    @Override
    public List<User> getAllObjects() {
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
    public List<User> filterObjects(int first, int pageSize, String sortField, int sortOrder, List<RequestFilter> filters) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".user";
                String index_type = "user";
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
                        User item = gson.fromJson(hit.getSourceAsString(), User.class);
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
            exceptionType="JsonSyntaxException | IOException";
            return new ArrayList();
        }
    }

    @Override
    public int filterObjectsSize(List<RequestFilter> filters) {
        try {
            try (RestClient rest = builder.build()) {
                this.client = new RestHighLevelClient(rest);
                String index_name = Global.getConfigValue("app.elastic.schema") + ".user";
                String index_type = "user";
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
