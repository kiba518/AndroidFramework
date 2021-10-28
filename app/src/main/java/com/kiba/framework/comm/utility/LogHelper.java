package com.kiba.framework.comm.utility;

import android.os.Environment;
import android.widget.Toast;

import com.kiba.framework.MyApplication;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public  class LogHelper {
    public  static void LogHelperError(String Title, Throwable throwable)  {
        Toast.makeText(MyApplication.context, "loghelper捕获到异常" , Toast.LENGTH_SHORT).show();
        String time = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
        String fileName = "ZJDCrashException-log-" + time + ".txt";
        StringBuilder result=new StringBuilder();
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        throwable.printStackTrace(pw);
        pw.close();
        result.append(writer.toString());
        // 有无SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath() + "/ZJDCrashException/";
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path + fileName,true);
                byte[] c=new byte[2];
                c[0]=0x0d;
                c[1]=0x0a;//用于输入换行符的字节码
                String t=new String(c);
                String errorStr=t+result.toString();
                fos.write(errorStr.getBytes());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    public  static void LogHelperDebug(String Title, String Message)  {
        String time = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
        String fileName = "ZJDCrashDebug-log-" + time + ".txt";
        StringBuilder result=new StringBuilder();
        result.append(Message+"====="+Title+"====="+new Date().toString());
        // 有无SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath() + "/ZJDCrashDebug/";
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path + fileName,true);
                byte[] c=new byte[2];
                c[0]=0x0d;
                c[1]=0x0a;//用于输入换行符的字节码
                String t=new String(c);
                String debugStr=t+result.toString();
                fos.write(debugStr.getBytes());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

}
