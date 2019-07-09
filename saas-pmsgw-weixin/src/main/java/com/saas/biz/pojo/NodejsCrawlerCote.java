package com.saas.biz.pojo;

import java.util.Date;

public class NodejsCrawlerCote {
    private String cote_id;

    private String cote_name;

    private String cote_website;

    private String cote_state;

    private Date create_time;

    private Date modify_time;

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

    public String getCote_website() {
        return cote_website;
    }

    public void setCote_website(String cote_website) {
        this.cote_website = cote_website;
    }

    public String getCote_state() {
        return cote_state;
    }

    public void setCote_state(String cote_state) {
        this.cote_state = cote_state;
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