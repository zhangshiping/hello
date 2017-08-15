package com.company.project.web.content;

import com.company.project.configurer.shiro.OAuth2Token;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.service.dto.LoginDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by eayon on 2017-07-17.
 */
@Controller
@RequestMapping("/")
public class ShiroController {
    private static final Logger LOG = LoggerFactory.getLogger(ShiroController.class);

//    @RequestMapping("index")
//    public String index(){
//        System.out.println("test print .");
//        return "mainIndex";
//    }

    @RequestMapping("unauthorized")
    public String unauthorized(){
        return  "unauthorized";
    }

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(Model model){
        return  "login";
    }

    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Result login(@ModelAttribute("formDto") LoginDto formDto, BindingResult errors){
        UsernamePasswordToken token = formDto.token();
        token.setRememberMe(false);
        try{
            SecurityUtils.getSubject().login(token);
            if (SecurityUtils.getSubject().isAuthenticated()){
                System.out.println("passed");
            }
        }catch (Exception e){
            LOG.debug("Error authenticating.", e);
            errors.rejectValue("username", null, "The username or password was not correct.");
            System.out.println("unautherized");
            return ResultGenerator.genFailResult("登录失败！");
        }
        return ResultGenerator.genSuccessResult(token.toString());
    }
}
