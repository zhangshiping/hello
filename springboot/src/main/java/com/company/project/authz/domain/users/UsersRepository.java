package com.company.project.authz.domain.users;


import com.company.project.authz.domain.shared.Repository;

import java.util.List;


public interface UsersRepository extends Repository {

    List<Roles> findUsersRolesList(String usersGuid);

    List<String> findPermissionsByRoles(String rolesGuid);
}