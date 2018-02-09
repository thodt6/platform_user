package com.unibro.access_list;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import org.apache.log4j.Logger;

public class Access_list implements Serializable {

    private Integer access_id = 0;
    private String func_id = "";
    private String prop_id = "";
    private String principle_type = "";
    private Integer principle_id = 0;
    private Integer permission =0;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.Z")
    private java.util.Date update_time = new java.util.Date();
    private Integer update_id = 0;
    private String application_id;

    static final Logger logger = Logger.getLogger(Access_list.class.getName());

    public void setAccess_id(Integer access_id) {
        this.access_id = access_id;
    }

    @JsonProperty("access_id")
    public Integer getAccess_id() {
        return this.access_id;
    }

    public void setFunc_id(String func_id) {
        this.func_id = func_id;
    }

    @JsonProperty("func_id")
    public String getFunc_id() {
        return this.func_id;
    }

    public void setProp_id(String prop_id) {
        this.prop_id = prop_id;
    }

    @JsonProperty("prop_id")
    public String getProp_id() {
        return this.prop_id;
    }

    public void setPrinciple_type(String principle_type) {
        this.principle_type = principle_type;
    }

    @JsonProperty("principle_type")
    public String getPrinciple_type() {
        return this.principle_type;
    }

    public void setPrinciple_id(Integer principle_id) {
        this.principle_id = principle_id;
    }

    @JsonProperty("principle_id")
    public Integer getPrinciple_id() {
        return this.principle_id;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    @JsonProperty("permission")
    public Integer getPermission() {
        return this.permission;
    }

    public void setUpdate_time(java.util.Date update_time) {
        this.update_time = update_time;
    }

    @JsonProperty("update_time")
    public java.util.Date getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_id(Integer update_id) {
        this.update_id = update_id;
    }

    @JsonProperty("update_id")
    public Integer getUpdate_id() {
        return this.update_id;
    }

    public static Access_list getInstant() {
        Access_list instant = new Access_list();
        instant.setAccess_id(0);
        instant.setFunc_id("");
        instant.setProp_id("");
        instant.setPrinciple_type("");
        instant.setPrinciple_id(0);
        instant.setPermission(0);
        instant.setUpdate_time(new java.util.Date());
        instant.setUpdate_id(0);
        instant.setApplication_id("");
        return instant;
    }

    public Access_list() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Access_list)) {
            return false;
        }
        Access_list compareObj = (Access_list) obj;
        return (compareObj.getAccess_id().equals(this.getAccess_id()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.getAccess_id() != null ? this.getAccess_id().hashCode() : 0);
        return hash;
    }

    /**
     * @return the application_id
     */
    public String getApplication_id() {
        return application_id;
    }

    /**
     * @param application_id the application_id to set
     */
    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

}
