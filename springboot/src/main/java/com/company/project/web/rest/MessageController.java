package com.company.project.web.rest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by eayon on 2017-08-07.
 */
@RestController
@RequestMapping(value = "/Message")
public class MessageController {

    @ApiOperation(value = "返回测试消息",notes = "仅仅用来测试")
    @ApiImplicitParam(name = "msg", value = "消息内容",required = false,dataType = "String" )
    @RequestMapping(value = "/{msg}", method = RequestMethod.GET)
    public String message(String  msg){

        if(msg == null) {
            msg ="没有有效的消息";
        }

        return "msg is "+ msg;
    }
}
