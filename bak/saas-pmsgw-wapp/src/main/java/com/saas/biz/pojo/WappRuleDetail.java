package com.saas.biz.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class WappRuleDetail {
    private String id;

    private String masterId;

    private String name;

    private String description;

    private String detailsMessage;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdDatetime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateDatetime;

    private String createdBy;

    private String updateBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
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

    public String getDetailsMessage() {
        return detailsMessage;
    }

    public void setDetailsMessage(String detailsMessage) {
        this.detailsMessage = detailsMessage;
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