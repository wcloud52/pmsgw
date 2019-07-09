package com.saas.biz.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class NodejsNews {
    /**
     * <pre>
     * 消息id
     * 表字段 : nodejs_news.news_id
     * </pre>
     */
    private String news_id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_news.cote_id
     * </pre>
     */
    private String cote_id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_news.cote_name
     * </pre>
     */
    private String cote_name;

    /**
     * <pre>
     * 发送者ID
     * 表字段 : nodejs_news.news_senderId
     * </pre>
     */
    private String news_senderId;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_news.news_senderName
     * </pre>
     */
    private String news_senderName;

    /**
     * <pre>
     * 发送时间
     * 表字段 : nodejs_news.news_sendTime
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date news_sendTime;

    /**
     * <pre>
     * 消息imgHref
     * 表字段 : nodejs_news.news_imgHref
     * </pre>
     */
    private String news_imgHref;

    /**
     * <pre>
     * 消息href
     * 表字段 : nodejs_news.news_href
     * </pre>
     */
    private String news_href;

    /**
     * <pre>
     * 消息类型
     * 表字段 : nodejs_news.news_type
     * </pre>
     */
    private String news_type;

    /**
     * <pre>
     * 标题
     * 表字段 : nodejs_news.news_title
     * </pre>
     */
    private String news_title;

    /**
     * <pre>
     * 描述
     * 表字段 : nodejs_news.news_desc
     * </pre>
     */
    private String news_desc;

    /**
     * <pre>
     * 内容
     * 表字段 : nodejs_news.news_text
     * </pre>
     */
    private String news_text;

    /**
     * <pre>
     * 状态
     * 表字段 : nodejs_news.news_status
     * </pre>
     */
    private Integer news_status;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_news.create_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_news.modify_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modify_time;

    /**
     * <pre>
     * 获取：消息id
     * 表字段：nodejs_news.news_id
     * </pre>
     *
     * @return nodejs_news.news_id：消息id
     */
    public String getNews_id() {
        return news_id;
    }

    /**
     * <pre>
     * 设置：消息id
     * 表字段：nodejs_news.news_id
     * </pre>
     *
     * @param news_id
     *            nodejs_news.news_id：消息id
     */
    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_news.cote_id
     * </pre>
     *
     * @return nodejs_news.cote_id：
     */
    public String getCote_id() {
        return cote_id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_news.cote_id
     * </pre>
     *
     * @param cote_id
     *            nodejs_news.cote_id：
     */
    public void setCote_id(String cote_id) {
        this.cote_id = cote_id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_news.cote_name
     * </pre>
     *
     * @return nodejs_news.cote_name：
     */
    public String getCote_name() {
        return cote_name;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_news.cote_name
     * </pre>
     *
     * @param cote_name
     *            nodejs_news.cote_name：
     */
    public void setCote_name(String cote_name) {
        this.cote_name = cote_name;
    }

    /**
     * <pre>
     * 获取：发送者ID
     * 表字段：nodejs_news.news_senderId
     * </pre>
     *
     * @return nodejs_news.news_senderId：发送者ID
     */
    public String getNews_senderId() {
        return news_senderId;
    }

    /**
     * <pre>
     * 设置：发送者ID
     * 表字段：nodejs_news.news_senderId
     * </pre>
     *
     * @param news_senderId
     *            nodejs_news.news_senderId：发送者ID
     */
    public void setNews_senderId(String news_senderId) {
        this.news_senderId = news_senderId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_news.news_senderName
     * </pre>
     *
     * @return nodejs_news.news_senderName：
     */
    public String getNews_senderName() {
        return news_senderName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_news.news_senderName
     * </pre>
     *
     * @param news_senderName
     *            nodejs_news.news_senderName：
     */
    public void setNews_senderName(String news_senderName) {
        this.news_senderName = news_senderName;
    }

    /**
     * <pre>
     * 获取：发送时间
     * 表字段：nodejs_news.news_sendTime
     * </pre>
     *
     * @return nodejs_news.news_sendTime：发送时间
     */
    public Date getNews_sendTime() {
        return news_sendTime;
    }

    /**
     * <pre>
     * 设置：发送时间
     * 表字段：nodejs_news.news_sendTime
     * </pre>
     *
     * @param news_sendTime
     *            nodejs_news.news_sendTime：发送时间
     */
    public void setNews_sendTime(Date news_sendTime) {
        this.news_sendTime = news_sendTime;
    }

    /**
     * <pre>
     * 获取：消息imgHref
     * 表字段：nodejs_news.news_imgHref
     * </pre>
     *
     * @return nodejs_news.news_imgHref：消息imgHref
     */
    public String getNews_imgHref() {
        return news_imgHref;
    }

    /**
     * <pre>
     * 设置：消息imgHref
     * 表字段：nodejs_news.news_imgHref
     * </pre>
     *
     * @param news_imgHref
     *            nodejs_news.news_imgHref：消息imgHref
     */
    public void setNews_imgHref(String news_imgHref) {
        this.news_imgHref = news_imgHref;
    }

    /**
     * <pre>
     * 获取：消息href
     * 表字段：nodejs_news.news_href
     * </pre>
     *
     * @return nodejs_news.news_href：消息href
     */
    public String getNews_href() {
        return news_href;
    }

    /**
     * <pre>
     * 设置：消息href
     * 表字段：nodejs_news.news_href
     * </pre>
     *
     * @param news_href
     *            nodejs_news.news_href：消息href
     */
    public void setNews_href(String news_href) {
        this.news_href = news_href;
    }

    /**
     * <pre>
     * 获取：消息类型
     * 表字段：nodejs_news.news_type
     * </pre>
     *
     * @return nodejs_news.news_type：消息类型
     */
    public String getNews_type() {
        return news_type;
    }

    /**
     * <pre>
     * 设置：消息类型
     * 表字段：nodejs_news.news_type
     * </pre>
     *
     * @param news_type
     *            nodejs_news.news_type：消息类型
     */
    public void setNews_type(String news_type) {
        this.news_type = news_type;
    }

    /**
     * <pre>
     * 获取：标题
     * 表字段：nodejs_news.news_title
     * </pre>
     *
     * @return nodejs_news.news_title：标题
     */
    public String getNews_title() {
        return news_title;
    }

    /**
     * <pre>
     * 设置：标题
     * 表字段：nodejs_news.news_title
     * </pre>
     *
     * @param news_title
     *            nodejs_news.news_title：标题
     */
    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    /**
     * <pre>
     * 获取：描述
     * 表字段：nodejs_news.news_desc
     * </pre>
     *
     * @return nodejs_news.news_desc：描述
     */
    public String getNews_desc() {
        return news_desc;
    }

    /**
     * <pre>
     * 设置：描述
     * 表字段：nodejs_news.news_desc
     * </pre>
     *
     * @param news_desc
     *            nodejs_news.news_desc：描述
     */
    public void setNews_desc(String news_desc) {
        this.news_desc = news_desc;
    }

    /**
     * <pre>
     * 获取：内容
     * 表字段：nodejs_news.news_text
     * </pre>
     *
     * @return nodejs_news.news_text：内容
     */
    public String getNews_text() {
        return news_text;
    }

    /**
     * <pre>
     * 设置：内容
     * 表字段：nodejs_news.news_text
     * </pre>
     *
     * @param news_text
     *            nodejs_news.news_text：内容
     */
    public void setNews_text(String news_text) {
        this.news_text = news_text;
    }

    /**
     * <pre>
     * 获取：状态
     * 表字段：nodejs_news.news_status
     * </pre>
     *
     * @return nodejs_news.news_status：状态
     */
    public Integer getNews_status() {
        return news_status;
    }

    /**
     * <pre>
     * 设置：状态
     * 表字段：nodejs_news.news_status
     * </pre>
     *
     * @param news_status
     *            nodejs_news.news_status：状态
     */
    public void setNews_status(Integer news_status) {
        this.news_status = news_status;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_news.create_time
     * </pre>
     *
     * @return nodejs_news.create_time：
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_news.create_time
     * </pre>
     *
     * @param create_time
     *            nodejs_news.create_time：
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_news.modify_time
     * </pre>
     *
     * @return nodejs_news.modify_time：
     */
    public Date getModify_time() {
        return modify_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_news.modify_time
     * </pre>
     *
     * @param modify_time
     *            nodejs_news.modify_time：
     */
    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }
}