package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class myCookiesForPost {
    private String url;
    private ResourceBundle bundle;
    private CookieStore store;
    @BeforeTest
    public void beforeTest(){
        bundle=ResourceBundle.getBundle("application", Locale.CHINA);
        url=bundle.getString("test.url");
    }
    @Test
    public void testGetCookies() throws IOException {
        String result;
        String uri=bundle.getString("getCookies.uri");
        String testUrl=this.url+uri;
        HttpGet get= new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response=client.execute(get);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //获取cookies 信息
        this.store= client.getCookieStore();
        List<Cookie> cookielist =store.getCookies();
        for(Cookie cookie:cookielist){
            String name= cookie.getName();
            String value=cookie.getValue();
            System.out.println("Cookies name="+name+"   ;Cookies value="+value );
        }

    }
    @Test(dependsOnMethods = {"testGetCookies"})
    public void testPostMethod() throws IOException {
        String uri=bundle.getString("test.post.with.cookies");
        String testUrl=this.url+uri;
        DefaultHttpClient client=new DefaultHttpClient();
        HttpPost post=new HttpPost(testUrl);
        //添加参数
        JSONObject param=new JSONObject();
        param.put("name","huawuque");
        param.put("age","18");
        //设置请求头信息
        post.setHeader("content-type","application/json");
        //将参数添加到方法中
        StringEntity entity=new StringEntity(param.toString());
        post.setEntity(entity);
        //声明一个对象来存储相应结果
        String result;
        //设置cookies信息
        client.setCookieStore(this.store);
        HttpResponse response=client.execute(post);
        result=EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //将响应结果字符串转换成json对象
       // JSONObject resultJson= new JSONObject(result);
       // String success= (String) resultJson.get("huawuque");
       // String status= (String) resultJson.get("status");
      //  Assert.assertEquals("success",success);
       // Assert.assertEquals("1",status);
    }
}
