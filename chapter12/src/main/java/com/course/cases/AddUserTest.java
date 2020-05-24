package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DateBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.testng.annotations.Test;


import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class AddUserTest {
    @Test(dependsOnGroups = "loginTrue", description = "添加用户接口测试")
    public void addUser() throws IOException {
        SqlSession session = DateBaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase", 1);
        System.out.printf(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);
        //发送请求
        String result = getResult(addUserCase);
        //验返回证结果
        User user=session.selectOne("addUser",addUserCase);
        System.out.println(user.toSting());
        assertEquals(addUserCase.getExpected(),result);

    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.addUserUrl);
        JSONObject param= new JSONObject();
        param.put("userName",addUserCase.getUsername());
        param.put("password",addUserCase.getPassword());
        param.put("age",addUserCase.getAge());
        param.put("sex",addUserCase.getSex());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());
        //设置头信息
        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"UTF-8");
        post.setEntity(entity);
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;//存放返回结果
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        result=EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.printf(result);

        return result;
    }



}
