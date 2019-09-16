package com.saas.biz.pojo;

import java.util.Date;

public class NodejsMpUser {
    /**
     * <pre>
     * 
     * 表字段 : nodejs_mp_user.id
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 用户名称
     * 表字段 : nodejs_mp_user.username
     * </pre>
     */
    private String username;

    /**
     * <pre>
     * 用户密码
     * 表字段 : nodejs_mp_user.password
     * </pre>
     */
    private String password;

    /**
     * <pre>
     * 性别：0 未知， 1男， 1 女
     * 表字段 : nodejs_mp_user.gender
     * </pre>
     */
    private Byte gender;

    /**
     * <pre>
     * 生日
     * 表字段 : nodejs_mp_user.birthday
     * </pre>
     */
    private Date birthday;

    /**
     * <pre>
     * 最近一次登录时间
     * 表字段 : nodejs_mp_user.last_login_time
     * </pre>
     */
    private Date last_login_time;

    /**
     * <pre>
     * 最近一次登录IP地址
     * 表字段 : nodejs_mp_user.last_login_ip
     * </pre>
     */
    private String last_login_ip;

    /**
     * <pre>
     * 0 普通用户，1 VIP用户，2 高级VIP用户
     * 表字段 : nodejs_mp_user.user_level
     * </pre>
     */
    private Byte user_level;

    /**
     * <pre>
     * 用户昵称或网络名称
     * 表字段 : nodejs_mp_user.nickname
     * </pre>
     */
    private String nickname;

    /**
     * <pre>
     * 用户手机号码
     * 表字段 : nodejs_mp_user.mobile
     * </pre>
     */
    private String mobile;

    /**
     * <pre>
     * 用户头像图片
     * 表字段 : nodejs_mp_user.avatar
     * </pre>
     */
    private String avatar;

    /**
     * <pre>
     * 微信登录openid
     * 表字段 : nodejs_mp_user.weixin_openid
     * </pre>
     */
    private String weixin_openid;

    /**
     * <pre>
     * 0 可用, 1 禁用, 2 注销
     * 表字段 : nodejs_mp_user.status
     * </pre>
     */
    private Byte status;

    /**
     * <pre>
     * 创建时间
     * 表字段 : nodejs_mp_user.add_time
     * </pre>
     */
    private Date add_time;

    /**
     * <pre>
     * 逻辑删除
     * 表字段 : nodejs_mp_user.deleted
     * </pre>
     */
    private Boolean deleted;

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_mp_user.id
     * </pre>
     *
     * @return nodejs_mp_user.id：
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_mp_user.id
     * </pre>
     *
     * @param id
     *            nodejs_mp_user.id：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：用户名称
     * 表字段：nodejs_mp_user.username
     * </pre>
     *
     * @return nodejs_mp_user.username：用户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * <pre>
     * 设置：用户名称
     * 表字段：nodejs_mp_user.username
     * </pre>
     *
     * @param username
     *            nodejs_mp_user.username：用户名称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <pre>
     * 获取：用户密码
     * 表字段：nodejs_mp_user.password
     * </pre>
     *
     * @return nodejs_mp_user.password：用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * <pre>
     * 设置：用户密码
     * 表字段：nodejs_mp_user.password
     * </pre>
     *
     * @param password
     *            nodejs_mp_user.password：用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <pre>
     * 获取：性别：0 未知， 1男， 1 女
     * 表字段：nodejs_mp_user.gender
     * </pre>
     *
     * @return nodejs_mp_user.gender：性别：0 未知， 1男， 1 女
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * <pre>
     * 设置：性别：0 未知， 1男， 1 女
     * 表字段：nodejs_mp_user.gender
     * </pre>
     *
     * @param gender
     *            nodejs_mp_user.gender：性别：0 未知， 1男， 1 女
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * <pre>
     * 获取：生日
     * 表字段：nodejs_mp_user.birthday
     * </pre>
     *
     * @return nodejs_mp_user.birthday：生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * <pre>
     * 设置：生日
     * 表字段：nodejs_mp_user.birthday
     * </pre>
     *
     * @param birthday
     *            nodejs_mp_user.birthday：生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * <pre>
     * 获取：最近一次登录时间
     * 表字段：nodejs_mp_user.last_login_time
     * </pre>
     *
     * @return nodejs_mp_user.last_login_time：最近一次登录时间
     */
    public Date getLast_login_time() {
        return last_login_time;
    }

    /**
     * <pre>
     * 设置：最近一次登录时间
     * 表字段：nodejs_mp_user.last_login_time
     * </pre>
     *
     * @param last_login_time
     *            nodejs_mp_user.last_login_time：最近一次登录时间
     */
    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }

    /**
     * <pre>
     * 获取：最近一次登录IP地址
     * 表字段：nodejs_mp_user.last_login_ip
     * </pre>
     *
     * @return nodejs_mp_user.last_login_ip：最近一次登录IP地址
     */
    public String getLast_login_ip() {
        return last_login_ip;
    }

    /**
     * <pre>
     * 设置：最近一次登录IP地址
     * 表字段：nodejs_mp_user.last_login_ip
     * </pre>
     *
     * @param last_login_ip
     *            nodejs_mp_user.last_login_ip：最近一次登录IP地址
     */
    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    /**
     * <pre>
     * 获取：0 普通用户，1 VIP用户，2 高级VIP用户
     * 表字段：nodejs_mp_user.user_level
     * </pre>
     *
     * @return nodejs_mp_user.user_level：0 普通用户，1 VIP用户，2 高级VIP用户
     */
    public Byte getUser_level() {
        return user_level;
    }

    /**
     * <pre>
     * 设置：0 普通用户，1 VIP用户，2 高级VIP用户
     * 表字段：nodejs_mp_user.user_level
     * </pre>
     *
     * @param user_level
     *            nodejs_mp_user.user_level：0 普通用户，1 VIP用户，2 高级VIP用户
     */
    public void setUser_level(Byte user_level) {
        this.user_level = user_level;
    }

    /**
     * <pre>
     * 获取：用户昵称或网络名称
     * 表字段：nodejs_mp_user.nickname
     * </pre>
     *
     * @return nodejs_mp_user.nickname：用户昵称或网络名称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * <pre>
     * 设置：用户昵称或网络名称
     * 表字段：nodejs_mp_user.nickname
     * </pre>
     *
     * @param nickname
     *            nodejs_mp_user.nickname：用户昵称或网络名称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * <pre>
     * 获取：用户手机号码
     * 表字段：nodejs_mp_user.mobile
     * </pre>
     *
     * @return nodejs_mp_user.mobile：用户手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * <pre>
     * 设置：用户手机号码
     * 表字段：nodejs_mp_user.mobile
     * </pre>
     *
     * @param mobile
     *            nodejs_mp_user.mobile：用户手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * <pre>
     * 获取：用户头像图片
     * 表字段：nodejs_mp_user.avatar
     * </pre>
     *
     * @return nodejs_mp_user.avatar：用户头像图片
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * <pre>
     * 设置：用户头像图片
     * 表字段：nodejs_mp_user.avatar
     * </pre>
     *
     * @param avatar
     *            nodejs_mp_user.avatar：用户头像图片
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * <pre>
     * 获取：微信登录openid
     * 表字段：nodejs_mp_user.weixin_openid
     * </pre>
     *
     * @return nodejs_mp_user.weixin_openid：微信登录openid
     */
    public String getWeixin_openid() {
        return weixin_openid;
    }

    /**
     * <pre>
     * 设置：微信登录openid
     * 表字段：nodejs_mp_user.weixin_openid
     * </pre>
     *
     * @param weixin_openid
     *            nodejs_mp_user.weixin_openid：微信登录openid
     */
    public void setWeixin_openid(String weixin_openid) {
        this.weixin_openid = weixin_openid;
    }

    /**
     * <pre>
     * 获取：0 可用, 1 禁用, 2 注销
     * 表字段：nodejs_mp_user.status
     * </pre>
     *
     * @return nodejs_mp_user.status：0 可用, 1 禁用, 2 注销
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * <pre>
     * 设置：0 可用, 1 禁用, 2 注销
     * 表字段：nodejs_mp_user.status
     * </pre>
     *
     * @param status
     *            nodejs_mp_user.status：0 可用, 1 禁用, 2 注销
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：nodejs_mp_user.add_time
     * </pre>
     *
     * @return nodejs_mp_user.add_time：创建时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：nodejs_mp_user.add_time
     * </pre>
     *
     * @param add_time
     *            nodejs_mp_user.add_time：创建时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    /**
     * <pre>
     * 获取：逻辑删除
     * 表字段：nodejs_mp_user.deleted
     * </pre>
     *
     * @return nodejs_mp_user.deleted：逻辑删除
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * <pre>
     * 设置：逻辑删除
     * 表字段：nodejs_mp_user.deleted
     * </pre>
     *
     * @param deleted
     *            nodejs_mp_user.deleted：逻辑删除
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}