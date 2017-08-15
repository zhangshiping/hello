package com.company.project.web.content;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Permissions;
import com.company.project.model.Roles;
import com.company.project.model.RolesPermissions;
import com.company.project.model.Users;
import com.company.project.service.AuthorizeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10 0010.
 */
@RestController
@RequestMapping("/authorize")
public class AuthorizeController {

    @Resource
    AuthorizeService authorizeService;

    /*登录后审核用户的权限*/
    @RequestMapping(value = "/check",method = RequestMethod.GET)
    public Users checkup(String username){
        Users users = authorizeService.findByName(username);
        List<Roles> roles = authorizeService.findRolesById(users.getId());

        Roles role = null;
        for(int i=0;i<roles.size();i++){
            role = roles.get(i);
            List<Permissions> permissions = authorizeService.findPermissionsById(role.getId());
            role.setPermissions(permissions);
        }
        users.setRoles(roles);
        return users;
    }

    /**
     * 添加角色前加载权限列表
     * @return
     */
    @RequestMapping(value = "/permissionsList",method = RequestMethod.GET)
    public List<Permissions> permissionsList(){
        return authorizeService.findAllPermission();
    }

    /**
     * 添加角色
     * @param guid
     * @param archived
     * @param version
     * @param roleName
     * @param permissionIds
     * @return
     */
    @RequestMapping(value = "/addRoles",method = RequestMethod.POST)
    public Result addRoles(String guid,Boolean archived,Integer version,String roleName,String[] permissionIds){

        Roles roles = new Roles();
        if(guid!=null || !"".equals(guid)){
            roles.setGuid(guid);
        }
        if(archived!=null || !"".equals(archived)){
            roles.setArchived(archived);
        }
        if(version!=null || !"".equals(version)){
            roles.setVersion(version);
        }
        if(roleName!=null || !"".equals(roleName)){
            roles.setRoleName(roleName);
        }
        //添加角色
        authorizeService.addRoles(roles);

        if(permissionIds!=null || !"".equals(permissionIds)){
            RolesPermissions rolesPermissions=new RolesPermissions();
            for(int i=0;i<permissionIds.length;i++){
                rolesPermissions.setRolesId(roles.getId());
                rolesPermissions.setPermissionId(permissionIds[i]);
                //添加中间表roles_permissions信息
                authorizeService.addRolesPermissions(rolesPermissions);
            }

        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 删除角色前，查询该角色下关联的账户数量：=0 可以删除，>0 不删除
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/queryUserNum",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public Integer queryUserNum(String roleId){
        return authorizeService.findUserNumByRoleId(roleId);
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/deleteRoles",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public Result deleteRoleByRoleId(String roleId){
        authorizeService.deleteRolesById(roleId);
        authorizeService.deleteRolesPermissionsById(roleId);
        return ResultGenerator.genSuccessResult();
    }


    /**
     * 加载所有角色
     * @return
     */
    @RequestMapping(value = "/rolesList",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public List<Roles> rolesList(){
        return authorizeService.findAllRoles();

    }

    /**
     * 修改角色前加载角色信息
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/queryRolesById",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public Roles queryRolesById(String roleId){
        return authorizeService.findRoleById(roleId);
    }

    /**
     * 修改角色前加载该角色的权限信息
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/queryRolesPermissionsById",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public List<Permissions> queryRolesPermissionsById(String roleId){
        return authorizeService.findPermissionsById(roleId);
    }


    /**
     * 修改角色
     * @param rolesId
     * @param guid
     * @param archived
     * @param version
     * @param roleName
     * @param permissionIds
     * @return
     */
    @RequestMapping(value = "/updateRoles",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Result updateRoles(String rolesId,String guid,Boolean archived,Integer version,String roleName,String[] permissionIds ){
        Roles roles = new Roles();
        if(rolesId!=null || !"".equals(rolesId)){
            roles.setId(rolesId);
        }
        if(guid!=null || !"".equals(guid)){
            roles.setGuid(guid);
        }
        if(archived!=null || !"".equals(archived)){
            roles.setArchived(archived);
        }
        if(version!=null || !"".equals(version)){
            roles.setVersion(version);
        }
        if(roleName!=null || !"".equals(roleName)){
            roles.setRoleName(roleName);
        }
        authorizeService.updateRoles(roles);
        //先删除rolespermissions再重新添加
        authorizeService.deleteRolesPermissionsById(rolesId);
        if(permissionIds!=null || !"".equals(permissionIds)){
            RolesPermissions rolesPermissions=new RolesPermissions();
            for(int i=0;i<permissionIds.length;i++){
                rolesPermissions.setRolesId(roles.getId());
                rolesPermissions.setPermissionId(permissionIds[i]);
                //添加中间表roles_permissions信息
                authorizeService.addRolesPermissions(rolesPermissions);
            }

        }
        return ResultGenerator.genSuccessResult();
    }



}
