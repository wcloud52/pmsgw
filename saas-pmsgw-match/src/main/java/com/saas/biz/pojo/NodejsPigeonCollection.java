package com.saas.biz.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class NodejsPigeonCollection {
    /**
     * <pre>
     * id
     * 表字段 : nodejs_pigeon_collection.collection_id
     * </pre>
     */
    private String collection_id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_pigeon_collection.cote_id
     * </pre>
     */
    private String cote_id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_pigeon_collection.cote_name
     * </pre>
     */
    private String cote_name;

    /**
     * <pre>
     * 发送者ID
     * 表字段 : nodejs_pigeon_collection.collection_senderId
     * </pre>
     */
    private String collection_senderId;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_pigeon_collection.collection_senderName
     * </pre>
     */
    private String collection_senderName;

    /**
     * <pre>
     * 发送时间(收鸽日期)
     * 表字段 : nodejs_pigeon_collection.collection_sendTime
     * </pre>
     */
    private String collection_sendTime;

    /**
     * <pre>
     * 足环号
     * 表字段 : nodejs_pigeon_collection.ringnum
     * </pre>
     */
    private String ringnum;

    /**
     * <pre>
     * 鸽主
     * 表字段 : nodejs_pigeon_collection.pigowner
     * </pre>
     */
    private String pigowner;

    /**
     * <pre>
     * 鸽主编号
     * 表字段 : nodejs_pigeon_collection.pigowner_num
     * </pre>
     */
    private String pigowner_num;

    /**
     * <pre>
     * 鸽主手机
     * 表字段 : nodejs_pigeon_collection.pigowner_mobile
     * </pre>
     */
    private String pigowner_mobile;

    /**
     * <pre>
     * 羽色
     * 表字段 : nodejs_pigeon_collection.pigeon_color
     * </pre>
     */
    private String pigeon_color;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_pigeon_collection.pigeon_ext
     * </pre>
     */
    private String pigeon_ext;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_pigeon_collection.fileName
     * </pre>
     */
    private String fileName;

    /**
     * <pre>
     * 1收鸽 2集鸽
     * 表字段 : nodejs_pigeon_collection.typeId
     * </pre>
     */
    private String typeId;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_pigeon_collection.typeName
     * </pre>
     */
    private String typeName;

    /**
     * <pre>
     * 批次号
     * 表字段 : nodejs_pigeon_collection.batchNumber
     * </pre>
     */
    private Long batchNumber;

    /**
     * <pre>
     * 当前批次序号
     * 表字段 : nodejs_pigeon_collection.sortNumber
     * </pre>
     */
    private Integer sortNumber;

    /**
     * <pre>
     * 当前状态
     * 表字段 : nodejs_pigeon_collection.status
     * </pre>
     */
    private Integer status;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_pigeon_collection.create_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_pigeon_collection.modify_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modify_time;

    /**
     * <pre>
     * 匹配用户openid
     * 表字段 : nodejs_pigeon_collection.openid
     * </pre>
     */
    private String openid;

    /**
     * <pre>
     * 匹配用户nickname
     * 表字段 : nodejs_pigeon_collection.nickname
     * </pre>
     */
    private String nickname;

    /**
     * <pre>
     * 匹配用户headimgurl
     * 表字段 : nodejs_pigeon_collection.headimgurl
     * </pre>
     */
    private String headimgurl;

    /**
     * <pre>
     * 获取：id
     * 表字段：nodejs_pigeon_collection.collection_id
     * </pre>
     *
     * @return nodejs_pigeon_collection.collection_id：id
     */
    public String getCollection_id() {
        return collection_id;
    }

    /**
     * <pre>
     * 设置：id
     * 表字段：nodejs_pigeon_collection.collection_id
     * </pre>
     *
     * @param collection_id
     *            nodejs_pigeon_collection.collection_id：id
     */
    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_pigeon_collection.cote_id
     * </pre>
     *
     * @return nodejs_pigeon_collection.cote_id：
     */
    public String getCote_id() {
        return cote_id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_pigeon_collection.cote_id
     * </pre>
     *
     * @param cote_id
     *            nodejs_pigeon_collection.cote_id：
     */
    public void setCote_id(String cote_id) {
        this.cote_id = cote_id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_pigeon_collection.cote_name
     * </pre>
     *
     * @return nodejs_pigeon_collection.cote_name：
     */
    public String getCote_name() {
        return cote_name;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_pigeon_collection.cote_name
     * </pre>
     *
     * @param cote_name
     *            nodejs_pigeon_collection.cote_name：
     */
    public void setCote_name(String cote_name) {
        this.cote_name = cote_name;
    }

    /**
     * <pre>
     * 获取：发送者ID
     * 表字段：nodejs_pigeon_collection.collection_senderId
     * </pre>
     *
     * @return nodejs_pigeon_collection.collection_senderId：发送者ID
     */
    public String getCollection_senderId() {
        return collection_senderId;
    }

    /**
     * <pre>
     * 设置：发送者ID
     * 表字段：nodejs_pigeon_collection.collection_senderId
     * </pre>
     *
     * @param collection_senderId
     *            nodejs_pigeon_collection.collection_senderId：发送者ID
     */
    public void setCollection_senderId(String collection_senderId) {
        this.collection_senderId = collection_senderId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_pigeon_collection.collection_senderName
     * </pre>
     *
     * @return nodejs_pigeon_collection.collection_senderName：
     */
    public String getCollection_senderName() {
        return collection_senderName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_pigeon_collection.collection_senderName
     * </pre>
     *
     * @param collection_senderName
     *            nodejs_pigeon_collection.collection_senderName：
     */
    public void setCollection_senderName(String collection_senderName) {
        this.collection_senderName = collection_senderName;
    }

    /**
     * <pre>
     * 获取：发送时间(收鸽日期)
     * 表字段：nodejs_pigeon_collection.collection_sendTime
     * </pre>
     *
     * @return nodejs_pigeon_collection.collection_sendTime：发送时间(收鸽日期)
     */
    public String getCollection_sendTime() {
        return collection_sendTime;
    }

    /**
     * <pre>
     * 设置：发送时间(收鸽日期)
     * 表字段：nodejs_pigeon_collection.collection_sendTime
     * </pre>
     *
     * @param collection_sendTime
     *            nodejs_pigeon_collection.collection_sendTime：发送时间(收鸽日期)
     */
    public void setCollection_sendTime(String collection_sendTime) {
        this.collection_sendTime = collection_sendTime;
    }

    /**
     * <pre>
     * 获取：足环号
     * 表字段：nodejs_pigeon_collection.ringnum
     * </pre>
     *
     * @return nodejs_pigeon_collection.ringnum：足环号
     */
    public String getRingnum() {
        return ringnum;
    }

    /**
     * <pre>
     * 设置：足环号
     * 表字段：nodejs_pigeon_collection.ringnum
     * </pre>
     *
     * @param ringnum
     *            nodejs_pigeon_collection.ringnum：足环号
     */
    public void setRingnum(String ringnum) {
        this.ringnum = ringnum;
    }

    /**
     * <pre>
     * 获取：鸽主
     * 表字段：nodejs_pigeon_collection.pigowner
     * </pre>
     *
     * @return nodejs_pigeon_collection.pigowner：鸽主
     */
    public String getPigowner() {
        return pigowner;
    }

    /**
     * <pre>
     * 设置：鸽主
     * 表字段：nodejs_pigeon_collection.pigowner
     * </pre>
     *
     * @param pigowner
     *            nodejs_pigeon_collection.pigowner：鸽主
     */
    public void setPigowner(String pigowner) {
        this.pigowner = pigowner;
    }

    /**
     * <pre>
     * 获取：鸽主编号
     * 表字段：nodejs_pigeon_collection.pigowner_num
     * </pre>
     *
     * @return nodejs_pigeon_collection.pigowner_num：鸽主编号
     */
    public String getPigowner_num() {
        return pigowner_num;
    }

    /**
     * <pre>
     * 设置：鸽主编号
     * 表字段：nodejs_pigeon_collection.pigowner_num
     * </pre>
     *
     * @param pigowner_num
     *            nodejs_pigeon_collection.pigowner_num：鸽主编号
     */
    public void setPigowner_num(String pigowner_num) {
        this.pigowner_num = pigowner_num;
    }

    /**
     * <pre>
     * 获取：鸽主手机
     * 表字段：nodejs_pigeon_collection.pigowner_mobile
     * </pre>
     *
     * @return nodejs_pigeon_collection.pigowner_mobile：鸽主手机
     */
    public String getPigowner_mobile() {
        return pigowner_mobile;
    }

    /**
     * <pre>
     * 设置：鸽主手机
     * 表字段：nodejs_pigeon_collection.pigowner_mobile
     * </pre>
     *
     * @param pigowner_mobile
     *            nodejs_pigeon_collection.pigowner_mobile：鸽主手机
     */
    public void setPigowner_mobile(String pigowner_mobile) {
        this.pigowner_mobile = pigowner_mobile;
    }

    /**
     * <pre>
     * 获取：羽色
     * 表字段：nodejs_pigeon_collection.pigeon_color
     * </pre>
     *
     * @return nodejs_pigeon_collection.pigeon_color：羽色
     */
    public String getPigeon_color() {
        return pigeon_color;
    }

    /**
     * <pre>
     * 设置：羽色
     * 表字段：nodejs_pigeon_collection.pigeon_color
     * </pre>
     *
     * @param pigeon_color
     *            nodejs_pigeon_collection.pigeon_color：羽色
     */
    public void setPigeon_color(String pigeon_color) {
        this.pigeon_color = pigeon_color;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_pigeon_collection.pigeon_ext
     * </pre>
     *
     * @return nodejs_pigeon_collection.pigeon_ext：
     */
    public String getPigeon_ext() {
        return pigeon_ext;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_pigeon_collection.pigeon_ext
     * </pre>
     *
     * @param pigeon_ext
     *            nodejs_pigeon_collection.pigeon_ext：
     */
    public void setPigeon_ext(String pigeon_ext) {
        this.pigeon_ext = pigeon_ext;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_pigeon_collection.fileName
     * </pre>
     *
     * @return nodejs_pigeon_collection.fileName：
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_pigeon_collection.fileName
     * </pre>
     *
     * @param fileName
     *            nodejs_pigeon_collection.fileName：
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * <pre>
     * 获取：1收鸽 2集鸽
     * 表字段：nodejs_pigeon_collection.typeId
     * </pre>
     *
     * @return nodejs_pigeon_collection.typeId：1收鸽 2集鸽
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * <pre>
     * 设置：1收鸽 2集鸽
     * 表字段：nodejs_pigeon_collection.typeId
     * </pre>
     *
     * @param typeId
     *            nodejs_pigeon_collection.typeId：1收鸽 2集鸽
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_pigeon_collection.typeName
     * </pre>
     *
     * @return nodejs_pigeon_collection.typeName：
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_pigeon_collection.typeName
     * </pre>
     *
     * @param typeName
     *            nodejs_pigeon_collection.typeName：
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * <pre>
     * 获取：批次号
     * 表字段：nodejs_pigeon_collection.batchNumber
     * </pre>
     *
     * @return nodejs_pigeon_collection.batchNumber：批次号
     */
    public Long getBatchNumber() {
        return batchNumber;
    }

    /**
     * <pre>
     * 设置：批次号
     * 表字段：nodejs_pigeon_collection.batchNumber
     * </pre>
     *
     * @param batchNumber
     *            nodejs_pigeon_collection.batchNumber：批次号
     */
    public void setBatchNumber(Long batchNumber) {
        this.batchNumber = batchNumber;
    }

    /**
     * <pre>
     * 获取：当前批次序号
     * 表字段：nodejs_pigeon_collection.sortNumber
     * </pre>
     *
     * @return nodejs_pigeon_collection.sortNumber：当前批次序号
     */
    public Integer getSortNumber() {
        return sortNumber;
    }

    /**
     * <pre>
     * 设置：当前批次序号
     * 表字段：nodejs_pigeon_collection.sortNumber
     * </pre>
     *
     * @param sortNumber
     *            nodejs_pigeon_collection.sortNumber：当前批次序号
     */
    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    /**
     * <pre>
     * 获取：当前状态
     * 表字段：nodejs_pigeon_collection.status
     * </pre>
     *
     * @return nodejs_pigeon_collection.status：当前状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * <pre>
     * 设置：当前状态
     * 表字段：nodejs_pigeon_collection.status
     * </pre>
     *
     * @param status
     *            nodejs_pigeon_collection.status：当前状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_pigeon_collection.create_time
     * </pre>
     *
     * @return nodejs_pigeon_collection.create_time：
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_pigeon_collection.create_time
     * </pre>
     *
     * @param create_time
     *            nodejs_pigeon_collection.create_time：
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_pigeon_collection.modify_time
     * </pre>
     *
     * @return nodejs_pigeon_collection.modify_time：
     */
    public Date getModify_time() {
        return modify_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_pigeon_collection.modify_time
     * </pre>
     *
     * @param modify_time
     *            nodejs_pigeon_collection.modify_time：
     */
    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    /**
     * <pre>
     * 获取：匹配用户openid
     * 表字段：nodejs_pigeon_collection.openid
     * </pre>
     *
     * @return nodejs_pigeon_collection.openid：匹配用户openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * <pre>
     * 设置：匹配用户openid
     * 表字段：nodejs_pigeon_collection.openid
     * </pre>
     *
     * @param openid
     *            nodejs_pigeon_collection.openid：匹配用户openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * <pre>
     * 获取：匹配用户nickname
     * 表字段：nodejs_pigeon_collection.nickname
     * </pre>
     *
     * @return nodejs_pigeon_collection.nickname：匹配用户nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * <pre>
     * 设置：匹配用户nickname
     * 表字段：nodejs_pigeon_collection.nickname
     * </pre>
     *
     * @param nickname
     *            nodejs_pigeon_collection.nickname：匹配用户nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * <pre>
     * 获取：匹配用户headimgurl
     * 表字段：nodejs_pigeon_collection.headimgurl
     * </pre>
     *
     * @return nodejs_pigeon_collection.headimgurl：匹配用户headimgurl
     */
    public String getHeadimgurl() {
        return headimgurl;
    }

    /**
     * <pre>
     * 设置：匹配用户headimgurl
     * 表字段：nodejs_pigeon_collection.headimgurl
     * </pre>
     *
     * @param headimgurl
     *            nodejs_pigeon_collection.headimgurl：匹配用户headimgurl
     */
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
}