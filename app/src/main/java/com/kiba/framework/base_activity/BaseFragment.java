package com.kiba.framework.base_activity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.kiba.framework.R;
import com.kiba.framework.comm.callback.ICallback_Boolean;
import com.kiba.framework.comm.utility.AppConfig;
import com.kiba.framework.comm.utility.XToastUtils;
import com.xuexiang.xaop.annotation.MemoryCache;
import com.xuexiang.xaop.cache.XMemoryCache;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xui.utils.DrawableUtils;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.actionbar.TitleUtils;
import com.xuexiang.xui.widget.progress.loading.IMessageLoader;

import java.net.URLEncoder;

public abstract class BaseFragment extends XPageFragment {
    /**
     * 消息加载框
     */
    private IMessageLoader mMessageLoader;
    //返回值【/storage/emulated/0/Android/data/com.kiba.framework/files】
    public String FilesPath_External;

    //返回值【/storage/emulated/0】
    public String FilesPath_Internal;
    @Override
    protected void initPage() {
        FilesPath_External = this.getActivity().getExternalFilesDir("").getAbsolutePath();
        FilesPath_Internal  = Environment.getExternalStorageDirectory().getPath();
        initTitle();
        initViews();
        initListeners();
    }

    protected TitleBar initTitle() {
        return TitleUtils.addTitleBarDynamic((ViewGroup) getRootView(), getPageTitle(), v ->{
            FragmentManager fragmentManager = this.getActivity().getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0 ){
                fragmentManager.popBackStack();
            }
        } ).setLeftImageDrawable(getNavigationBackDrawable(R.attr.xui_actionbar_ic_navigation_back));
    }

    @MemoryCache
    protected Drawable getNavigationBackDrawable(int navigationBackId) {
        return DrawableUtils.getSupportRTLDrawable(ThemeUtils.resolveDrawable(getContext(), navigationBackId));
    }

    public IMessageLoader getMessageLoader() {
        if (mMessageLoader == null) {
            mMessageLoader = WidgetUtils.getMiniLoadingDialog(getContext());
        }
        return mMessageLoader;
    }

    public IMessageLoader getMessageLoader(String message) {
        if (mMessageLoader == null) {
            mMessageLoader = WidgetUtils.getMiniLoadingDialog(getContext(), message);
        } else {
            mMessageLoader.updateMessage(message);
        }
        return mMessageLoader;
    }

    @Override
    protected void initListeners() {

    }

    /**
     * 关闭当前fragment所在的activity,请谨慎使用！！！
     */
    protected void finishActivity() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        if (mMessageLoader != null) {
            mMessageLoader.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        //屏幕旋转时刷新一下title
        super.onConfigurationChanged(newConfig);
        XMemoryCache.getInstance().clear();
        ViewGroup root = (ViewGroup) getRootView();
        if (root.getChildAt(0) instanceof TitleBar) {
            root.removeViewAt(0);
            initTitle();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void hideCurrentPageSoftInput() {
        if (getActivity() == null) {
            return;
        }
        // 记住，要在xml的父布局加上android:focusable="true" 和 android:focusableInTouchMode="true"
        KeyboardUtils.hideSoftInputClearFocus(getActivity().getCurrentFocus());
    }

    public void ShowMessage(String msg) {
        XToastUtils.info(msg);
    }
    public void ShowMessage_Snackbar(View view, String msg) {
        Snackbar.make(view ,msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    public void ShowMessage_Toast(String msg) {
        Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    public void Confirm(String msg , ICallback_Boolean callback ){

        if(msg.isEmpty())
        {
            msg="是否确认";
        }
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this.getActivity());
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
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this.getActivity());
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
}

