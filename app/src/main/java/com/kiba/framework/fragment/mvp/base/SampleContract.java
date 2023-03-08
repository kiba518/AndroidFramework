package com.kiba.framework.fragment.mvp.base;

public interface SampleContract {

    interface Presenter extends BasePresenter {

        //检查数据是否有效
        void checkData();

    }

    interface View extends BaseView<SampleContract.Presenter> {
        //显示加载中
        void showLoading();
        //显示错误界面
        void showError();

    }
}
