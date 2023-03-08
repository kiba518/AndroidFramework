package com.kiba.framework;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.kiba.framework.services.MyService;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.kiba.framework", appContext.getPackageName());
    }
    @Test
    public void servicesTest(){
        //不同实例服务调用，先start，后stop
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent it=new Intent(appContext, MyService.class);
        appContext.startService(it);
        appContext.stopService(it);
        Intent it2=new Intent(appContext, MyService.class);
        appContext.startService(it2);
        appContext.stopService(it2);
        assertEquals("com.kiba.framework", appContext.getPackageName());
    }
    @Test
    public void servicesTest2(){
        //同一实例服务调用，先start，后stop
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent it=new Intent(appContext, MyService.class);
        appContext.startService(it);
        appContext.stopService(it);
        appContext.startService(it);
        appContext.stopService(it);
        assertEquals("com.kiba.framework", appContext.getPackageName());
    }
    @Test
    public void servicesTest3(){
        //不同实例，不调用销毁服务方法，只调用start
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent it=new Intent(appContext, MyService.class);
        appContext.startService(it);
        Intent it2=new Intent(appContext, MyService.class);
        appContext.startService(it2);
        //虽然定义了两个实例，但onCreate没有被重复调用，即，同一类型的service，只有显示调用了stopService才会销毁
        assertEquals("com.kiba.framework", appContext.getPackageName());
    }
}