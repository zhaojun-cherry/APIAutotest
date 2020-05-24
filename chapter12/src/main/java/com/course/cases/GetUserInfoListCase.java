package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DateBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetUserInfoListCase {
    @Test(dependsOnGroups = "loginTrue",description = "获取用户性别为男的信息")
    public void getUserInfoList() throws IOException {
        SqlSession session= DateBaseUtil.getSqlSession();
        GetUserListCase getUserListCase=session.selectOne("getUserList",1);
        System.out.printf(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
        //发送请求
        JSONArray resultjson=getJsonResult(getUserListCase);
        //验证结果
        List<User> userList =session.selectList(getUserListCase.getExpected(),getUserListCase);
        for(User u:userList){
            System.out.println("获取的user"+u.toSting());
        }
        JSONArray userListJson = new JSONArray(userList);
        Assert.assertEquals(userListJson.length(),resultjson.length());
        for(int i=0;i<resultjson.length();i++){
            JSONObject expect= (JSONObject) resultjson.get(i);
            JSONObject actual= (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(),actual.toString());
        }


    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.getUserListUrl);
        JSONObject param=new JSONObject();
        param.put("userName",getUserListCase.getUsername());
        param.put("sex",getUserListCase.getSex());
        param.put("age",getUserListCase.getAge());
        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        JSONArray jsonArray=new JSONArray(result);
        return jsonArray;
    }
}
