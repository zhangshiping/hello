package com.company.project.service.impl;

import com.company.project.dao.PermissionsMapper;
import com.company.project.dao.RolesMapper;
import com.company.project.dao.RolesPermissionsMapper;
import com.company.project.dao.UsersMapper;
import com.company.project.model.Permissions;
import com.company.project.model.Roles;
import com.company.project.model.RolesPermissions;
import com.company.project.model.Users;
import com.company.project.service.AuthorizeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2017/08/10.
 */
@Service
@Transactional
public class AuthorizeServiceImpl implements AuthorizeService {
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private RolesMapper rolesMapper;
    @Resource
    private PermissionsMapper permissionsMapper;
    @Resource
    private RolesPermissionsMapper rolesPermissionsMapper;

    @Override
    public Users findByName(String username) {
        return usersMapper.selectedByName(username);
    }

    @Override
    public List<Roles> findRolesById(String id) {
        return rolesMapper.selecteByUserId(id);
    }

    @Override
    public Roles findRoleById(String roleId) {
        return rolesMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public List<Roles> findAllRoles() {
        return rolesMapper.selectAll();
    }

    @Override
    public List<Permissions> findPermissionsById(String id) {
        return permissionsMapper.selectedPermissionsByRoleId(id);
    }

    @Override
    public List<Permissions> findAllPermission() {
        return permissionsMapper.selectAll();
    }

    @Override
    public void addRoles(Roles roles) {
        rolesMapper.insert(roles);
    }

    @Override
    public void addRolesPermissions(RolesPermissions rolesPermissions) {
        rolesPermissionsMapper.insert(rolesPermissions);
    }

    @Override
    public Integer findUserNumByRoleId(String roleId) {
        return usersMapper.selectUserNumByRoleId(roleId);
    }

    @Override
    public void deleteRolesById(String roleId) {
        rolesMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public void deleteRolesPermissionsById(String roleId) {
        rolesPermissionsMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public void updateRoles(Roles roles) {
        rolesMapper.updateByPrimaryKeySelective(roles);
    }
}
