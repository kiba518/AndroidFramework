package com.kiba.framework.activity.protocol;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.kiba.framework.R;
import com.kiba.framework.base_activity.BaseActivity;

import com.xuexiang.xutil.resource.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProtocolActivity extends BaseActivity {


    @BindView(R.id.txt_protocol)
    TextView txt_protocol;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {  return R.layout.activity_protocol; }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);//设置自定义bar

        Intent intent =getIntent();
        /*取出Intent中附加的数据*/
        String type = intent.getStringExtra("type");
        String title ="";
        if (type.equals("user")) {
            title="用户协议";
            txt_protocol.setText(getUerProtocol());

        } else {
            title="隐私政策";
            txt_protocol.setText(getPrivacyProtocol());
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setTitle(title);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private String getUerProtocol() {
        return ResourceUtils.readStringFromAssert("protocol/user_protocol.txt");
    }


    private String getPrivacyProtocol() {
        return ResourceUtils.readStringFromAssert( "protocol/privacy_protocol.txt");
    }

}