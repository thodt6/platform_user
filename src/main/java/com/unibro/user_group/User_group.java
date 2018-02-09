package com.unibro.user_group;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import org.apache.log4j.Logger;

public class User_group implements Serializable {

    private Integer userid = 0;
    private String groupid_list = "";
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.Z")
    private java.util.Date update_time = new java.util.Date();
    private Integer created_id = 0;
    private String default_groupid="";

    static final Logger logger = Logger.getLogger(User_group.class.getName());

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @JsonProperty("userid")
    public Integer getUserid() {
        return this.userid;
    }

    public void setGroupid_list(String groupid_list) {
        this.groupid_list = groupid_list;
    }

    @JsonProperty("groupid_list")
    public String getGroupid_list() {
        return this.groupid_list;
    }

    public void setUpdate_time(java.util.Date update_time) {
        this.update_time = update_time;
    }

    @JsonProperty("update_time")
    public java.util.Date getUpdate_time() {
        return this.update_time;
    }

    public void setCreated_id(Integer created_id) {
        this.created_id = created_id;
    }

    @JsonProperty("created_id")
    public Integer getCreated_id() {
        return this.created_id;
    }

    public static User_group getInstant() {
        User_group instant = new User_group();
        instant.setUserid(0);
        instant.setGroupid_list("");
        instant.setUpdate_time(new java.util.Date());
        instant.setCreated_id(0);
        instant.setDefault_groupid("");
        return instant;
    }

    public User_group() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User_group)) {
            return false;
        }
        User_group compareObj = (User_group) obj;
        return (compareObj.getUserid().equals(this.getUserid()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.getUserid() != null ? this.getUserid().hashCode() : 0);
        return hash;
    }

    /**
     * @return the default_groupid
     */
    public String getDefault_groupid() {
        return default_groupid;
    }

    /**
     * @param default_groupid the default_groupid to set
     */
    public void setDefault_groupid(String default_groupid) {
        this.default_groupid = default_groupid;
    }

}
