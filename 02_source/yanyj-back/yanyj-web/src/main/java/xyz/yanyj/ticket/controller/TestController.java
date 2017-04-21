package xyz.yanyj.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.yanyj.ticket.service.UserService;
import xyz.yanyj.util.StringUtil.JsonUtil;

/**
 *
 * @author yanyj
 * @date 2017-04-13
 **/
@Controller
@RequestMapping(value = "/Test/*")
public class TestController {

    @Autowired
    public UserService userService;


    //访问地址：http://localhost:8080/Test/returnString
    @RequestMapping(value = "returnString", produces = {"text/plain;charset=UTF-8"})
    //produces用于解决返回中文乱码问题，application/json;为json解决中文乱码
    @ResponseBody       //用于返回字符串,不写即返回视图
    public String returnString() {

//        return userService.get("40287d815b7eef9b015b7eef9ebd0000").getUsername();
        return JsonUtil.convertToJson(userService.getUserList());
    }
}
