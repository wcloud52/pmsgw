package com.saas.biz.pojo;

import java.util.Date;

public class NodejsStorage {
    /**
     * <pre>
     * 
     * 表字段 : nodejs_storage.id
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 文件的唯一索引
     * 表字段 : nodejs_storage.storageKey
     * </pre>
     */
    private String storageKey;

    /**
     * <pre>
     * 文件名
     * 表字段 : nodejs_storage.storageName
     * </pre>
     */
    private String storageName;

    /**
     * <pre>
     * 文件类型
     * 表字段 : nodejs_storage.storageType
     * </pre>
     */
    private String storageType;

    /**
     * <pre>
     * 文件大小
     * 表字段 : nodejs_storage.storageSize
     * </pre>
     */
    private Integer storageSize;

    /**
     * <pre>
     * 最后更新时间
     * 表字段 : nodejs_storage.modified
     * </pre>
     */
    private Date modified;

    /**
     * <pre>
     * 文件访问链接
     * 表字段 : nodejs_storage.url
     * </pre>
     */
    private String url;

    /**
     * <pre>
     * 创建时间
     * 表字段 : nodejs_storage.add_time
     * </pre>
     */
    private Date add_time;

    /**
     * <pre>
     * 逻辑删除
     * 表字段 : nodejs_storage.deleted
     * </pre>
     */
    private Boolean deleted;

    /**
     * <pre>
     * 获取：
     * 表字段：nodejs_storage.id
     * </pre>
     *
     * @return nodejs_storage.id：
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：nodejs_storage.id
     * </pre>
     *
     * @param id
     *            nodejs_storage.id：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：文件的唯一索引
     * 表字段：nodejs_storage.storageKey
     * </pre>
     *
     * @return nodejs_storage.storageKey：文件的唯一索引
     */
    public String getStorageKey() {
        return storageKey;
    }

    /**
     * <pre>
     * 设置：文件的唯一索引
     * 表字段：nodejs_storage.storageKey
     * </pre>
     *
     * @param storageKey
     *            nodejs_storage.storageKey：文件的唯一索引
     */
    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }

    /**
     * <pre>
     * 获取：文件名
     * 表字段：nodejs_storage.storageName
     * </pre>
     *
     * @return nodejs_storage.storageName：文件名
     */
    public String getStorageName() {
        return storageName;
    }

    /**
     * <pre>
     * 设置：文件名
     * 表字段：nodejs_storage.storageName
     * </pre>
     *
     * @param storageName
     *            nodejs_storage.storageName：文件名
     */
    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    /**
     * <pre>
     * 获取：文件类型
     * 表字段：nodejs_storage.storageType
     * </pre>
     *
     * @return nodejs_storage.storageType：文件类型
     */
    public String getStorageType() {
        return storageType;
    }

    /**
     * <pre>
     * 设置：文件类型
     * 表字段：nodejs_storage.storageType
     * </pre>
     *
     * @param storageType
     *            nodejs_storage.storageType：文件类型
     */
    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    /**
     * <pre>
     * 获取：文件大小
     * 表字段：nodejs_storage.storageSize
     * </pre>
     *
     * @return nodejs_storage.storageSize：文件大小
     */
    public Integer getStorageSize() {
        return storageSize;
    }

    /**
     * <pre>
     * 设置：文件大小
     * 表字段：nodejs_storage.storageSize
     * </pre>
     *
     * @param storageSize
     *            nodejs_storage.storageSize：文件大小
     */
    public void setStorageSize(Integer storageSize) {
        this.storageSize = storageSize;
    }

    /**
     * <pre>
     * 获取：最后更新时间
     * 表字段：nodejs_storage.modified
     * </pre>
     *
     * @return nodejs_storage.modified：最后更新时间
     */
    public Date getModified() {
        return modified;
    }

    /**
     * <pre>
     * 设置：最后更新时间
     * 表字段：nodejs_storage.modified
     * </pre>
     *
     * @param modified
     *            nodejs_storage.modified：最后更新时间
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * <pre>
     * 获取：文件访问链接
     * 表字段：nodejs_storage.url
     * </pre>
     *
     * @return nodejs_storage.url：文件访问链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * <pre>
     * 设置：文件访问链接
     * 表字段：nodejs_storage.url
     * </pre>
     *
     * @param url
     *            nodejs_storage.url：文件访问链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：nodejs_storage.add_time
     * </pre>
     *
     * @return nodejs_storage.add_time：创建时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：nodejs_storage.add_time
     * </pre>
     *
     * @param add_time
     *            nodejs_storage.add_time：创建时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    /**
     * <pre>
     * 获取：逻辑删除
     * 表字段：nodejs_storage.deleted
     * </pre>
     *
     * @return nodejs_storage.deleted：逻辑删除
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * <pre>
     * 设置：逻辑删除
     * 表字段：nodejs_storage.deleted
     * </pre>
     *
     * @param deleted
     *            nodejs_storage.deleted：逻辑删除
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}