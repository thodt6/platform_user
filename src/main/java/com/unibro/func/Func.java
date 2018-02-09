package com.unibro.func;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unibro.utils.Global;
import java.io.Serializable;
import org.apache.log4j.Logger;

public class Func implements Serializable {

    private String func_id = Global.getRandomString();
    private String name = "";
    private String description = "";
    private String uri = "";
    private Integer created_id = 0;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.Z")
    private java.util.Date update_time = new java.util.Date();
    private String application_id = "";

    static final Logger logger = Logger.getLogger(Func.class.getName());

    public void setFunc_id(String func_id) {
        this.func_id = func_id;
    }

    @JsonProperty("func_id")
    public String getFunc_id() {
        return this.func_id;
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

    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("uri")
    public String getUri() {
        return this.uri;
    }

    public void setCreated_id(Integer created_id) {
        this.created_id = created_id;
    }

    @JsonProperty("created_id")
    public Integer getCreated_id() {
        return this.created_id;
    }

    public void setUpdate_time(java.util.Date update_time) {
        this.update_time = update_time;
    }

    @JsonProperty("update_time")
    public java.util.Date getUpdate_time() {
        return this.update_time;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    @JsonProperty("application_id")
    public String getApplication_id() {
        return this.application_id;
    }

    public static Func getInstant() {
        Func instant = new Func();
        instant.setFunc_id(Global.getRandomString());
        instant.setName("");
        instant.setDescription("");
        instant.setUri("");
        instant.setCreated_id(0);
        instant.setUpdate_time(new java.util.Date());
        instant.setApplication_id("");

        return instant;
    }

    public Func() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Func)) {
            return false;
        }
        Func compareObj = (Func) obj;
        return (compareObj.getFunc_id().equals(this.getFunc_id()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.getFunc_id() != null ? this.getFunc_id().hashCode() : 0);
        return hash;
    }

}
