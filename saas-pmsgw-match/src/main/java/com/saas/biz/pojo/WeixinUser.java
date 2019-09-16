package com.saas.biz.pojo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class WeixinUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modify_time;

    private String city;

    private String country;

    private Long groupid;

    private String headimgurl;

    private String language;

    private String nickname;

    private String openid;

    private String province;

    private String remark;

    private Integer sex;

    private Integer subscribe;

    private Long subscribe_time;

    private String unionid;

    private String bind_type;

    private String bind_tel;

    private String bind_name;

    private String bind_loft;

    private String bind_address;

    private String bind_game;

    private String bind_address_prov;

    private String bind_address_city;

    private String bind_address_dist;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date bind_time;

    private String club_bind_tel;

    private String club_bind_name;

    private String club_bind_loft;

    private String club_bind_address;

    private String club_bind_game;

    private String club_bind_address_prov;

    private String club_bind_address_city;

    private String club_bind_address_dist;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date club_bind_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Long getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(Long subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getBind_type() {
        return bind_type;
    }

    public void setBind_type(String bind_type) {
        this.bind_type = bind_type;
    }

    public String getBind_tel() {
        return bind_tel;
    }

    public void setBind_tel(String bind_tel) {
        this.bind_tel = bind_tel;
    }

    public String getBind_name() {
        return bind_name;
    }

    public void setBind_name(String bind_name) {
        this.bind_name = bind_name;
    }

    public String getBind_loft() {
        return bind_loft;
    }

    public void setBind_loft(String bind_loft) {
        this.bind_loft = bind_loft;
    }

    public String getBind_address() {
        return bind_address;
    }

    public void setBind_address(String bind_address) {
        this.bind_address = bind_address;
    }

    public String getBind_game() {
        return bind_game;
    }

    public void setBind_game(String bind_game) {
        this.bind_game = bind_game;
    }

    public String getBind_address_prov() {
        return bind_address_prov;
    }

    public void setBind_address_prov(String bind_address_prov) {
        this.bind_address_prov = bind_address_prov;
    }

    public String getBind_address_city() {
        return bind_address_city;
    }

    public void setBind_address_city(String bind_address_city) {
        this.bind_address_city = bind_address_city;
    }

    public String getBind_address_dist() {
        return bind_address_dist;
    }

    public void setBind_address_dist(String bind_address_dist) {
        this.bind_address_dist = bind_address_dist;
    }

    public Date getBind_time() {
        return bind_time;
    }

    public void setBind_time(Date bind_time) {
        this.bind_time = bind_time;
    }

    public String getClub_bind_tel() {
        return club_bind_tel;
    }

    public void setClub_bind_tel(String club_bind_tel) {
        this.club_bind_tel = club_bind_tel;
    }

    public String getClub_bind_name() {
        return club_bind_name;
    }

    public void setClub_bind_name(String club_bind_name) {
        this.club_bind_name = club_bind_name;
    }

    public String getClub_bind_loft() {
        return club_bind_loft;
    }

    public void setClub_bind_loft(String club_bind_loft) {
        this.club_bind_loft = club_bind_loft;
    }

    public String getClub_bind_address() {
        return club_bind_address;
    }

    public void setClub_bind_address(String club_bind_address) {
        this.club_bind_address = club_bind_address;
    }

    public String getClub_bind_game() {
        return club_bind_game;
    }

    public void setClub_bind_game(String club_bind_game) {
        this.club_bind_game = club_bind_game;
    }

    public String getClub_bind_address_prov() {
        return club_bind_address_prov;
    }

    public void setClub_bind_address_prov(String club_bind_address_prov) {
        this.club_bind_address_prov = club_bind_address_prov;
    }

    public String getClub_bind_address_city() {
        return club_bind_address_city;
    }

    public void setClub_bind_address_city(String club_bind_address_city) {
        this.club_bind_address_city = club_bind_address_city;
    }

    public String getClub_bind_address_dist() {
        return club_bind_address_dist;
    }

    public void setClub_bind_address_dist(String club_bind_address_dist) {
        this.club_bind_address_dist = club_bind_address_dist;
    }

    public Date getClub_bind_time() {
        return club_bind_time;
    }

    public void setClub_bind_time(Date club_bind_time) {
        this.club_bind_time = club_bind_time;
    }
}