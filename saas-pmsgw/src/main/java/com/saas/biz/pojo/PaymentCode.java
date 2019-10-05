package com.saas.biz.pojo;

import java.util.Date;

public class PaymentCode {
    private String id;

    private String cote_id;

    private Date creat_time;

    private Date modify_time;

    private String z_storageKey;

    private String z_url;

    private String z_storageType;

    private String w_storageKey;

    private String w_url;

    private String w_storageType;

    private Integer z_id;

    private Integer w_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCote_id() {
        return cote_id;
    }

    public void setCote_id(String cote_id) {
        this.cote_id = cote_id == null ? null : cote_id.trim();
    }

    public Date getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(Date creat_time) {
        this.creat_time = creat_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    public String getZ_storageKey() {
        return z_storageKey;
    }

    public void setZ_storageKey(String z_storageKey) {
        this.z_storageKey = z_storageKey == null ? null : z_storageKey.trim();
    }

    public String getZ_url() {
        return z_url;
    }

    public void setZ_url(String z_url) {
        this.z_url = z_url == null ? null : z_url.trim();
    }

    public String getZ_storageType() {
        return z_storageType;
    }

    public void setZ_storageType(String z_storageType) {
        this.z_storageType = z_storageType == null ? null : z_storageType.trim();
    }

    public String getW_storageKey() {
        return w_storageKey;
    }

    public void setW_storageKey(String w_storageKey) {
        this.w_storageKey = w_storageKey == null ? null : w_storageKey.trim();
    }

    public String getW_url() {
        return w_url;
    }

    public void setW_url(String w_url) {
        this.w_url = w_url == null ? null : w_url.trim();
    }

    public String getW_storageType() {
        return w_storageType;
    }

    public void setW_storageType(String w_storageType) {
        this.w_storageType = w_storageType == null ? null : w_storageType.trim();
    }

    public Integer getZ_id() {
        return z_id;
    }

    public void setZ_id(Integer z_id) {
        this.z_id = z_id;
    }

    public Integer getW_id() {
        return w_id;
    }

    public void setW_id(Integer w_id) {
        this.w_id = w_id;
    }
}