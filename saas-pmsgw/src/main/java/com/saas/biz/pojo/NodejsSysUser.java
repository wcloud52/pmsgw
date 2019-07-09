package com.saas.biz.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class NodejsSysUser {
    /**
     * <pre>
     * 用户Id
     * 表字段 : nodejs_sys_user.Id
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_sys_user.cote_id
     * </pre>
     */
    private String cote_id;

    /**
     * <pre>
     * 
     * 表字段 : nodejs_sys_user.cote_name
     * </pre>
     */
    private String cote_name;

    /**
     * <pre>
     * 登录账号
     * 表字段 : nodejs_sys_user.LoginName
     * </pre>
     */
    private String loginName;

    /**
     * <pre>
     * 密码
     * 表字段 : nodejs_sys_user.Password
     * </pre>
     */
    private String password;

    /**
     * <pre>
     * 用户姓名
     * 表字段 : nodejs_sys_user.UserName
     * </pre>
     */
    private String userName;

    /**
     * <pre>
     * 用户类型 system-user
     * 表字段 : nodejs_sys_user.UserType
     * </pre>
     */
    private String userType;

    /**
     * <pre>
     * 邮箱
     * 表字段 : nodejs_sys_user.Email
     * </pre>
     */
    private String email;

    /**
     * <pre>
     * 手机号
     * 表字段 : nodejs_sys_user.Mobile
     * </pre>
     */
    private String mobile;

    /**
     * <pre>
     * 是否允许登录
     * 表字段 : nodejs_sys_user.Status
     * </pre>
     */
    private Integer status;

    /**
     * <pre>
     * 描述
     * 表字段 : nodejs_sys_user.Description
     * </pre>
     */
    private String description;

    /**
     * <pre>
     * 创建时间
     * 表字段 : nodejs_sys_user.CreatedDatetime
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdDatetime;

    /**
     * <pre>
     * 最后更新时间
     * 表字段 : nodejs_sys_user.UpdatedDatetime
     * </pre>
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDatetime;

    /**
     * <pre>
     * 获取：用户Id
     * 表字段：nodejs_sys_user.Id
     * </pre>
     *
     * @return nodejs_sys_user.Id：用户Id
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：用户Id
     * 表字段：nodejs_sys_user.Id
     * </pre>
     *
     * @param id
     *            nodejs_sys_user.Id：用户Id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_sys_user.cote_id
     * </pre>
     *
     * @return nodejs_sys_user.cote_id：
     */
    public String getCote_id() {
        return cote_id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_sys_user.cote_id
     * </pre>
     *
     * @param cote_id
     *            nodejs_sys_user.cote_id：
     */
    public void setCote_id(String cote_id) {
        this.cote_id = cote_id;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_sys_user.cote_name
     * </pre>
     *
     * @return nodejs_sys_user.cote_name：
     */
    public String getCote_name() {
        return cote_name;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_sys_user.cote_name
     * </pre>
     *
     * @param cote_name
     *            nodejs_sys_user.cote_name：
     */
    public void setCote_name(String cote_name) {
        this.cote_name = cote_name;
    }

    /**
     * <pre>
     * 获取：登录账号
     * 表字段：nodejs_sys_user.LoginName
     * </pre>
     *
     * @return nodejs_sys_user.LoginName：登录账号
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * <pre>
     * 设置：登录账号
     * 表字段：nodejs_sys_user.LoginName
     * </pre>
     *
     * @param loginName
     *            nodejs_sys_user.LoginName：登录账号
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * <pre>
     * 获取：密码
     * 表字段：nodejs_sys_user.Password
     * </pre>
     *
     * @return nodejs_sys_user.Password：密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * <pre>
     * 设置：密码
     * 表字段：nodejs_sys_user.Password
     * </pre>
     *
     * @param password
     *            nodejs_sys_user.Password：密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <pre>
     * 获取：用户姓名
     * 表字段：nodejs_sys_user.UserName
     * </pre>
     *
     * @return nodejs_sys_user.UserName：用户姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <pre>
     * 设置：用户姓名
     * 表字段：nodejs_sys_user.UserName
     * </pre>
     *
     * @param userName
     *            nodejs_sys_user.UserName：用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * <pre>
     * 获取：用户类型 system-user
     * 表字段：nodejs_sys_user.UserType
     * </pre>
     *
     * @return nodejs_sys_user.UserType：用户类型 system-user
     */
    public String getUserType() {
        return userType;
    }

    /**
     * <pre>
     * 设置：用户类型 system-user
     * 表字段：nodejs_sys_user.UserType
     * </pre>
     *
     * @param userType
     *            nodejs_sys_user.UserType：用户类型 system-user
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * <pre>
     * 获取：邮箱
     * 表字段：nodejs_sys_user.Email
     * </pre>
     *
     * @return nodejs_sys_user.Email：邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * <pre>
     * 设置：邮箱
     * 表字段：nodejs_sys_user.Email
     * </pre>
     *
     * @param email
     *            nodejs_sys_user.Email：邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <pre>
     * 获取：手机号
     * 表字段：nodejs_sys_user.Mobile
     * </pre>
     *
     * @return nodejs_sys_user.Mobile：手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * <pre>
     * 设置：手机号
     * 表字段：nodejs_sys_user.Mobile
     * </pre>
     *
     * @param mobile
     *            nodejs_sys_user.Mobile：手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * <pre>
     * 获取：是否允许登录
     * 表字段：nodejs_sys_user.Status
     * </pre>
     *
     * @return nodejs_sys_user.Status：是否允许登录
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * <pre>
     * 设置：是否允许登录
     * 表字段：nodejs_sys_user.Status
     * </pre>
     *
     * @param status
     *            nodejs_sys_user.Status：是否允许登录
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * <pre>
     * 获取：描述
     * 表字段：nodejs_sys_user.Description
     * </pre>
     *
     * @return nodejs_sys_user.Description：描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * <pre>
     * 设置：描述
     * 表字段：nodejs_sys_user.Description
     * </pre>
     *
     * @param description
     *            nodejs_sys_user.Description：描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：nodejs_sys_user.CreatedDatetime
     * </pre>
     *
     * @return nodejs_sys_user.CreatedDatetime：创建时间
     */
    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：nodejs_sys_user.CreatedDatetime
     * </pre>
     *
     * @param createdDatetime
     *            nodejs_sys_user.CreatedDatetime：创建时间
     */
    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    /**
     * <pre>
     * 获取：最后更新时间
     * 表字段：nodejs_sys_user.UpdatedDatetime
     * </pre>
     *
     * @return nodejs_sys_user.UpdatedDatetime：最后更新时间
     */
    public Date getUpdatedDatetime() {
        return updatedDatetime;
    }

    /**
     * <pre>
     * 设置：最后更新时间
     * 表字段：nodejs_sys_user.UpdatedDatetime
     * </pre>
     *
     * @param updatedDatetime
     *            nodejs_sys_user.UpdatedDatetime：最后更新时间
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }
}