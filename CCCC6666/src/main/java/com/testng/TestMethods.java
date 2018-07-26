package com.testng;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestMethods {

    @Test
    public void test1(){
        Assert.assertEquals(1,1);
    }

    @Test
    public void test2(){
        Assert.assertEquals(1,2);
    }

    @Test
    public void logDemo(){
        Reporter.log("这是我自己写的日志哦");
        throw new RuntimeException("这是运行时的日志异常");
    }



}
