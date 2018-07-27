package com.course.httpclient.demo;

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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Mycookiesforpost {
    private String url;
    private ResourceBundle bundle;
    private CookieStore store;
    @BeforeClass
    public void beforetest(){
        //去配置文件拿变量
        bundle=ResourceBundle.getBundle("application",Locale.CANADA);
        url=bundle.getString("test.ur");
    }
    @Test
    public  void test() throws IOException {
        String uri=bundle.getString("getcookies.url");
//URL拼接
        String testurl=this.url+uri;
        HttpGet get=new HttpGet(testurl);
        //发起GET

        DefaultHttpClient client=new DefaultHttpClient();

        HttpResponse response= client.execute(get);
        //接受GET
        String result= EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //getCOOKER
        store= client.getCookieStore();
        List<Cookie> cookieList=store.getCookies();

        for(Cookie cookie:cookieList){
            String name=cookie.getName();
            String value=cookie.getValue();
            System.out.println("name 是"+name);
            System.out.println("value 是"+value);
        }
    }
    @Test(dependsOnMethods = {"test"})
    public void testpost() throws IOException {
        String uri=bundle.getString("post.ur");
        //拼接地址
        String testurl=this.url+uri;

        //声明一个client对象，用来执行方法
DefaultHttpClient client=new DefaultHttpClient();
        //声明一个方法，这个方法就是POST方法
        HttpPost post=new HttpPost(testurl);
        //添加参数
        JSONObject parm=new JSONObject();
        parm.put("name","zhangsan");
        parm.put("age","18");

        //设置请求头信息  设置请求头
        post.setHeader("content-type","application/json");

        //将参数信息添加到方法
        StringEntity entity=new StringEntity(parm.toString(),"utf-8");
                post.setEntity(entity);
        //声明一个对象，进行相应结果存储

        String result;
        //设置COOKIES信息

        client.setCookieStore(this.store);
        //执行POST方法

        HttpResponse response=client.execute(post);
        //获取相应结果
        String result1= EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("result1是"+result1);


        //将返回的相应结果字符串转为JSON对象
        JSONObject resultjson =new JSONObject(result1);

        //获取结果集
        String success= (String) resultjson.get("zhangsan");
        String status= (String) resultjson.get("status");
        //具体的判断返回结果
        Assert.assertEquals("success",success);
        Assert.assertEquals("1",status);



    }
}
