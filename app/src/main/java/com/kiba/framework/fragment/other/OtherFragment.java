package com.kiba.framework.fragment.other;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.kiba.framework.R;
import com.kiba.framework.base_activity.BaseFragment;
import com.kiba.framework.comm.utility.AppConfig;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import java.io.File;


@Page(name = "其他")
public class OtherFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initViews() {
        TitleBar titleBar = super.initTitle();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
