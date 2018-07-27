package com.course.httpclient.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Mycookies {

    private String url;
    private ResourceBundle bundle;
    private CookieStore  store;
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
    public void test2() throws IOException {
      //  bundle=ResourceBundle.getBundle("application",Locale.CANADA);
        String uri=bundle.getString("getcookies.url2");
        String testurl=this.url+uri;

        HttpGet get=new HttpGet(testurl);
        DefaultHttpClient client=new DefaultHttpClient();

        //设置COOKIES
        client.setCookieStore(this.store);

        HttpResponse response=client.execute(get);
        //获取相应吗
        int statusCode=response.getStatusLine().getStatusCode();

        System.out.println("statusCode"+statusCode);

        if(statusCode==200){
            String result= EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println("result是"+result);
        }





    }

}
