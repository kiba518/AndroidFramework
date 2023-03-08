package com.kiba.framework.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;

import com.kiba.framework.R;
import com.kiba.framework.base_activity.BaseActivity;
import com.kiba.framework.fragment.mvp.SampleFragment;
import com.kiba.framework.fragment.mvp.SamplePresenterImpl;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.WidgetUtils;

import butterknife.ButterKnife;

public class MvpAcitvity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    private void init() {
        //初始化view
        SampleFragment fragment = new SampleFragment();

        //初始化presenter
        new SamplePresenterImpl(fragment);
    }
}
