package com.saas.biz.pojo;

import java.util.Date;

public class WeixinUserPurse {
    private String id;

    private String openid;

    private Integer money;

    private String cote_id;

    private Date create_time;

    private Date modify_time;

    private String state;
    private String bind_name;
    private String bind_tel;
    private String pigowner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getCote_id() {
        return cote_id;
    }

    public void setCote_id(String cote_id) {
        this.cote_id = cote_id == null ? null : cote_id.trim();
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getBind_name() {
        return bind_name;
    }

    public void setBind_name(String bind_name) {
        this.bind_name = bind_name;
    }

    public String getBind_tel() {
        return bind_tel;
    }

    public void setBind_tel(String bind_tel) {
        this.bind_tel = bind_tel;
    }

    public String getPigowner() {
        return pigowner;
    }

    public void setPigowner(String pigowner) {
        this.pigowner = pigowner;
    }
}