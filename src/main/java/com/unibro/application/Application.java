package com.unibro.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unibro.utils.Global;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application implements Serializable {

    private String appid = Global.getRandomString();
    private String name = "";
    private String description = "";
    private Integer update_id = 0;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private java.util.Date update_time = new java.util.Date();

    static final Logger logger = LogManager.getLogger(Application.class.getName());

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @JsonProperty("appid")
    public String getAppid() {
        return this.appid;
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

    public void setUpdate_id(Integer update_id) {
        this.update_id = update_id;
    }

    @JsonProperty("update_id")
    public Integer getUpdate_id() {
        return this.update_id;
    }

    public void setUpdate_time(java.util.Date update_time) {
        this.update_time = update_time;
    }

    @JsonProperty("update_time")
    public java.util.Date getUpdate_time() {
        return this.update_time;
    }

    public static Application getInstant() {
        Application instant = new Application();
        instant.setAppid(Global.getRandomString());
        instant.setName("");
        instant.setDescription("");
        instant.setUpdate_id(0);
        instant.setUpdate_time(new java.util.Date());

        return instant;
    }

    public Application() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Application)) {
            return false;
        }
        Application compareObj = (Application) obj;
        return (compareObj.getAppid().equals(this.getAppid()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.getAppid() != null ? this.getAppid().hashCode() : 0);
        return hash;
    }

}
