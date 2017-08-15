package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.RolesPermissions;

import java.util.List;

public interface RolesPermissionsMapper extends Mapper<RolesPermissions> {
    List<RolesPermissions> selectedPermissionsByUserId(String id);
    List<RolesPermissions> selectedPermissionsByRoleId(String id);
}