package com.unibro.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.unibro.utils.Global;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class User implements Serializable {

    private Integer userid = 0;
    private String oauth_provider = "";
    private String uid = "";
    private String username = "";
    private String password_hash = "";
    private String email = "";
    private String phone = "";
    private String first_name = "";
    private String last_name = "";
    private String gender = "";
    private String picture = "";
    private String country_code = "";
    private String state = "";
    private String city = "";
    private String post_code = "";
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private java.util.Date created_time = new java.util.Date();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private java.util.Date modified_time = new java.util.Date();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private java.util.Date last_login = new java.util.Date();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private java.util.Date last_logout = new java.util.Date();
    private Integer status = 0;
    private String login_token = "";
    private Long expired_time = Long.valueOf(0);
    private String password_reset_token = "";
    private String application_id = "";

    static final Logger logger = LogManager.getLogger(User.class.getName());

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @JsonProperty("userid")
    public Integer getUserid() {
        return this.userid;
    }

    public void setOauth_provider(String oauth_provider) {
        this.oauth_provider = oauth_provider;
    }

    @JsonProperty("oauth_provider")
    public String getOauth_provider() {
        return this.oauth_provider;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @JsonProperty("uid")
    public String getUid() {
        return this.uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("username")
    public String getUsername() {
        return this.username;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    @JsonProperty("password_hash")
    public String getPassword_hash() {
        return this.password_hash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("email")
    public String getEmail() {
        return this.email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return this.phone;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @JsonProperty("first_name")
    public String getFirst_name() {
        return this.first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @JsonProperty("last_name")
    public String getLast_name() {
        return this.last_name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("gender")
    public String getGender() {
        return this.gender;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @JsonProperty("picture")
    public String getPicture() {
        return this.picture;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    @JsonProperty("country_code")
    public String getCountry_code() {
        return this.country_code;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("state")
    public String getState() {
        return this.state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("city")
    public String getCity() {
        return this.city;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    @JsonProperty("post_code")
    public String getPost_code() {
        return this.post_code;
    }

    public void setCreated_time(java.util.Date created_time) {
        this.created_time = created_time;
    }

    @JsonProperty("created_time")
    public java.util.Date getCreated_time() {
        return this.created_time;
    }

    public void setModified_time(java.util.Date modified_time) {
        this.modified_time = modified_time;
    }

    @JsonProperty("modified_time")
    public java.util.Date getModified_time() {
        return this.modified_time;
    }

    public void setLast_login(java.util.Date last_login) {
        this.last_login = last_login;
    }

    @JsonProperty("last_login")
    public java.util.Date getLast_login() {
        return this.last_login;
    }

    public void setLast_logout(java.util.Date last_logout) {
        this.last_logout = last_logout;
    }

    @JsonProperty("last_logout")
    public java.util.Date getLast_logout() {
        return this.last_logout;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("status")
    public Integer getStatus() {
        return this.status;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }

    @JsonProperty("login_token")
    public String getLogin_token() {
        return this.login_token;
    }

    public void setExpired_time(Long expired_time) {
        this.expired_time = expired_time;
    }

    @JsonProperty("expired_time")
    public Long getExpired_time() {
        return this.expired_time;
    }

    public void setPassword_reset_token(String password_reset_token) {
        this.password_reset_token = password_reset_token;
    }

    @JsonProperty("password_reset_token")
    public String getPassword_reset_token() {
        return this.password_reset_token;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    @JsonProperty("application_id")
    public String getApplication_id() {
        return this.application_id;
    }

    public static User getInstant() {
        User instant = new User();
        instant.setUserid(0);
        instant.setOauth_provider("");
        instant.setUid("");
        instant.setUsername("");
        instant.setPassword_hash("");
        instant.setEmail("");
        instant.setPhone("");
        instant.setFirst_name("");
        instant.setLast_name("");
        instant.setGender("");
        instant.setPicture("");
        instant.setCountry_code("");
        instant.setState("");
        instant.setCity("");
        instant.setPost_code("");
        instant.setCreated_time(new java.util.Date());
        instant.setModified_time(new java.util.Date());
        instant.setLast_login(new java.util.Date());
        instant.setLast_logout(new java.util.Date());
        instant.setStatus(0);
        instant.setLogin_token("");
        instant.setExpired_time(Long.valueOf(0));
        instant.setPassword_reset_token("");
        instant.setApplication_id("");

        return instant;
    }

    public User() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User compareObj = (User) obj;
        return (compareObj.getUserid().equals(this.getUserid()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.getUserid() != null ? this.getUserid().hashCode() : 0);
        return hash;
    }

    public boolean checkValidSession() {
        logger.info("Login token:" + this.getLogin_token());
        if (this.getLogin_token().equals("")) {
            return false;
        }
        if (this.getExpired_time() <= System.currentTimeMillis()) {
            return false;
        }
        return true;
    }

    public String toJsonString() {
        Gson gson = Global.getGsonObject("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        JsonObject obj = gson.toJsonTree(this).getAsJsonObject();
        obj.remove("login_token");
        obj.remove("password_reset_token");
        return obj.toString();
    }

    public String toJsonToken() {
        JsonObject obj = new JsonObject();
        obj.addProperty("userid", this.getUserid());
        obj.addProperty("username", this.getUsername());
        obj.addProperty("password_hash", this.getPassword_hash());
        obj.addProperty("uid", this.getUid());
        obj.addProperty("oauth_provider", this.getOauth_provider());
        return obj.toString();
    }

}
