package com.kiba.framework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.kiba.framework.activity.login.LoginActivity;
import com.kiba.framework.base_activity.BaseActivity;
import org.xutils.DbManager;
import org.xutils.x;

public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //这里使用常规的视图填充，为了下面的动画可以正常运行
        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);

        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.4f, 1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                InitDatabaseConfig();
                DbManager db = x.getDb(daoConfig);
                // 升级数据库从版本3到版本4，并触发setDbUpgradeListener
                daoConfig.getDbUpgradeListener().onUpgrade(db,3,4);
                //也可以直接删除数据，后面使用时会重新创建
//                try {
//                    db.dropDb();
//                } catch (DbException e) {
//                    e.printStackTrace();
//                }
            }
            @Override
            public void onAnimationEnd(Animation arg0)
            {
                Intent intent =new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();//关闭当前活动
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
        });
    }
}
