package com.saas.biz.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class NodejsCrawlerCote {
    /**
     * <pre>
     * 
     * 表字段 : nodejs_crawler_cote.cote_id
     * </pre>
     */
    private String cote_id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_crawler_cote.cote_name
     * </pre>
     */
    private String cote_name;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_crawler_cote.cote_website
     * </pre>
     */
    private String cote_website;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_crawler_cote.cote_state
     * </pre>
     */
    private String cote_state;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_crawler_cote.create_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_crawler_cote.modify_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modify_time;

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_crawler_cote.cote_id
     * </pre>
     *
     * @return nodejs_crawler_cote.cote_id：
     */
    public String getCote_id() {
        return cote_id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_crawler_cote.cote_id
     * </pre>
     *
     * @param cote_id
     *            nodejs_crawler_cote.cote_id：
     */
    public void setCote_id(String cote_id) {
        this.cote_id = cote_id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_crawler_cote.cote_name
     * </pre>
     *
     * @return nodejs_crawler_cote.cote_name：
     */
    public String getCote_name() {
        return cote_name;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_crawler_cote.cote_name
     * </pre>
     *
     * @param cote_name
     *            nodejs_crawler_cote.cote_name：
     */
    public void setCote_name(String cote_name) {
        this.cote_name = cote_name;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_crawler_cote.cote_website
     * </pre>
     *
     * @return nodejs_crawler_cote.cote_website：
     */
    public String getCote_website() {
        return cote_website;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_crawler_cote.cote_website
     * </pre>
     *
     * @param cote_website
     *            nodejs_crawler_cote.cote_website：
     */
    public void setCote_website(String cote_website) {
        this.cote_website = cote_website;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_crawler_cote.cote_state
     * </pre>
     *
     * @return nodejs_crawler_cote.cote_state：
     */
    public String getCote_state() {
        return cote_state;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_crawler_cote.cote_state
     * </pre>
     *
     * @param cote_state
     *            nodejs_crawler_cote.cote_state：
     */
    public void setCote_state(String cote_state) {
        this.cote_state = cote_state;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_crawler_cote.create_time
     * </pre>
     *
     * @return nodejs_crawler_cote.create_time：
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_crawler_cote.create_time
     * </pre>
     *
     * @param create_time
     *            nodejs_crawler_cote.create_time：
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_crawler_cote.modify_time
     * </pre>
     *
     * @return nodejs_crawler_cote.modify_time：
     */
    public Date getModify_time() {
        return modify_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_crawler_cote.modify_time
     * </pre>
     *
     * @param modify_time
     *            nodejs_crawler_cote.modify_time：
     */
    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }
}