package com.ddot.controller;

import com.ddot.common.Const;
import com.ddot.pojo.User;
import com.ddot.pojo.UserInfo;
import com.ddot.service.UserService;
import com.ddot.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

//@Controller
@RestController     //所有都方法都返回json
@RequestMapping("/portal")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user/login.do")
    public ServerResponse login(String username, String password, HttpSession session){

       ServerResponse serverResponse = userService.loginLogin(username, password);

       //判断登录是否成功
        if (serverResponse.ifSuccess()){
            session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
        }
       return serverResponse;

    }

    @RequestMapping("/user/register.do")
    public ServerResponse register(User user){
       return userService.registerLogic(user);
    }

   /* //登录 login.do?username=xxx&password=xxx
    @RequestMapping(value = "/login.do",method = RequestMethod.GET)
    //@ResponseBody  该方法返回json字符串
    public UserInfo login(@RequestParam(value = "username",required = true,defaultValue = "") String username,
                        @RequestParam("password") String password){

        UserInfo userInfo = new UserInfo();

        userInfo.setId(1);
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        return userInfo;
    }*/

    @RequestMapping(value = "/restful/login/{username}/{password}")
    public UserInfo loginRestful(@PathVariable("username") String username,
                                 @PathVariable("password") String password){

        UserInfo userInfo = new UserInfo();

        userInfo.setId(1);
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        return userInfo;
    }
}
