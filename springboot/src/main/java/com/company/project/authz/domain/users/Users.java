package com.company.project.authz.domain.users;

import com.company.project.authz.domain.AbstractDomain;
import com.company.project.authz.domain.shared.BeanProvider;

import java.util.Date;
import java.util.List;


public class Users extends AbstractDomain {
    private static final long serialVersionUID = -3990278799194556012L;

    private transient UsersRepository usersRepository = BeanProvider.getBean(UsersRepository.class);

    private String username;
    private String password;


    private boolean defaultUser;
    private Date lastLoginTime;

    public Users() {
    }


    public List<Roles> rolesList() {
        return usersRepository.findUsersRolesList(this.guid);
    }


    public String username() {
        return username;
    }

    public Users username(String username) {
        this.username = username;
        return this;
    }

    public String password() {
        return password;
    }

    public Users password(String password) {
        this.password = password;
        return this;
    }

    public boolean defaultUser() {
        return defaultUser;
    }

    public Users defaultUser(boolean defaultUser) {
        this.defaultUser = defaultUser;
        return this;
    }

    public Date lastLoginTime() {
        return lastLoginTime;
    }

    public Users lastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }
}
