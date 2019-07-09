package com.saas.biz.pojo;

import java.util.Date;

public class WappPigowner {
    private String id;

    private String name;

    private String description;

    private String ringnum;

    private String pigowner;

    private Date createdDatetime;

    private Date updateDatetime;

    private String createdBy;

    private String updateBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRingnum() {
        return ringnum;
    }

    public void setRingnum(String ringnum) {
        this.ringnum = ringnum;
    }

    public String getPigowner() {
        return pigowner;
    }

    public void setPigowner(String pigowner) {
        this.pigowner = pigowner;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}