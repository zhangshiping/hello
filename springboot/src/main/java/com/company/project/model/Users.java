package com.company.project.model;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String guid;

    @Column(name = "create_time")
    private Date createTime;

    private Boolean archived;

    private Integer version;

    private String password;

    private String username;

    @Column(name = "default_user")
    private Boolean defaultUser;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Transient
    private List<Roles> roles;

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * @param guid
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return archived
     */
    public Boolean getArchived() {
        return archived;
    }

    /**
     * @param archived
     */
    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    /**
     * @return version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return default_user
     */
    public Boolean getDefaultUser() {
        return defaultUser;
    }

    /**
     * @param defaultUser
     */
    public void setDefaultUser(Boolean defaultUser) {
        this.defaultUser = defaultUser;
    }

    /**
     * @return last_login_time
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}