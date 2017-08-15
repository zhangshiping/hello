package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Permissions;

import java.util.List;

public interface PermissionsMapper extends Mapper<Permissions> {
    List<Permissions> selectedPermissionsByRoleId(String id);

}