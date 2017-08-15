package com.company.project.service;
import com.company.project.model.Permissions;
import com.company.project.model.Roles;
import com.company.project.core.Service;
import com.company.project.model.RolesPermissions;
import com.company.project.model.Users;

import java.util.List;


/**
 * Created by CodeGenerator on 2017/08/10.
 */
public interface AuthorizeService {

    public Users findByName(String username);
    public List<Roles> findRolesById(String id);
    public Roles findRoleById(String roleId);
    public List<Roles> findAllRoles();
    public List<Permissions> findPermissionsById(String id);
    public List<Permissions> findAllPermission();
    public void addRoles(Roles roles);
    public void addRolesPermissions(RolesPermissions rolesPermissions);
    public Integer findUserNumByRoleId(String roleId);
    public void deleteRolesById(String roleId);
    public void deleteRolesPermissionsById(String roleId);
    public void updateRoles(Roles roles);

}
