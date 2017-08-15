package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Roles;

import java.util.List;

public interface RolesMapper extends Mapper<Roles> {
    List<Roles> selecteByUserId(String id);
}