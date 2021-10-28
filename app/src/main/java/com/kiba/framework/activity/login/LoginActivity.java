package com.kiba.framework.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.kiba.framework.R;
import com.kiba.framework.activity.main.MainActivity;

import com.kiba.framework.activity.protocol.ProtocolActivity;
import com.kiba.framework.base_activity.BaseActivity;
import com.kiba.framework.comm.db_local_table.Cache_User;
import com.kiba.framework.comm.dto.LoginCommand;
import com.kiba.framework.comm.dto.LoginCommandResult;
import com.kiba.framework.comm.utility.AppConfig;
import com.kiba.framework.comm.utility.HttpHelper;
import com.kiba.framework.comm.utility.JsonHelper;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {


    DbManager db;
    @BindView(R.id.txt_username)
    EditText txt_username;
    @BindView(R.id.txt_password)
    EditText txt_password;
    @BindView(R.id.pb_loading)
    ProgressBar pb_loading;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.cb_protocol)
    CheckBox cb_protocol;

    //XUI的绑定页面的模式
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        db = x.getDb(daoConfig);

        try {
            Cache_User cUser = db.selector(Cache_User.class)
                    .orderBy("id", true).findFirst();
            if (cUser != null) {
                txt_username.setText(cUser.loginName);
                txt_password.setText(cUser.password);
                txt_password.requestFocus();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_login)
    void login() {
        if (cb_protocol.isChecked()) {
            pb_loading.setVisibility(View.VISIBLE);
            btn_login.setEnabled(false);

            try {
                LoginCommand cmd = new LoginCommand();
                cmd.LoginName = txt_username.getText().toString();
                cmd.Password = txt_password.getText().toString();
                String cmdStr = JsonHelper.Serialize(cmd);

                HttpHelper.post(AppConfig.WebApiCommUrl, GetHttpParam("LoginCommand=", cmdStr), con -> {
                    try {
                        LoginCommandResult baseResult = JsonHelper.Deserialize(LoginCommandResult.class, con);
                        if (baseResult.IsSuccess) {
                            Cache_User user = new Cache_User();
                            user.id = 0;
                            user.loginName = cmd.LoginName;
                            user.password = cmd.Password;
                            CacheLoginInfo(user);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();//关闭当前活动
                        } else {
                            runOnUiThread(() -> {
                                ShowMessage("登录失败，" + baseResult.Message);
                            });

                        }
                        runOnUiThread(() -> {
                            btn_login.setEnabled(true);
                            pb_loading.setVisibility(View.GONE);
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        runOnUiThread(() -> {
                            ShowMessage("登录失败，暂时也先调转到主页" + ex.getMessage());
                            btn_login.setEnabled(true);
                            pb_loading.setVisibility(View.GONE);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        });
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    ShowMessage("登录失败" + e.getMessage());
                    btn_login.setEnabled(true);
                    pb_loading.setVisibility(View.GONE);
                });
            }
        } else {
            ShowMessage("请阅读用户协议和隐私政策");
        }
    }

    public void CacheLoginInfo(Cache_User user) {
        try {
            List<Cache_User> list = db.selector(Cache_User.class)
                    .where("realId", "=", user.id).orderBy("id").findAll();

            if (list != null && list.size() > 0) {
                Cache_User cUser = list.get(0);
                cUser.setRealId(user.id);
                cUser.setName(user.name);
                cUser.setPassword(user.password);
                cUser.setPassword(txt_password.getText().toString());
                cUser.setLoginName(user.loginName);
                db.update(cUser);//保存对象关联数据库生成的id
            } else {

                Cache_User cUser = new Cache_User();
                cUser.setRealId(user.id);
                cUser.setName(user.name);
                cUser.setPassword(user.password);
                cUser.setPassword(txt_password.getText().toString());
                cUser.setLoginName(user.loginName);
                db.saveBindingId(cUser);//保存对象关联数据库生成的id
            }

        } catch (Throwable e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                ShowMessage("登录失败" + e.getMessage());
            });
        }
    }

    @OnClick(R.id.forget_password)
    void forget_password() {
        ShowMessage("请联系管理员");
    }

    @OnClick(R.id.txt_user_protocol)
    void txt_user_protocol_click() {
        Intent intent = new Intent(LoginActivity.this, ProtocolActivity.class);
        intent.putExtra("type", "user");
        startActivity(intent);
    }

    @OnClick(R.id.txt_privacy_protocol)
    void txt_privacy_protocol_click() {
        Intent intent = new Intent(LoginActivity.this, ProtocolActivity.class);
        intent.putExtra("type", "privacy");
        startActivity(intent);
    }

    private long mBackPressed;

    /**
     * 点击手机平板的返回键事件
     */
    @Override
    public void onBackPressed() {
        if (mBackPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            ShowMessage("再按一次返回键退出程序");
        }
        mBackPressed = System.currentTimeMillis();
    }
}