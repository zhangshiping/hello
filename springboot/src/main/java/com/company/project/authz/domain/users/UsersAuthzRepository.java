package com.company.project.authz.domain.users;

import java.util.List;


public interface UsersAuthzRepository extends UsersRepository {

    List<Users> findUsersByUsername(String username);

    List<Roles> findAvailableRolesList();

    Users findByUsername(String username);

    int saveUsers(Users users);

    Roles findRolesByGuid(String guid);

    void insertUserRoles(int userId, int rolesId);
}