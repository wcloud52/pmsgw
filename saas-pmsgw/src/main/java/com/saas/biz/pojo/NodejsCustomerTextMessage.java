package com.saas.biz.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class NodejsCustomerTextMessage {
    /**
     * <pre>
     * 消息id
     * 表字段 : nodejs_customer_text_message.message_id
     * </pre>
     */
    private String message_id;

    /**
     * <pre>
     * 发送者ID
     * 表字段 : nodejs_customer_text_message.message_senderId
     * </pre>
     */
    private String message_senderId;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_customer_text_message.message_senderName
     * </pre>
     */
    private String message_senderName;

    /**
     * <pre>
     * 接收者ID
     * 表字段 : nodejs_customer_text_message.message_receiverId
     * </pre>
     */
    private String message_receiverId;

    /**
     * <pre>
     * 接收者名称
     * 表字段 : nodejs_customer_text_message.message_receiverName
     * </pre>
     */
    private String message_receiverName;

    /**
     * <pre>
     * 发送时间
     * 表字段 : nodejs_customer_text_message.message_sendTime
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date message_sendTime;

    /**
     * <pre>
     * 消息类型
     * 表字段 : nodejs_customer_text_message.message_type
     * </pre>
     */
    private String message_type;

    /**
     * <pre>
     * 标题
     * 表字段 : nodejs_customer_text_message.message_title
     * </pre>
     */
    private String message_title;

    /**
     * <pre>
     * 内容
     * 表字段 : nodejs_customer_text_message.message_text
     * </pre>
     */
    private String message_text;

    /**
     * <pre>
     * 状态
     * 表字段 : nodejs_customer_text_message.message_status
     * </pre>
     */
    private Integer message_status;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_customer_text_message.message_create_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date message_create_time;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_customer_text_message.message_modify_time
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date message_modify_time;

    /**
     * <pre>
     * 获取：消息id
     * 表字段：nodejs_customer_text_message.message_id
     * </pre>
     *
     * @return nodejs_customer_text_message.message_id：消息id
     */
    public String getMessage_id() {
        return message_id;
    }

    /**
     * <pre>
     * 设置：消息id
     * 表字段：nodejs_customer_text_message.message_id
     * </pre>
     *
     * @param message_id
     *            nodejs_customer_text_message.message_id：消息id
     */
    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    /**
     * <pre>
     * 获取：发送者ID
     * 表字段：nodejs_customer_text_message.message_senderId
     * </pre>
     *
     * @return nodejs_customer_text_message.message_senderId：发送者ID
     */
    public String getMessage_senderId() {
        return message_senderId;
    }

    /**
     * <pre>
     * 设置：发送者ID
     * 表字段：nodejs_customer_text_message.message_senderId
     * </pre>
     *
     * @param message_senderId
     *            nodejs_customer_text_message.message_senderId：发送者ID
     */
    public void setMessage_senderId(String message_senderId) {
        this.message_senderId = message_senderId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_customer_text_message.message_senderName
     * </pre>
     *
     * @return nodejs_customer_text_message.message_senderName：
     */
    public String getMessage_senderName() {
        return message_senderName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_customer_text_message.message_senderName
     * </pre>
     *
     * @param message_senderName
     *            nodejs_customer_text_message.message_senderName：
     */
    public void setMessage_senderName(String message_senderName) {
        this.message_senderName = message_senderName;
    }

    /**
     * <pre>
     * 获取：接收者ID
     * 表字段：nodejs_customer_text_message.message_receiverId
     * </pre>
     *
     * @return nodejs_customer_text_message.message_receiverId：接收者ID
     */
    public String getMessage_receiverId() {
        return message_receiverId;
    }

    /**
     * <pre>
     * 设置：接收者ID
     * 表字段：nodejs_customer_text_message.message_receiverId
     * </pre>
     *
     * @param message_receiverId
     *            nodejs_customer_text_message.message_receiverId：接收者ID
     */
    public void setMessage_receiverId(String message_receiverId) {
        this.message_receiverId = message_receiverId;
    }

    /**
     * <pre>
     * 获取：接收者名称
     * 表字段：nodejs_customer_text_message.message_receiverName
     * </pre>
     *
     * @return nodejs_customer_text_message.message_receiverName：接收者名称
     */
    public String getMessage_receiverName() {
        return message_receiverName;
    }

    /**
     * <pre>
     * 设置：接收者名称
     * 表字段：nodejs_customer_text_message.message_receiverName
     * </pre>
     *
     * @param message_receiverName
     *            nodejs_customer_text_message.message_receiverName：接收者名称
     */
    public void setMessage_receiverName(String message_receiverName) {
        this.message_receiverName = message_receiverName;
    }

    /**
     * <pre>
     * 获取：发送时间
     * 表字段：nodejs_customer_text_message.message_sendTime
     * </pre>
     *
     * @return nodejs_customer_text_message.message_sendTime：发送时间
     */
    public Date getMessage_sendTime() {
        return message_sendTime;
    }

    /**
     * <pre>
     * 设置：发送时间
     * 表字段：nodejs_customer_text_message.message_sendTime
     * </pre>
     *
     * @param message_sendTime
     *            nodejs_customer_text_message.message_sendTime：发送时间
     */
    public void setMessage_sendTime(Date message_sendTime) {
        this.message_sendTime = message_sendTime;
    }

    /**
     * <pre>
     * 获取：消息类型
     * 表字段：nodejs_customer_text_message.message_type
     * </pre>
     *
     * @return nodejs_customer_text_message.message_type：消息类型
     */
    public String getMessage_type() {
        return message_type;
    }

    /**
     * <pre>
     * 设置：消息类型
     * 表字段：nodejs_customer_text_message.message_type
     * </pre>
     *
     * @param message_type
     *            nodejs_customer_text_message.message_type：消息类型
     */
    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    /**
     * <pre>
     * 获取：标题
     * 表字段：nodejs_customer_text_message.message_title
     * </pre>
     *
     * @return nodejs_customer_text_message.message_title：标题
     */
    public String getMessage_title() {
        return message_title;
    }

    /**
     * <pre>
     * 设置：标题
     * 表字段：nodejs_customer_text_message.message_title
     * </pre>
     *
     * @param message_title
     *            nodejs_customer_text_message.message_title：标题
     */
    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    /**
     * <pre>
     * 获取：内容
     * 表字段：nodejs_customer_text_message.message_text
     * </pre>
     *
     * @return nodejs_customer_text_message.message_text：内容
     */
    public String getMessage_text() {
        return message_text;
    }

    /**
     * <pre>
     * 设置：内容
     * 表字段：nodejs_customer_text_message.message_text
     * </pre>
     *
     * @param message_text
     *            nodejs_customer_text_message.message_text：内容
     */
    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    /**
     * <pre>
     * 获取：状态
     * 表字段：nodejs_customer_text_message.message_status
     * </pre>
     *
     * @return nodejs_customer_text_message.message_status：状态
     */
    public Integer getMessage_status() {
        return message_status;
    }

    /**
     * <pre>
     * 设置：状态
     * 表字段：nodejs_customer_text_message.message_status
     * </pre>
     *
     * @param message_status
     *            nodejs_customer_text_message.message_status：状态
     */
    public void setMessage_status(Integer message_status) {
        this.message_status = message_status;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_customer_text_message.message_create_time
     * </pre>
     *
     * @return nodejs_customer_text_message.message_create_time：
     */
    public Date getMessage_create_time() {
        return message_create_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_customer_text_message.message_create_time
     * </pre>
     *
     * @param message_create_time
     *            nodejs_customer_text_message.message_create_time：
     */
    public void setMessage_create_time(Date message_create_time) {
        this.message_create_time = message_create_time;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_customer_text_message.message_modify_time
     * </pre>
     *
     * @return nodejs_customer_text_message.message_modify_time：
     */
    public Date getMessage_modify_time() {
        return message_modify_time;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_customer_text_message.message_modify_time
     * </pre>
     *
     * @param message_modify_time
     *            nodejs_customer_text_message.message_modify_time：
     */
    public void setMessage_modify_time(Date message_modify_time) {
        this.message_modify_time = message_modify_time;
    }
}