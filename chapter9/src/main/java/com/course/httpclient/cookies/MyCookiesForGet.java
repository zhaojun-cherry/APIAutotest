package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {
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
        result=EntityUtils.toString(response.getEntity(),"utf-8");
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
    public void testGetWithCookies() throws IOException {
        String uri=bundle.getString("test.get.with.cookies");
        String testUrl=this.url+uri;
        String result;
        HttpGet get=new HttpGet(testUrl);
        DefaultHttpClient client=new DefaultHttpClient();
        client.setCookieStore(this.store);
        HttpResponse response=client.execute(get);
        //获取响应状态码
        int statuscode=response.getStatusLine().getStatusCode();
        System.out.println("statuscode="+ statuscode);
        if(statuscode==200){
            result=EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }

    }

}


