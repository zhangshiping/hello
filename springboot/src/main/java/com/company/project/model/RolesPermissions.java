package com.company.project.model;

import javax.persistence.*;

@Table(name = "roles_permissions")
public class RolesPermissions {
    @Id
    @Column(name = "roles_id")
    private String rolesId;

    private String permissionId;

    /**
     * @return roles_id
     */
    public String getRolesId() {
        return rolesId;
    }

    /**
     * @param rolesId
     */
    public void setRolesId(String rolesId) {
        this.rolesId = rolesId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}