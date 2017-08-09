package com.company.project.service;
import com.company.project.model.User;
import com.company.project.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2017/07/10.
 */
public interface UserService extends Service<User> {
    List<User> search(User user);
}
