package com.saas.biz.pojo;

import java.util.Date;

public class NodejsMobileUser {
    /**
     * <pre>
     * CONCAT(cote_id,'-',mobile,'-',to_base64(pigowner))
     * 表字段 : nodejs_mobile_user.id
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_mobile_user.pigowner
     * </pre>
     */
    private String pigowner;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_mobile_user.mobile
     * </pre>
     */
    private String mobile;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_mobile_user.cote_id
     * </pre>
     */
    private String cote_id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_mobile_user.cote_name
     * </pre>
     */
    private String cote_name;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_mobile_user.short_cote_name
     * </pre>
     */
    private String short_cote_name;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_mobile_user.state
     * </pre>
     */
    private String state;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_mobile_user.fileName
     * </pre>
     */
    private String fileName;

    /**
     * <pre>
     * 批次号
     * 表字段 : nodejs_mobile_user.batchNumber
     * </pre>
     */
    private Long batchNumber;

    /**
     * <pre>
     * 当前批次序号
     * 表字段 : nodejs_mobile_user.sortNumber
     * </pre>
     */
    private Integer sortNumber;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_mobile_user.create_time
     * </pre>
     */
    private Date create_time;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_mobile_user.modify_time
     * </pre>
     */
    private Date modify_time;

    /**
     * <pre>
     * 获取：CONCAT(cote_id,'-',mobile,'-',to_base64(pigowner))
     * 表字段：nodejs_mobile_user.id
     * </pre>
     *
     * @return nodejs_mobile_user.id：CONCAT(cote_id,'-',mobile,'-',to_base64(pigowner))
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：CONCAT(cote_id,'-',mobile,'-',to_base64(pigowner))
     * 表字段：nodejs_mobile_user.id
     * </pre>
     *
     * @param id
     *            nodejs_mobile_user.id：CONCAT(cote_id,'-',mobile,'-',to_base64(pigowner))
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_mobile_user.pigowner
     * </pre>
     *
     * @return nodejs_mobile_user.pigowner：
     */
    public String getPigowner() {
        return pigowner;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_mobile_user.pigowner
     * </pre>
     *
     * @param pigowner
     *            nodejs_mobile_user.pigowner：
     */
    public void setPigowner(String pigowner) {
        this.pigowner = pigowner;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_mobile_user.mobile
     * </pre>
     *
     * @return nodejs_mobile_user.mobile：
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_mobile_user.mobile
     * </pre>
     *
     * @param mobile
     *            nodejs_mobile_user.mobile：
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_mobile_user.cote_id
     * </pre>
     *
     * @return nodejs_mobile_user.cote_id：
     */
    public String getCote_id() {
        return cote_id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_mobile_user.cote_id
     * </pre>
     *
     * @param cote_id
     *            nodejs_mobile_user.cote_id：
     */
    public void setCote_id(String cote_id) {
        this.cote_id = cote_id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_mobile_user.cote_name
     * </pre>
     *
     * @return nodejs_mobile_user.cote_name：
     */
    public String getCote_name() {
        return cote_name;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_mobile_user.cote_name
     * </pre>
     *
     * @param cote_name
     *            nodejs_mobile_user.cote_name：
     */
    public void setCote_name(String cote_name) {
        this.cote_name = cote_name;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_mobile_user.short_cote_name
     * </pre>
     *
     * @return nodejs_mobile_user.short_cote_name：
     */
    public String getShort_cote_name() {
        return short_cote_name;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_mobile_user.short_cote_name
     * </pre>
     *
     * @param short_cote_name
     *            nodejs_mobile_user.short_cote_name：
     */
    public void setShort_cote_name(String short_cote_name) {
        this.short_cote_name = short_cote_name;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_mobile_user.state
     * </pre>
     *
     * @return nodejs_mobile_user.state：
     */
    public String getState() {
        return state;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_mobile_user.state
     * </pre>
     *
     * @param state
     *            nodejs_mobile_user.state：
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_mobile_user.fileName
     * </pre>
     *
     * @return nodejs_mobile_user.fileName：
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_mobile_user.fileName
     * </pre>
     *
     * @param fileName
     *            nodejs_mobile_user.fileName：
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * <pre>
     * 获取：批次号
     * 表字段：nodejs_mobile_user.batchNumber
     * </pre>
     *
     * @return nodejs_mobile_user.batchNumber：批次号
     */
    public Long getBatchNumber() {
        return batchNumber;
    }

    /**
     * <pre>
     * 设置：批次号
     * 表字段：nodejs_mobile_user.batchNumber
     * </pre>
     *
     * @param batchNumber
     *            nodejs_mobile_user.batchNumber：批次号
     */
    public void setBatchNumber(Long batchNumber) {
        this.batchNumber = batchNumber;
    }

    /**
     * <pre>
     * 获取：当前批次序号
     * 表字段：nodejs_mobile_user.sortNumber
     * </pre>
     *
     * @return nodejs_mobile_user.sortNumber：当前批次序号
     */
    public Integer getSortNumber() {
        return sortNumber;
    }

    /**
     * <pre>
     * 设置：当前批次序号
     * 表字段：nodejs_mobile_user.sortNumber
     * </pre>
     *
     * @param sortNumber
     *            nodejs_mobile_user.sortNumber：当前批次序号
     */
    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_mobile_user.create_time
     * </pre>
     *
     * @return nodejs_mobile_user.create_time：
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_mobile_user.create_time
     * </pre>
     *
     * @param create_time
     *            nodejs_mobile_user.create_time：
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_mobile_user.modify_time
     * </pre>
     *
     * @return nodejs_mobile_user.modify_time：
     */
    public Date getModify_time() {
        return modify_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_mobile_user.modify_time
     * </pre>
     *
     * @param modify_time
     *            nodejs_mobile_user.modify_time：
     */
    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }
}