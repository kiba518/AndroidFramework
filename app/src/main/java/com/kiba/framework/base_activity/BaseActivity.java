package com.kiba.framework.base_activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.kiba.framework.MyApplication;
import com.kiba.framework.comm.callback.ICallback_Boolean;
import com.kiba.framework.comm.db_local_table.Cache_User;
import com.kiba.framework.comm.utility.AppConfig;
import com.kiba.framework.comm.utility.XToastUtils;
import com.xuexiang.xpage.base.XPageActivity;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.File;
import java.net.URLEncoder;

public class BaseActivity extends XPageActivity {
    //返回值【/storage/emulated/0/Android/data/com.kiba.framework/files】
    public String FilesPath_External;

    //返回值【/storage/emulated/0】
    public String FilesPath_Internal;
    public Context baseContext;

    public DbManager.DaoConfig daoConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FilesPath_External = getExternalFilesDir("").getAbsolutePath();
        FilesPath_Internal  = Environment.getExternalStorageDirectory().getPath();
        super.onCreate(savedInstanceState);

        showPermissions();
        baseContext = this;
        MyApplication.activityList.add(this);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private static final int PERMISSION_REQ_CODE = 100;

    //请求权限
    public void showPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.CHANGE_NETWORK_STATE,
            }, PERMISSION_REQ_CODE);
        } else {
            // PERMISSION_GRANTED
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQ_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSION_GRANTED
                }
                break;
            default:
                break;
        }
    }
    public void ShowMessage(String msg) {
        XToastUtils.info(msg);
    }
    public void ShowMessage_Snackbar(View view, String msg) {
        Snackbar.make(view ,msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    public void ShowMessage_Toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void Confirm(String msg , ICallback_Boolean callback ){

        if(msg.isEmpty())
        {
            msg="是否确认";
        }
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage(msg);
        //设置确定按钮
        alertdialogbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    callback.Call(true);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        //设置取消按钮
        alertdialogbuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    callback.Call(false);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        final AlertDialog alertdialog1 = alertdialogbuilder.create();
        alertdialog1.show();

    }
    public void Confirm(ICallback_Boolean callback){
        String msg="是否确认";
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage(msg);
        //设置确定按钮
        alertdialogbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    callback.Call(true);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        //设置取消按钮
        alertdialogbuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    callback.Call(false);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        final AlertDialog alertdialog1 = alertdialogbuilder.create();
        alertdialog1.show();

    }
    public String GetHttpUrl_Comm(String conmmandtype,String jsoncontent) {
        String url = AppConfig.WebApiCommUrl +"?"+ GetHttpParam(conmmandtype,jsoncontent);
        return url;
    }
    public String GetHttpParam(String conmmandtype,String jsoncontent) {
        String param =  String.format("conmmandtype=%s&jsoncontent=%s", conmmandtype, URLEncoder.encode(jsoncontent));
        return param;
    }

    public void InitDatabaseConfig(){
        File file = new File(FilesPath_External,"database");

        daoConfig = new DbManager.DaoConfig()
                .setDbName("survey.db")
                .setDbDir(file) // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbVersion(3)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        try {
                            db.addColumn(Cache_User.class,"password");
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });
    }
}

