package com.saas.biz.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class NodejsCustomerMessage {
    private String message_id;

    private String message_senderId;

    private String message_senderName;

    private String message_receiverId;

    private String message_receiverName;

    private Date message_sendTime;

    private String message_type;

    private String message_title;

    private String message_text;

    private Integer message_status;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date message_create_time;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date message_modify_time;

    private String game_detail_id;

    private String game_cote_id;

    private String game_cote_name;

    private String game_cote_state;

    private String game_master_id;

    private String game_master_text;

    private String game_master_href;

    private String game_master_type;

    private String game_master_website;

    private Integer game_detail_page;

    private Integer game_detail_page_index;

    private String game_detail_state;

    private String game_distence;

    private String game_ringnum;

    private String game_pigowner;

    private String game_cometime;

    private String game_cotenum;

    private String game_speed;

    private Integer game_rank;

    private String game_receiver_openid;

    private String game_receiver_nickname;

    private String game_receiver_headimgurl;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date game_create_time;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date game_modify_time;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage_senderId() {
        return message_senderId;
    }

    public void setMessage_senderId(String message_senderId) {
        this.message_senderId = message_senderId;
    }

    public String getMessage_senderName() {
        return message_senderName;
    }

    public void setMessage_senderName(String message_senderName) {
        this.message_senderName = message_senderName;
    }

    public String getMessage_receiverId() {
        return message_receiverId;
    }

    public void setMessage_receiverId(String message_receiverId) {
        this.message_receiverId = message_receiverId;
    }

    public String getMessage_receiverName() {
        return message_receiverName;
    }

    public void setMessage_receiverName(String message_receiverName) {
        this.message_receiverName = message_receiverName;
    }

    public Date getMessage_sendTime() {
        return message_sendTime;
    }

    public void setMessage_sendTime(Date message_sendTime) {
        this.message_sendTime = message_sendTime;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public Integer getMessage_status() {
        return message_status;
    }

    public void setMessage_status(Integer message_status) {
        this.message_status = message_status;
    }

    public Date getMessage_create_time() {
        return message_create_time;
    }

    public void setMessage_create_time(Date message_create_time) {
        this.message_create_time = message_create_time;
    }

    public Date getMessage_modify_time() {
        return message_modify_time;
    }

    public void setMessage_modify_time(Date message_modify_time) {
        this.message_modify_time = message_modify_time;
    }

    public String getGame_detail_id() {
        return game_detail_id;
    }

    public void setGame_detail_id(String game_detail_id) {
        this.game_detail_id = game_detail_id;
    }

    public String getGame_cote_id() {
        return game_cote_id;
    }

    public void setGame_cote_id(String game_cote_id) {
        this.game_cote_id = game_cote_id;
    }

    public String getGame_cote_name() {
        return game_cote_name;
    }

    public void setGame_cote_name(String game_cote_name) {
        this.game_cote_name = game_cote_name;
    }

    public String getGame_cote_state() {
        return game_cote_state;
    }

    public void setGame_cote_state(String game_cote_state) {
        this.game_cote_state = game_cote_state;
    }

    public String getGame_master_id() {
        return game_master_id;
    }

    public void setGame_master_id(String game_master_id) {
        this.game_master_id = game_master_id;
    }

    public String getGame_master_text() {
        return game_master_text;
    }

    public void setGame_master_text(String game_master_text) {
        this.game_master_text = game_master_text;
    }

    public String getGame_master_href() {
        return game_master_href;
    }

    public void setGame_master_href(String game_master_href) {
        this.game_master_href = game_master_href;
    }

    public String getGame_master_type() {
        return game_master_type;
    }

    public void setGame_master_type(String game_master_type) {
        this.game_master_type = game_master_type;
    }

    public String getGame_master_website() {
        return game_master_website;
    }

    public void setGame_master_website(String game_master_website) {
        this.game_master_website = game_master_website;
    }

    public Integer getGame_detail_page() {
        return game_detail_page;
    }

    public void setGame_detail_page(Integer game_detail_page) {
        this.game_detail_page = game_detail_page;
    }

    public Integer getGame_detail_page_index() {
        return game_detail_page_index;
    }

    public void setGame_detail_page_index(Integer game_detail_page_index) {
        this.game_detail_page_index = game_detail_page_index;
    }

    public String getGame_detail_state() {
        return game_detail_state;
    }

    public void setGame_detail_state(String game_detail_state) {
        this.game_detail_state = game_detail_state;
    }

    public String getGame_distence() {
        return game_distence;
    }

    public void setGame_distence(String game_distence) {
        this.game_distence = game_distence;
    }

    public String getGame_ringnum() {
        return game_ringnum;
    }

    public void setGame_ringnum(String game_ringnum) {
        this.game_ringnum = game_ringnum;
    }

    public String getGame_pigowner() {
        return game_pigowner;
    }

    public void setGame_pigowner(String game_pigowner) {
        this.game_pigowner = game_pigowner;
    }

    public String getGame_cometime() {
        return game_cometime;
    }

    public void setGame_cometime(String game_cometime) {
        this.game_cometime = game_cometime;
    }

    public String getGame_cotenum() {
        return game_cotenum;
    }

    public void setGame_cotenum(String game_cotenum) {
        this.game_cotenum = game_cotenum;
    }

    public String getGame_speed() {
        return game_speed;
    }

    public void setGame_speed(String game_speed) {
        this.game_speed = game_speed;
    }

    public Integer getGame_rank() {
        return game_rank;
    }

    public void setGame_rank(Integer game_rank) {
        this.game_rank = game_rank;
    }

    public String getGame_receiver_openid() {
        return game_receiver_openid;
    }

    public void setGame_receiver_openid(String game_receiver_openid) {
        this.game_receiver_openid = game_receiver_openid;
    }

    public String getGame_receiver_nickname() {
        return game_receiver_nickname;
    }

    public void setGame_receiver_nickname(String game_receiver_nickname) {
        this.game_receiver_nickname = game_receiver_nickname;
    }

    public String getGame_receiver_headimgurl() {
        return game_receiver_headimgurl;
    }

    public void setGame_receiver_headimgurl(String game_receiver_headimgurl) {
        this.game_receiver_headimgurl = game_receiver_headimgurl;
    }

    public Date getGame_create_time() {
        return game_create_time;
    }

    public void setGame_create_time(Date game_create_time) {
        this.game_create_time = game_create_time;
    }

    public Date getGame_modify_time() {
        return game_modify_time;
    }

    public void setGame_modify_time(Date game_modify_time) {
        this.game_modify_time = game_modify_time;
    }
}