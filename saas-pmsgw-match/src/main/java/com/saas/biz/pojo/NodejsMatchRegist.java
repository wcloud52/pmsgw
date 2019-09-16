package com.saas.biz.pojo;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class NodejsMatchRegist implements Serializable {
    private String id;

    private String match_id;

    private String member_code;

    private String member_name;

    private Date create_time;

    private Date modify_time;

    private String cote_id;

    private String cote_name;

    private String create_user_id;

    private String regist;

    private String pigeon_code;

    private int rank;
    private float reward;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id == null ? null : match_id.trim();
    }

    public String getMember_code() {
        return member_code;
    }

    public void setMember_code(String member_code) {
        this.member_code = member_code == null ? null : member_code.trim();
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name == null ? null : member_name.trim();
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

    public String getCote_id() {
        return cote_id;
    }

    public void setCote_id(String cote_id) {
        this.cote_id = cote_id == null ? null : cote_id.trim();
    }

    public String getCote_name() {
        return cote_name;
    }

    public void setCote_name(String cote_name) {
        this.cote_name = cote_name == null ? null : cote_name.trim();
    }

    public String getRegist() {
        return regist;
    }

    public void setRegist(String regist) {
        this.regist = regist == null ? null : regist.trim();
    }

    public String getPigeon_code() {
        return pigeon_code;
    }

    public void setPigeon_code(String pigeon_code) {
        this.pigeon_code = pigeon_code == null ? null : pigeon_code.trim();
    }

    public String getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(String create_user_id) {
        this.create_user_id = create_user_id == null ? null : create_user_id.trim();
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getReward() {
        return reward;
    }

    public void setReward(float reward) {
        this.reward = reward;
    }
}