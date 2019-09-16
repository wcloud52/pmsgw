package com.saas.biz.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class NodejsCrawlerDetailGame {
    private String detail_id;

    private String cote_id;

    private String cote_name;

    private String cote_state;

    private String master_id;

    private String master_text;

    private String master_href;

    private String master_type;

    private String master_website;

    private Integer detail_page;

    private Integer detail_page_index;

    private String detail_state;

    private String distence;

    private String ringnum;

    private String pigowner;

    private String cometime;

    private String cotenum;

    private String speed;

    private Integer rank;

    private String receiver_openid;

    private String receiver_nickname;

    private String receiver_headimgurl;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modify_time;

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getCote_id() {
        return cote_id;
    }

    public void setCote_id(String cote_id) {
        this.cote_id = cote_id;
    }

    public String getCote_name() {
        return cote_name;
    }

    public void setCote_name(String cote_name) {
        this.cote_name = cote_name;
    }

    public String getCote_state() {
        return cote_state;
    }

    public void setCote_state(String cote_state) {
        this.cote_state = cote_state;
    }

    public String getMaster_id() {
        return master_id;
    }

    public void setMaster_id(String master_id) {
        this.master_id = master_id;
    }

    public String getMaster_text() {
        return master_text;
    }

    public void setMaster_text(String master_text) {
        this.master_text = master_text;
    }

    public String getMaster_href() {
        return master_href;
    }

    public void setMaster_href(String master_href) {
        this.master_href = master_href;
    }

    public String getMaster_type() {
        return master_type;
    }

    public void setMaster_type(String master_type) {
        this.master_type = master_type;
    }

    public String getMaster_website() {
        return master_website;
    }

    public void setMaster_website(String master_website) {
        this.master_website = master_website;
    }

    public Integer getDetail_page() {
        return detail_page;
    }

    public void setDetail_page(Integer detail_page) {
        this.detail_page = detail_page;
    }

    public Integer getDetail_page_index() {
        return detail_page_index;
    }

    public void setDetail_page_index(Integer detail_page_index) {
        this.detail_page_index = detail_page_index;
    }

    public String getDetail_state() {
        return detail_state;
    }

    public void setDetail_state(String detail_state) {
        this.detail_state = detail_state;
    }

    public String getDistence() {
        return distence;
    }

    public void setDistence(String distence) {
        this.distence = distence;
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

    public String getCometime() {
        return cometime;
    }

    public void setCometime(String cometime) {
        this.cometime = cometime;
    }

    public String getCotenum() {
        return cotenum;
    }

    public void setCotenum(String cotenum) {
        this.cotenum = cotenum;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getReceiver_openid() {
        return receiver_openid;
    }

    public void setReceiver_openid(String receiver_openid) {
        this.receiver_openid = receiver_openid;
    }

    public String getReceiver_nickname() {
        return receiver_nickname;
    }

    public void setReceiver_nickname(String receiver_nickname) {
        this.receiver_nickname = receiver_nickname;
    }

    public String getReceiver_headimgurl() {
        return receiver_headimgurl;
    }

    public void setReceiver_headimgurl(String receiver_headimgurl) {
        this.receiver_headimgurl = receiver_headimgurl;
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
}