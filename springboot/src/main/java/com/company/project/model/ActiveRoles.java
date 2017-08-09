package com.company.project.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
public class ActiveRoles {
    private String roleId;
    private String roleName;
    private List<String> permissions;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
