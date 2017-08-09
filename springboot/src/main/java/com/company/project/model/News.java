package com.company.project.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String  id;

    private String title;

    @Column(name = "picture")
    private String picture;

    private String content;

    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private String stringUpdateTime;

    @Transient
    private String stringUpdateTimeYear;

    @Transient
    private Date updateTimeMin;

    @Transient
    private Date updateTimeMax;


    public News() {
        this.id = String.valueOf( UUID.randomUUID()).replaceAll("-","");
        this.updateTime = new Date();
    }

    public String getStringUpdateTimeYear() {
        return stringUpdateTimeYear;
    }

    public void setStringUpdateTimeYear(String stringUpdateTimeYear) {
        this.stringUpdateTimeYear = stringUpdateTimeYear;
    }

    public String getStringUpdateTime() {
        return stringUpdateTime;
    }

    public void setStringUpdateTime(String stringUpdateTime) {
        this.stringUpdateTime = stringUpdateTime;
    }

    public Date getUpdateTimeMin() {
        return updateTimeMin;
    }

    public void setUpdateTimeMin(Date updateTimeMin) {
        this.updateTimeMin = updateTimeMin;
    }

    public Date getUpdateTimeMax() {
        return updateTimeMax;
    }

    public void setUpdateTimeMax(Date updateTimeMax) {
        this.updateTimeMax = updateTimeMax;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 0-往期;  1-实时;
     */
    private Integer status;

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
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.stringUpdateTime =dateFormat.format(updateTime);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
        this.stringUpdateTimeYear=dateFormat2.format( updateTime );
        this.updateTime = updateTime;
    }

    /**
     * 获取0-往期;  1-实时;
     *
     * @return status - 0-往期;  1-实时;
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0-往期;  1-实时;
     *
     * @param status 0-往期;  1-实时;
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}