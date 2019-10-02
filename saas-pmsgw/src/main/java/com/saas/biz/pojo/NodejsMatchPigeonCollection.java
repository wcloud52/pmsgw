package com.saas.biz.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class NodejsMatchPigeonCollection {
    private String tel;
    /**
     * <pre>
     * id
     * 表字段 : nodejs_match_pigeon_collection.collection_id
     * </pre>
     */
    private String collection_id;

    /**
     * <pre>
     * 指定比赛id
     * 表字段 : nodejs_match_pigeon_collection.match_id
     * </pre>
     */
    private String match_id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_match_pigeon_collection.cote_id
     * </pre>
     */
    private String cote_id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_match_pigeon_collection.cote_name
     * </pre>
     */
    private String cote_name;

    /**
     * <pre>
     * 发送者ID
     * 表字段 : nodejs_match_pigeon_collection.collection_senderId
     * </pre>
     */
    private String collection_senderId;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_match_pigeon_collection.collection_senderName
     * </pre>
     */
    private String collection_senderName;

    /**
     * <pre>
     * 发送时间(收鸽日期)
     * 表字段 : nodejs_match_pigeon_collection.collection_sendTime
     * </pre>
     */
    private String collection_sendTime;

    /**
     * <pre>
     * 足环号
     * 表字段 : nodejs_match_pigeon_collection.ringnum
     * </pre>
     */
    private String ringnum;

    /**
     * <pre>
     * 鸽主
     * 表字段 : nodejs_match_pigeon_collection.pigowner
     * </pre>
     */
    private String pigowner;

    /**
     * <pre>
     * 鸽主编号
     * 表字段 : nodejs_match_pigeon_collection.pigowner_num
     * </pre>
     */
    private String pigowner_num;

    /**
     * <pre>
     * 羽色
     * 表字段 : nodejs_match_pigeon_collection.pigeon_color
     * </pre>
     */
    private String pigeon_color;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_match_pigeon_collection.pigeon_ext
     * </pre>
     */
    private String pigeon_ext;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_match_pigeon_collection.fileName
     * </pre>
     */
    private String fileName;

    /**
     * <pre>
     * 3指定比赛集鸽
     * 表字段 : nodejs_match_pigeon_collection.typeId
     * </pre>
     */
    private String typeId;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_match_pigeon_collection.typeName
     * </pre>
     */
    private String typeName;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_match_pigeon_collection.create_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_match_pigeon_collection.modify_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modify_time;

    /**
     * <pre>
     * 获取：id
     * 表字段：nodejs_match_pigeon_collection.collection_id
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.collection_id：id
     */
    public String getCollection_id() {
        return collection_id;
    }

    /**
     * <pre>
     * 设置：id
     * 表字段：nodejs_match_pigeon_collection.collection_id
     * </pre>
     *
     * @param collection_id
     *            nodejs_match_pigeon_collection.collection_id：id
     */
    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }

    /**
     * <pre>
     * 获取：指定比赛id
     * 表字段：nodejs_match_pigeon_collection.match_id
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.match_id：指定比赛id
     */
    public String getMatch_id() {
        return match_id;
    }

    /**
     * <pre>
     * 设置：指定比赛id
     * 表字段：nodejs_match_pigeon_collection.match_id
     * </pre>
     *
     * @param match_id
     *            nodejs_match_pigeon_collection.match_id：指定比赛id
     */
    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_match_pigeon_collection.cote_id
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.cote_id：
     */
    public String getCote_id() {
        return cote_id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_match_pigeon_collection.cote_id
     * </pre>
     *
     * @param cote_id
     *            nodejs_match_pigeon_collection.cote_id：
     */
    public void setCote_id(String cote_id) {
        this.cote_id = cote_id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_match_pigeon_collection.cote_name
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.cote_name：
     */
    public String getCote_name() {
        return cote_name;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_match_pigeon_collection.cote_name
     * </pre>
     *
     * @param cote_name
     *            nodejs_match_pigeon_collection.cote_name：
     */
    public void setCote_name(String cote_name) {
        this.cote_name = cote_name;
    }

    /**
     * <pre>
     * 获取：发送者ID
     * 表字段：nodejs_match_pigeon_collection.collection_senderId
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.collection_senderId：发送者ID
     */
    public String getCollection_senderId() {
        return collection_senderId;
    }

    /**
     * <pre>
     * 设置：发送者ID
     * 表字段：nodejs_match_pigeon_collection.collection_senderId
     * </pre>
     *
     * @param collection_senderId
     *            nodejs_match_pigeon_collection.collection_senderId：发送者ID
     */
    public void setCollection_senderId(String collection_senderId) {
        this.collection_senderId = collection_senderId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_match_pigeon_collection.collection_senderName
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.collection_senderName：
     */
    public String getCollection_senderName() {
        return collection_senderName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_match_pigeon_collection.collection_senderName
     * </pre>
     *
     * @param collection_senderName
     *            nodejs_match_pigeon_collection.collection_senderName：
     */
    public void setCollection_senderName(String collection_senderName) {
        this.collection_senderName = collection_senderName;
    }

    /**
     * <pre>
     * 获取：发送时间(收鸽日期)
     * 表字段：nodejs_match_pigeon_collection.collection_sendTime
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.collection_sendTime：发送时间(收鸽日期)
     */
    public String getCollection_sendTime() {
        return collection_sendTime;
    }

    /**
     * <pre>
     * 设置：发送时间(收鸽日期)
     * 表字段：nodejs_match_pigeon_collection.collection_sendTime
     * </pre>
     *
     * @param collection_sendTime
     *            nodejs_match_pigeon_collection.collection_sendTime：发送时间(收鸽日期)
     */
    public void setCollection_sendTime(String collection_sendTime) {
        this.collection_sendTime = collection_sendTime;
    }

    /**
     * <pre>
     * 获取：足环号
     * 表字段：nodejs_match_pigeon_collection.ringnum
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.ringnum：足环号
     */
    public String getRingnum() {
        return ringnum;
    }

    /**
     * <pre>
     * 设置：足环号
     * 表字段：nodejs_match_pigeon_collection.ringnum
     * </pre>
     *
     * @param ringnum
     *            nodejs_match_pigeon_collection.ringnum：足环号
     */
    public void setRingnum(String ringnum) {
        this.ringnum = ringnum;
    }

    /**
     * <pre>
     * 获取：鸽主
     * 表字段：nodejs_match_pigeon_collection.pigowner
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.pigowner：鸽主
     */
    public String getPigowner() {
        return pigowner;
    }

    /**
     * <pre>
     * 设置：鸽主
     * 表字段：nodejs_match_pigeon_collection.pigowner
     * </pre>
     *
     * @param pigowner
     *            nodejs_match_pigeon_collection.pigowner：鸽主
     */
    public void setPigowner(String pigowner) {
        this.pigowner = pigowner;
    }

    /**
     * <pre>
     * 获取：鸽主编号
     * 表字段：nodejs_match_pigeon_collection.pigowner_num
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.pigowner_num：鸽主编号
     */
    public String getPigowner_num() {
        return pigowner_num;
    }

    /**
     * <pre>
     * 设置：鸽主编号
     * 表字段：nodejs_match_pigeon_collection.pigowner_num
     * </pre>
     *
     * @param pigowner_num
     *            nodejs_match_pigeon_collection.pigowner_num：鸽主编号
     */
    public void setPigowner_num(String pigowner_num) {
        this.pigowner_num = pigowner_num;
    }

    /**
     * <pre>
     * 获取：羽色
     * 表字段：nodejs_match_pigeon_collection.pigeon_color
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.pigeon_color：羽色
     */
    public String getPigeon_color() {
        return pigeon_color;
    }

    /**
     * <pre>
     * 设置：羽色
     * 表字段：nodejs_match_pigeon_collection.pigeon_color
     * </pre>
     *
     * @param pigeon_color
     *            nodejs_match_pigeon_collection.pigeon_color：羽色
     */
    public void setPigeon_color(String pigeon_color) {
        this.pigeon_color = pigeon_color;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_match_pigeon_collection.pigeon_ext
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.pigeon_ext：
     */
    public String getPigeon_ext() {
        return pigeon_ext;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_match_pigeon_collection.pigeon_ext
     * </pre>
     *
     * @param pigeon_ext
     *            nodejs_match_pigeon_collection.pigeon_ext：
     */
    public void setPigeon_ext(String pigeon_ext) {
        this.pigeon_ext = pigeon_ext;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_match_pigeon_collection.fileName
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.fileName：
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_match_pigeon_collection.fileName
     * </pre>
     *
     * @param fileName
     *            nodejs_match_pigeon_collection.fileName：
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * <pre>
     * 获取：3指定比赛集鸽
     * 表字段：nodejs_match_pigeon_collection.typeId
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.typeId：3指定比赛集鸽
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * <pre>
     * 设置：3指定比赛集鸽
     * 表字段：nodejs_match_pigeon_collection.typeId
     * </pre>
     *
     * @param typeId
     *            nodejs_match_pigeon_collection.typeId：3指定比赛集鸽
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_match_pigeon_collection.typeName
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.typeName：
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_match_pigeon_collection.typeName
     * </pre>
     *
     * @param typeName
     *            nodejs_match_pigeon_collection.typeName：
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_match_pigeon_collection.create_time
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.create_time：
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_match_pigeon_collection.create_time
     * </pre>
     *
     * @param create_time
     *            nodejs_match_pigeon_collection.create_time：
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_match_pigeon_collection.modify_time
     * </pre>
     *
     * @return nodejs_match_pigeon_collection.modify_time：
     */
    public Date getModify_time() {
        return modify_time;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_match_pigeon_collection.modify_time
     * </pre>
     *
     * @param modify_time
     *            nodejs_match_pigeon_collection.modify_time：
     */
    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }


}