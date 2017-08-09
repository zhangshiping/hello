package com.company.project.web.content;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.infrastructure.tools.MD5Utils;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
* Created by CodeGenerator on 2017/07/10.
*/
@RestController
@RequestMapping("/user")
public class
UserController {

    @Resource
    private UserService userService;

    @PostMapping("/add")
    public Result add(String username,String nickName,Integer sex,String password)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = new User();
        if(username!=null || !"".equals(username)){
            user.setUsername(username);
        }
        if(nickName!=null || !"".equals(nickName)){
            user.setNickName(nickName);
        }
        if(password!=null || !"".equals(password)){
            user.setPassword(MD5Utils.EncoderByMd5(password));
        }
        if(sex!=null || !"".equals(sex)){
            user.setSex(sex);
        }
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @GetMapping("/list")
    public Result list() {
        PageHelper.startPage(0, 10);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    @PostMapping("/search")
    public Result seach(String username, String nickName, String sex  ) {
         User user = new User();

        if (StringUtils.isNotEmpty( username )){
            user.setUsername( username );
        }
        if (StringUtils.isNotEmpty( nickName )){
            user.setNickName( nickName );
        }
//        if (StringUtils.isNotEmpty( username )){
//            user.setRegisterDate( registDate );
//        }
        List<User> search = userService.search( user );

        PageInfo pageInfo = new PageInfo(search);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
