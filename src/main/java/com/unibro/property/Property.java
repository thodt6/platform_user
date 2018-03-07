package com.unibro.property;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unibro.utils.Global;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Property implements Serializable {

    private String prop_id = Global.getRandomString();
    private String name = "";
    private String uri = "";
    private Integer type = 0;
    private String func_id = "";
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private java.util.Date update_time = new java.util.Date();
    private Integer created_id = 0;
    private String application_id = "";

    static final Logger logger = LogManager.getLogger(Property.class.getName());

    public void setProp_id(String prop_id) {
        this.prop_id = prop_id;
    }

    @JsonProperty("prop_id")
    public String getProp_id() {
        return this.prop_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("uri")
    public String getUri() {
        return this.uri;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @JsonProperty("type")
    public Integer getType() {
        return this.type;
    }

    public void setFunc_id(String func_id) {
        this.func_id = func_id;
    }

    @JsonProperty("func_id")
    public String getFunc_id() {
        return this.func_id;
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

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    @JsonProperty("application_id")
    public String getApplication_id() {
        return this.application_id;
    }

    public static Property getInstant() {
        Property instant = new Property();
        instant.setProp_id(Global.getRandomString());
        instant.setName("");
        instant.setUri("");
        instant.setType(0);
        instant.setFunc_id("");
        instant.setUpdate_time(new java.util.Date());
        instant.setCreated_id(0);
        instant.setApplication_id("");

        return instant;
    }

    public Property() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Property)) {
            return false;
        }
        Property compareObj = (Property) obj;
        return (compareObj.getProp_id().equals(this.getProp_id()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.getProp_id() != null ? this.getProp_id().hashCode() : 0);
        return hash;
    }

}
