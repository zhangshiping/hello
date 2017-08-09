package com.company.project.model;

import com.company.project.infrastructure.tools.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "username")
    private String username;

    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name="sex")
    private Integer sex;

    @Column(name = "register_date")
    private Date registerDate;

    @Transient
    private String stringRegisterDate ;

    public String getStringRegisterDate() {
        return stringRegisterDate;
    }

    public void setStringRegisterDate(String stringRegisterDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.stringRegisterDate =dateFormat.format(this.registerDate);
    }

    public User() {
        this.id = String.valueOf(UUID.randomUUID()).replaceAll("-","");
        this.registerDate = new Date();
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
     * @return nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return register_date
     */
    public Date getRegisterDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.stringRegisterDate =dateFormat.format(this.registerDate);
        return registerDate;
    }

    /**
     * @param registerDate
     */
    public void setRegisterDate(Date registerDate) {

        this.registerDate = registerDate;
    }
}