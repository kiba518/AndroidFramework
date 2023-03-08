package com.kiba.framework.fragment.mvp;

import android.view.View;

import com.kiba.framework.fragment.mvp.base.SampleContract;

public class SamplePresenterImpl implements SampleContract.Presenter {

    private SampleContract.View mView;

    public SamplePresenterImpl(SampleContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void checkData() {
        
    }
}
