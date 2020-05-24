package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "/",description="这是我全部的get方法")
public class MyGetMethod {
    @RequestMapping(value="/getCookies",method = RequestMethod.GET)
    @ApiOperation(value ="这是可以获取cookies的方法",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        Cookie cookie= new Cookie("logon","true");
        response.addCookie(cookie);
        return "恭喜你获得cookies 信息成功";
    }
    @RequestMapping(value="/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value ="这是携带cookies信息才能访问的get 请求",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if(Objects.isNull(cookies)){
            return "你必须携带cookies 信息来";
        }
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("login")&&cookie.getValue().equals("true")){
                return "恭喜你访问成功";
            }
        }
        return "你必须携带cookies 信息来";
    }
    /**
     * 开发一个需要携带参数才能访问的get 请求
     * 第一种实现方式：url key=value&key=value
     * 模拟获取商品列表
     */
    @RequestMapping(value="/get/with/param",method = RequestMethod.GET)
    @ApiOperation(value ="这是携带参数信息才能访问的get 请求",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,
                                       @RequestParam Integer end){
        Map<String,Integer> myList=new HashMap<>();
        myList.put("shooes",400);
        myList.put("coat",1000);
        myList.put("wazi",20);
        return myList;
    }
}

