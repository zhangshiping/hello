package com.company.project.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
public class ActiveUser {
    private String userId;
    private String userName;
    private List<ActiveRoles> roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ActiveRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<ActiveRoles> roles) {
        this.roles = roles;
    }
}
