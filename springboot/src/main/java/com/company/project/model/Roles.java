package com.company.project.model;


import com.company.project.infrastructure.tools.DateUtils;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String guid;

    @Column(name = "create_time")
    private Date createTime;

    private Boolean archived;

    private Integer version;

    @Column(name = "role_name")
    private String roleName;

    @Transient
    private List<Permissions> permissions;

    @Transient
    private String createTimeString;

    public Roles() {
        this.id = String.valueOf(UUID.randomUUID()).replaceAll("-","");
        this.createTime= new Date();
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
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
        this.createTimeString =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createTime);
        return createTime;
    }
    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString =createTimeString;
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
     * @return role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}