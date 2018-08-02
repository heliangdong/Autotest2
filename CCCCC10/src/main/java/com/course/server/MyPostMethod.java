package com.course.server;

import bean.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MyPostMethod {

    Cookie cookie;


    @RequestMapping(value = "/postlogin",method = RequestMethod.POST)
    public String login(HttpServletResponse response, @RequestParam(value = "username",required = true) String username,@RequestParam String password){
        if(username.equals("zhangsan")&&password.equals("123456")){
            cookie=new Cookie("login","true");
            return "恭喜你，登录成功";
        }
        return "登录失败";
    }



    @RequestMapping(value = "/getuser",method = RequestMethod.POST)
    public String GetUser(HttpServletRequest request,@RequestBody User u){

        User user;
        //获取Cookie
       Cookie[] cookies= request.getCookies();
       for(Cookie c:cookies){
           if(c.getName().equals("login")&&c.getValue().equals("true")&&u.getUserName().equals("zhangsan")&&u.getPassword().equals("123456")){
               user=new User();
               user.setName("lisi");
               user.setAge("18");
               user.setSex("man");
               return  user.toString();
           }

       }

       return  "获取失败";
    }
}
