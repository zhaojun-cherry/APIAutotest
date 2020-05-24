package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DateBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取userID=1的用户信息")
    public void getUserInfo() throws IOException {
        SqlSession session= DateBaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase=session.selectOne("getUserInfoCase",1);
        System.out.printf(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);
        JSONArray resultJson= getJsonResult(getUserInfoCase);
        User user=session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        List userList=new ArrayList();
        userList.add(user);
        JSONArray jsonArray =new JSONArray(userList);
        Assert.assertEquals(resultJson,jsonArray);
    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.getUserInfoUrl);
        JSONArray param=new JSONArray();
        param.put(Integer.parseInt("id"),getUserInfoCase.getUserId());
        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        List resultlist= Arrays.asList(result);
        JSONArray array=new JSONArray(resultlist);
        return array;
    }


}
