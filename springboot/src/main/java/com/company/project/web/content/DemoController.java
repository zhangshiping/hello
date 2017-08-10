package com.company.project.web.content;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public Result list() {
        PageHelper.startPage(0, 10);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public String list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,Model model) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
       model.addAttribute("list",list);
//        PageInfo pageInfo = new PageInfo(list);
        return "demo";
    }
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(Model model){
        model.addAttribute("name","zsp");
        return "demo";
    }
}
