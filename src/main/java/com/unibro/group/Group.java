package com.unibro.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Group implements Serializable {

    private Integer groupid = 0;
    private String name = "";
    private String description = "";
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.Z")
    private java.util.Date created_time = new java.util.Date();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.Z")
    private java.util.Date modified_time = new java.util.Date();
    private Integer created_id = 0;
    private String application_id = "";
    private String uri;

    static final Logger logger = LogManager.getLogger(Group.class.getName());

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    @JsonProperty("groupid")
    public Integer getGroupid() {
        return this.groupid;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("description")
    public String getDescription() {
        return this.description;
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

    public void setCreated_id(Integer created_id) {
        this.created_id = created_id;
    }

    @JsonProperty("created_id")
    public Integer getCreated_id() {
        return this.created_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    @JsonProperty("application_id")
    public String getApplication_id() {
        return this.application_id;
    }

    public static Group getInstant() {
        Group instant = new Group();
        instant.setGroupid(0);
        instant.setName("");
        instant.setDescription("");
        instant.setCreated_time(new java.util.Date());
        instant.setModified_time(new java.util.Date());
        instant.setCreated_id(0);
        instant.setApplication_id("");
        instant.setUri("");
        return instant;
    }

    public Group() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Group)) {
            return false;
        }
        Group compareObj = (Group) obj;
        return (compareObj.getGroupid().equals(this.getGroupid()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.getGroupid() != null ? this.getGroupid().hashCode() : 0);
        return hash;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

}
