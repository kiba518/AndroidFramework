package com.kiba.framework.fragment.mvp.base;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
