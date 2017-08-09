package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.model.ActiveUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
@RestController
@RequestMapping("Authorize")
public class AuthorizeController {


    @RequestMapping(value = "check",method = RequestMethod.GET)
    public Result checkup(String username){
        ActiveUser activeUser = new ActiveUser();
        //Users users = usersService.findUserByName(userName);
        //String id = users.getId();
        //activeUsers.setId(id);
        //activeUsers.setUsername(username);

        //List<String> roles = usersService.findRolesByName(userName);
        return null;
    }
}
