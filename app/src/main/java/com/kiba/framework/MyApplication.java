package com.kiba.framework;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.kiba.framework.comm.utility.AppConfig;
import com.kiba.framework.comm.utility.LogHelper;
import com.xuexiang.xui.XUI;

import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class MyApplication extends Application {

    public String ApplicationCustomName="启动Application";
    public static Context context;//全局上下文
    public static List<Activity> activityList = new ArrayList<Activity>();//用于存放所有启动的Activity的集合
    public static ApplicationInfo applicationInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

        context = getApplicationContext();

        PackageManager packageManager = getApplicationContext().getPackageManager();
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
            LogHelper.LogHelperError("获取applicationInfo报错",e);
        }

        CrashExceptionHandler.getInstance().init(this);
        AppConfig.WebApiCommUrl = GetProperties("WebApiCommUrl");

    }
    public static String GetProperties(String propertyName) {
        Properties props = new Properties();
        String serviceUrl = null;
        try {
            InputStream in =context.getAssets().open("appConfig.properties");
            props.load(in);
            String vaule = props.getProperty(propertyName);
            serviceUrl = new String(vaule.getBytes("ISO-8859-1"), "gbk");
        } catch (IOException e) {
            e.printStackTrace();
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("错误");
            dialog.setMessage("读取配置文件失败");
            dialog.setCancelable(false);
            removeALLActivity();
        }
        return serviceUrl;
    }
    /**
     * 销毁所有的Activity
     */
    public static void removeALLActivity() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
