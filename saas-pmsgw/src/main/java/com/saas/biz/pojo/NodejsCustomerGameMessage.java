package com.saas.biz.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class NodejsCustomerGameMessage {
    /**
     * <pre>
     * 消息id
     * 表字段 : nodejs_customer_game_message.message_id
     * </pre>
     */
    private String message_id;

    /**
     * <pre>
     * 发送者ID
     * 表字段 : nodejs_customer_game_message.message_senderId
     * </pre>
     */
    private String message_senderId;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_customer_game_message.message_senderName
     * </pre>
     */
    private String message_senderName;

    /**
     * <pre>
     * 接收者ID
     * 表字段 : nodejs_customer_game_message.message_receiverId
     * </pre>
     */
    private String message_receiverId;

    /**
     * <pre>
     * 接收者名称
     * 表字段 : nodejs_customer_game_message.message_receiverName
     * </pre>
     */
    private String message_receiverName;

    /**
     * <pre>
     * 发送时间
     * 表字段 : nodejs_customer_game_message.message_sendTime
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date message_sendTime;

    /**
     * <pre>
     * 消息类型
     * 表字段 : nodejs_customer_game_message.message_type
     * </pre>
     */
    private String message_type;

    /**
     * <pre>
     * 标题
     * 表字段 : nodejs_customer_game_message.message_title
     * </pre>
     */
    private String message_title;

    /**
     * <pre>
     * 内容
     * 表字段 : nodejs_customer_game_message.message_text
     * </pre>
     */
    private String message_text;

    /**
     * <pre>
     * 状态
     * 表字段 : nodejs_customer_game_message.message_status
     * </pre>
     */
    private Integer message_status;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_customer_game_message.message_create_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date message_create_time;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_customer_game_message.message_modify_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date message_modify_time;

    /**
     * <pre>
     * 公棚id
     * 表字段 : nodejs_customer_game_message.game_cote_id
     * </pre>
     */
    private String game_cote_id;

    /**
     * <pre>
     * 公棚名称
     * 表字段 : nodejs_customer_game_message.game_cote_name
     * </pre>
     */
    private String game_cote_name;

    /**
     * <pre>
     * 公棚状态
     * 表字段 : nodejs_customer_game_message.game_cote_state
     * </pre>
     */
    private String game_cote_state;

    /**
     * <pre>
     * 比赛id
     * 表字段 : nodejs_customer_game_message.game_master_id
     * </pre>
     */
    private String game_master_id;

    /**
     * <pre>
     * 比赛标题
     * 表字段 : nodejs_customer_game_message.game_master_text
     * </pre>
     */
    private String game_master_text;

    /**
     * <pre>
     * 比赛url
     * 表字段 : nodejs_customer_game_message.game_master_href
     * </pre>
     */
    private String game_master_href;

    /**
     * <pre>
     * 比赛类型
     * 表字段 : nodejs_customer_game_message.game_master_type
     * </pre>
     */
    private String game_master_type;

    /**
     * <pre>
     * 比赛站点
     * 表字段 : nodejs_customer_game_message.game_master_website
     * </pre>
     */
    private String game_master_website;

    /**
     * <pre>
     * 比赛日期
     * 表字段 : nodejs_customer_game_message.game_master_date
     * </pre>
     */
    private String game_master_date;

    /**
     * <pre>
     * 状态(是否发送信息)
     * 表字段 : nodejs_customer_game_message.game_master_state
     * </pre>
     */
    private String game_master_state;

    /**
     * <pre>
     * 鸽主openid
     * 表字段 : nodejs_customer_game_message.game_receiver_openid
     * </pre>
     */
    private String game_receiver_openid;

    /**
     * <pre>
     * 鸽主nickname
     * 表字段 : nodejs_customer_game_message.game_receiver_nickname
     * </pre>
     */
    private String game_receiver_nickname;

    /**
     * <pre>
     * 鸽主headimgurl
     * 表字段 : nodejs_customer_game_message.game_receiver_headimgurl
     * </pre>
     */
    private String game_receiver_headimgurl;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_customer_game_message.game_create_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date game_create_time;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_customer_game_message.game_modify_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date game_modify_time;

    /**
     * <pre>
     * 获取：消息id
     * 表字段：nodejs_customer_game_message.message_id
     * </pre>
     *
     * @return nodejs_customer_game_message.message_id：消息id
     */
    public String getMessage_id() {
        return message_id;
    }

    /**
     * <pre>
     * 设置：消息id
     * 表字段：nodejs_customer_game_message.message_id
     * </pre>
     *
     * @param message_id
     *            nodejs_customer_game_message.message_id：消息id
     */
    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    /**
     * <pre>
     * 获取：发送者ID
     * 表字段：nodejs_customer_game_message.message_senderId
     * </pre>
     *
     * @return nodejs_customer_game_message.message_senderId：发送者ID
     */
    public String getMessage_senderId() {
        return message_senderId;
    }

    /**
     * <pre>
     * 设置：发送者ID
     * 表字段：nodejs_customer_game_message.message_senderId
     * </pre>
     *
     * @param message_senderId
     *            nodejs_customer_game_message.message_senderId：发送者ID
     */
    public void setMessage_senderId(String message_senderId) {
        this.message_senderId = message_senderId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_customer_game_message.message_senderName
     * </pre>
     *
     * @return nodejs_customer_game_message.message_senderName：
     */
    public String getMessage_senderName() {
        return message_senderName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_customer_game_message.message_senderName
     * </pre>
     *
     * @param message_senderName
     *            nodejs_customer_game_message.message_senderName：
     */
    public void setMessage_senderName(String message_senderName) {
        this.message_senderName = message_senderName;
    }

    /**
     * <pre>
     * 获取：接收者ID
     * 表字段：nodejs_customer_game_message.message_receiverId
     * </pre>
     *
     * @return nodejs_customer_game_message.message_receiverId：接收者ID
     */
    public String getMessage_receiverId() {
        return message_receiverId;
    }

    /**
     * <pre>
     * 设置：接收者ID
     * 表字段：nodejs_customer_game_message.message_receiverId
     * </pre>
     *
     * @param message_receiverId
     *            nodejs_customer_game_message.message_receiverId：接收者ID
     */
    public void setMessage_receiverId(String message_receiverId) {
        this.message_receiverId = message_receiverId;
    }

    /**
     * <pre>
     * 获取：接收者名称
     * 表字段：nodejs_customer_game_message.message_receiverName
     * </pre>
     *
     * @return nodejs_customer_game_message.message_receiverName：接收者名称
     */
    public String getMessage_receiverName() {
        return message_receiverName;
    }

    /**
     * <pre>
     * 设置：接收者名称
     * 表字段：nodejs_customer_game_message.message_receiverName
     * </pre>
     *
     * @param message_receiverName
     *            nodejs_customer_game_message.message_receiverName：接收者名称
     */
    public void setMessage_receiverName(String message_receiverName) {
        this.message_receiverName = message_receiverName;
    }

    /**
     * <pre>
     * 获取：发送时间
     * 表字段：nodejs_customer_game_message.message_sendTime
     * </pre>
     *
     * @return nodejs_customer_game_message.message_sendTime：发送时间
     */
    public Date getMessage_sendTime() {
        return message_sendTime;
    }

    /**
     * <pre>
     * 设置：发送时间
     * 表字段：nodejs_customer_game_message.message_sendTime
     * </pre>
     *
     * @param message_sendTime
     *            nodejs_customer_game_message.message_sendTime：发送时间
     */
    public void setMessage_sendTime(Date message_sendTime) {
        this.message_sendTime = message_sendTime;
    }

    /**
     * <pre>
     * 获取：消息类型
     * 表字段：nodejs_customer_game_message.message_type
     * </pre>
     *
     * @return nodejs_customer_game_message.message_type：消息类型
     */
    public String getMessage_type() {
        return message_type;
    }

    /**
     * <pre>
     * 设置：消息类型
     * 表字段：nodejs_customer_game_message.message_type
     * </pre>
     *
     * @param message_type
     *            nodejs_customer_game_message.message_type：消息类型
     */
    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    /**
     * <pre>
     * 获取：标题
     * 表字段：nodejs_customer_game_message.message_title
     * </pre>
     *
     * @return nodejs_customer_game_message.message_title：标题
     */
    public String getMessage_title() {
        return message_title;
    }

    /**
     * <pre>
     * 设置：标题
     * 表字段：nodejs_customer_game_message.message_title
     * </pre>
     *
     * @param message_title
     *            nodejs_customer_game_message.message_title：标题
     */
    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    /**
     * <pre>
     * 获取：内容
     * 表字段：nodejs_customer_game_message.message_text
     * </pre>
     *
     * @return nodejs_customer_game_message.message_text：内容
     */
    public String getMessage_text() {
        return message_text;
    }

    /**
     * <pre>
     * 设置：内容
     * 表字段：nodejs_customer_game_message.message_text
     * </pre>
     *
     * @param message_text
     *            nodejs_customer_game_message.message_text：内容
     */
    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    /**
     * <pre>
     * 获取：状态
     * 表字段：nodejs_customer_game_message.message_status
     * </pre>
     *
     * @return nodejs_customer_game_message.message_status：状态
     */
    public Integer getMessage_status() {
        return message_status;
    }

    /**
     * <pre>
     * 设置：状态
     * 表字段：nodejs_customer_game_message.message_status
     * </pre>
     *
     * @param message_status
     *            nodejs_customer_game_message.message_status：状态
     */
    public void setMessage_status(Integer message_status) {
        this.message_status = message_status;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_customer_game_message.message_create_time
     * </pre>
     *
     * @return nodejs_customer_game_message.message_create_time：
     */
    public Date getMessage_create_time() {
        return message_create_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_customer_game_message.message_create_time
     * </pre>
     *
     * @param message_create_time
     *            nodejs_customer_game_message.message_create_time：
     */
    public void setMessage_create_time(Date message_create_time) {
        this.message_create_time = message_create_time;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_customer_game_message.message_modify_time
     * </pre>
     *
     * @return nodejs_customer_game_message.message_modify_time：
     */
    public Date getMessage_modify_time() {
        return message_modify_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_customer_game_message.message_modify_time
     * </pre>
     *
     * @param message_modify_time
     *            nodejs_customer_game_message.message_modify_time：
     */
    public void setMessage_modify_time(Date message_modify_time) {
        this.message_modify_time = message_modify_time;
    }

    /**
     * <pre>
     * 获取：公棚id
     * 表字段：nodejs_customer_game_message.game_cote_id
     * </pre>
     *
     * @return nodejs_customer_game_message.game_cote_id：公棚id
     */
    public String getGame_cote_id() {
        return game_cote_id;
    }

    /**
     * <pre>
     * 设置：公棚id
     * 表字段：nodejs_customer_game_message.game_cote_id
     * </pre>
     *
     * @param game_cote_id
     *            nodejs_customer_game_message.game_cote_id：公棚id
     */
    public void setGame_cote_id(String game_cote_id) {
        this.game_cote_id = game_cote_id;
    }

    /**
     * <pre>
     * 获取：公棚名称
     * 表字段：nodejs_customer_game_message.game_cote_name
     * </pre>
     *
     * @return nodejs_customer_game_message.game_cote_name：公棚名称
     */
    public String getGame_cote_name() {
        return game_cote_name;
    }

    /**
     * <pre>
     * 设置：公棚名称
     * 表字段：nodejs_customer_game_message.game_cote_name
     * </pre>
     *
     * @param game_cote_name
     *            nodejs_customer_game_message.game_cote_name：公棚名称
     */
    public void setGame_cote_name(String game_cote_name) {
        this.game_cote_name = game_cote_name;
    }

    /**
     * <pre>
     * 获取：公棚状态
     * 表字段：nodejs_customer_game_message.game_cote_state
     * </pre>
     *
     * @return nodejs_customer_game_message.game_cote_state：公棚状态
     */
    public String getGame_cote_state() {
        return game_cote_state;
    }

    /**
     * <pre>
     * 设置：公棚状态
     * 表字段：nodejs_customer_game_message.game_cote_state
     * </pre>
     *
     * @param game_cote_state
     *            nodejs_customer_game_message.game_cote_state：公棚状态
     */
    public void setGame_cote_state(String game_cote_state) {
        this.game_cote_state = game_cote_state;
    }

    /**
     * <pre>
     * 获取：比赛id
     * 表字段：nodejs_customer_game_message.game_master_id
     * </pre>
     *
     * @return nodejs_customer_game_message.game_master_id：比赛id
     */
    public String getGame_master_id() {
        return game_master_id;
    }

    /**
     * <pre>
     * 设置：比赛id
     * 表字段：nodejs_customer_game_message.game_master_id
     * </pre>
     *
     * @param game_master_id
     *            nodejs_customer_game_message.game_master_id：比赛id
     */
    public void setGame_master_id(String game_master_id) {
        this.game_master_id = game_master_id;
    }

    /**
     * <pre>
     * 获取：比赛标题
     * 表字段：nodejs_customer_game_message.game_master_text
     * </pre>
     *
     * @return nodejs_customer_game_message.game_master_text：比赛标题
     */
    public String getGame_master_text() {
        return game_master_text;
    }

    /**
     * <pre>
     * 设置：比赛标题
     * 表字段：nodejs_customer_game_message.game_master_text
     * </pre>
     *
     * @param game_master_text
     *            nodejs_customer_game_message.game_master_text：比赛标题
     */
    public void setGame_master_text(String game_master_text) {
        this.game_master_text = game_master_text;
    }

    /**
     * <pre>
     * 获取：比赛url
     * 表字段：nodejs_customer_game_message.game_master_href
     * </pre>
     *
     * @return nodejs_customer_game_message.game_master_href：比赛url
     */
    public String getGame_master_href() {
        return game_master_href;
    }

    /**
     * <pre>
     * 设置：比赛url
     * 表字段：nodejs_customer_game_message.game_master_href
     * </pre>
     *
     * @param game_master_href
     *            nodejs_customer_game_message.game_master_href：比赛url
     */
    public void setGame_master_href(String game_master_href) {
        this.game_master_href = game_master_href;
    }

    /**
     * <pre>
     * 获取：比赛类型
     * 表字段：nodejs_customer_game_message.game_master_type
     * </pre>
     *
     * @return nodejs_customer_game_message.game_master_type：比赛类型
     */
    public String getGame_master_type() {
        return game_master_type;
    }

    /**
     * <pre>
     * 设置：比赛类型
     * 表字段：nodejs_customer_game_message.game_master_type
     * </pre>
     *
     * @param game_master_type
     *            nodejs_customer_game_message.game_master_type：比赛类型
     */
    public void setGame_master_type(String game_master_type) {
        this.game_master_type = game_master_type;
    }

    /**
     * <pre>
     * 获取：比赛站点
     * 表字段：nodejs_customer_game_message.game_master_website
     * </pre>
     *
     * @return nodejs_customer_game_message.game_master_website：比赛站点
     */
    public String getGame_master_website() {
        return game_master_website;
    }

    /**
     * <pre>
     * 设置：比赛站点
     * 表字段：nodejs_customer_game_message.game_master_website
     * </pre>
     *
     * @param game_master_website
     *            nodejs_customer_game_message.game_master_website：比赛站点
     */
    public void setGame_master_website(String game_master_website) {
        this.game_master_website = game_master_website;
    }

    /**
     * <pre>
     * 获取：比赛日期
     * 表字段：nodejs_customer_game_message.game_master_date
     * </pre>
     *
     * @return nodejs_customer_game_message.game_master_date：比赛日期
     */
    public String getGame_master_date() {
        return game_master_date;
    }

    /**
     * <pre>
     * 设置：比赛日期
     * 表字段：nodejs_customer_game_message.game_master_date
     * </pre>
     *
     * @param game_master_date
     *            nodejs_customer_game_message.game_master_date：比赛日期
     */
    public void setGame_master_date(String game_master_date) {
        this.game_master_date = game_master_date;
    }

    /**
     * <pre>
     * 获取：状态(是否发送信息)
     * 表字段：nodejs_customer_game_message.game_master_state
     * </pre>
     *
     * @return nodejs_customer_game_message.game_master_state：状态(是否发送信息)
     */
    public String getGame_master_state() {
        return game_master_state;
    }

    /**
     * <pre>
     * 设置：状态(是否发送信息)
     * 表字段：nodejs_customer_game_message.game_master_state
     * </pre>
     *
     * @param game_master_state
     *            nodejs_customer_game_message.game_master_state：状态(是否发送信息)
     */
    public void setGame_master_state(String game_master_state) {
        this.game_master_state = game_master_state;
    }

    /**
     * <pre>
     * 获取：鸽主openid
     * 表字段：nodejs_customer_game_message.game_receiver_openid
     * </pre>
     *
     * @return nodejs_customer_game_message.game_receiver_openid：鸽主openid
     */
    public String getGame_receiver_openid() {
        return game_receiver_openid;
    }

    /**
     * <pre>
     * 设置：鸽主openid
     * 表字段：nodejs_customer_game_message.game_receiver_openid
     * </pre>
     *
     * @param game_receiver_openid
     *            nodejs_customer_game_message.game_receiver_openid：鸽主openid
     */
    public void setGame_receiver_openid(String game_receiver_openid) {
        this.game_receiver_openid = game_receiver_openid;
    }

    /**
     * <pre>
     * 获取：鸽主nickname
     * 表字段：nodejs_customer_game_message.game_receiver_nickname
     * </pre>
     *
     * @return nodejs_customer_game_message.game_receiver_nickname：鸽主nickname
     */
    public String getGame_receiver_nickname() {
        return game_receiver_nickname;
    }

    /**
     * <pre>
     * 设置：鸽主nickname
     * 表字段：nodejs_customer_game_message.game_receiver_nickname
     * </pre>
     *
     * @param game_receiver_nickname
     *            nodejs_customer_game_message.game_receiver_nickname：鸽主nickname
     */
    public void setGame_receiver_nickname(String game_receiver_nickname) {
        this.game_receiver_nickname = game_receiver_nickname;
    }

    /**
     * <pre>
     * 获取：鸽主headimgurl
     * 表字段：nodejs_customer_game_message.game_receiver_headimgurl
     * </pre>
     *
     * @return nodejs_customer_game_message.game_receiver_headimgurl：鸽主headimgurl
     */
    public String getGame_receiver_headimgurl() {
        return game_receiver_headimgurl;
    }

    /**
     * <pre>
     * 设置：鸽主headimgurl
     * 表字段：nodejs_customer_game_message.game_receiver_headimgurl
     * </pre>
     *
     * @param game_receiver_headimgurl
     *            nodejs_customer_game_message.game_receiver_headimgurl：鸽主headimgurl
     */
    public void setGame_receiver_headimgurl(String game_receiver_headimgurl) {
        this.game_receiver_headimgurl = game_receiver_headimgurl;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_customer_game_message.game_create_time
     * </pre>
     *
     * @return nodejs_customer_game_message.game_create_time：
     */
    public Date getGame_create_time() {
        return game_create_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_customer_game_message.game_create_time
     * </pre>
     *
     * @param game_create_time
     *            nodejs_customer_game_message.game_create_time：
     */
    public void setGame_create_time(Date game_create_time) {
        this.game_create_time = game_create_time;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_customer_game_message.game_modify_time
     * </pre>
     *
     * @return nodejs_customer_game_message.game_modify_time：
     */
    public Date getGame_modify_time() {
        return game_modify_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_customer_game_message.game_modify_time
     * </pre>
     *
     * @param game_modify_time
     *            nodejs_customer_game_message.game_modify_time：
     */
    public void setGame_modify_time(Date game_modify_time) {
        this.game_modify_time = game_modify_time;
    }
}