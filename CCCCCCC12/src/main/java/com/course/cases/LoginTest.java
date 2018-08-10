package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {

    @BeforeClass(groups = "loginTrue",description = "测试准备工作，获取对象")
    public void beforeTest(){
        TestConfig.getUserInfoUrl=ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl=ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl=ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl=ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl=ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        TestConfig.defaultHttpClient=new DefaultHttpClient();

    }

    @Test(groups = "loginTrue",description = "loginTrue")
    public  void loginTrue() throws IOException {
        SqlSession session=DatabaseUtil.getSqlSession();
        LoginCase loginCase=session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        String result=getResult(loginCase);

    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.loginUrl);
        JSONObject parm=new JSONObject();
        parm.put("userName",loginCase.getUserName());
        parm.put("password",loginCase.getPassword());
        post.setHeader("content-type","application/json");
        StringEntity entity =new StringEntity(parm.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        //获取响应结果
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        TestConfig.store=TestConfig.defaultHttpClient.getCookieStore();
        return result;


    }
}
