package com.company.project.model;

import javax.persistence.*;

public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String permission;

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
     * @return permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @param permission
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }
}