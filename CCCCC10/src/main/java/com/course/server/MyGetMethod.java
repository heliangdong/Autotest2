package com.course.server;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class MyGetMethod {

    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)
    public String getCookies(HttpServletResponse response){

        //设置cookies
        Cookie cookie=new Cookie("login","true");
        response.addCookie(cookie);
        return "恭喜你获得Cookies信息";
    }

//携带COOKIES访问

    @RequestMapping(value = "/getwithCookies",method = RequestMethod.GET)
    public String getwithcookies(HttpServletRequest request){
        Cookie[] cookie= request.getCookies();
        if(Objects.isNull(cookie)){
            return "你必须要携带COOKIES才能访问222222";
        }
        for(Cookie cookie1:cookie){
            if(cookie1.getName().equals("login")&&cookie1.getValue().equals("true")){
                return "你已经成功携带COOKIES登录了";

            }
        }
        return "你必须要携带COOKIES才能访问";
    }

    //携带参数的请求接口
    @RequestMapping(value = "/getwithparam",method = RequestMethod.GET)
    public Map<String,Integer> getwithparam(@RequestParam Integer start,@RequestParam Integer end){
        Map<String,Integer> mylist=new HashMap<>();
        mylist.put("鞋子",500);
        mylist.put("衣服",200);
        mylist.put("袜子",200);
        return  mylist;
    }

}
