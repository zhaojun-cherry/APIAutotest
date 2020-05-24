package com.course.server;

import com.course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;

@RestController
@Api(value = "/",description = "这是我所有的post请求")
@RequestMapping("/v1")
public class MyPostMethod {
       @RequestMapping(value ="/login",method = RequestMethod.POST)
       @ApiOperation(value = "登录接口， 成功后返回cookies信息",httpMethod ="POST" )
        public String login(HttpServletResponse response,
                            @RequestParam(value = "userName",required = true) String userName,
                            @RequestParam(value = "password",required = true) String password
                            ){
           if(userName.equals("junjun")&&password.equals("123456")){
               Cookie cookie=new Cookie("login","true");
               response.addCookie(cookie);
               return "恭喜你登录成功了";
           }
              return "用户名或者密码错误";
        }
        @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
        @ApiOperation(value = "获取用户列表",httpMethod = "POST")
        public String getUserList(HttpServletRequest request,
                                @RequestBody User u){
           User user;
            //获取cookies
           Cookie[] cookies=request.getCookies();
           //验证cookies是否合法
            for (Cookie c: cookies){
                if(c.getName().equals("login")
                        && c.getValue().equals("true")
                        && u.getUserName().equals("zhangsan")
                        && u.getPassword().equals("123456")
                        ){
                  user=new User();
                  user.setName("lisi");
                  user.setAge("18");
                  user.setSex("male");
                  return user.toString();

                }
            }
            return "参数不合法";
        }


}
