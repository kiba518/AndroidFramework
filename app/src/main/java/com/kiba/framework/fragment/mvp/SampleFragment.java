

package com.kiba.framework.fragment.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kiba.framework.R;
import com.kiba.framework.base_activity.BaseFragment;
import com.kiba.framework.fragment.mvp.base.SampleContract;


public class SampleFragment extends BaseFragment implements SampleContract.View{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SampleContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews() {

    }


    @Override
    public void setPresenter(SampleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {
        //change UI
    }
}

