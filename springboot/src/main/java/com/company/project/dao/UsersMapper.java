package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Users;

public interface UsersMapper extends Mapper<Users> {
    Users selectedByName(String username);
    Integer selectUserNumByRoleId(String roleId);
}