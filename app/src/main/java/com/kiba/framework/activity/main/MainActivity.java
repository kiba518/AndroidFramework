package com.kiba.framework.activity.main;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kiba.framework.R;
import com.kiba.framework.base_activity.BaseActivity;

import com.kiba.framework.activity.login.LoginActivity;
import com.kiba.framework.activity.main.adapter.DrawerAdapter;
import com.kiba.framework.activity.main.adapter.DrawerItem;
import com.kiba.framework.activity.main.adapter.SimpleItem;
import com.kiba.framework.activity.main.adapter.SpaceItem;

import com.kiba.framework.fragment.newslist.NewsListEditFragment;

import com.kiba.framework.fragment.other.OtherFragment;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.guidview.GuideCaseQueue;
import com.xuexiang.xui.widget.guidview.GuideCaseView;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.common.ClickUtils;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragStateListener;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements DrawerAdapter.OnItemSelectedListener, ClickUtils.OnClick2ExitListener {
    private static final int POS_main = 0;//菜单1索引
    private static final int POS_Other = 1;//菜单1索引
    private static final int POS_EXPANDS = 2;//菜单1索引
    private static final int POS_ABOUT = 3;//关于索引
    private static final int POS_LOGOUT = 5;//退出索引


    private SlidingRootNav mSlidingRootNav;
    private LinearLayout mLLMenu;
    private String[] mMenuTitles;
    private Drawable[] mMenuIcons;
    private DrawerAdapter mAdapter;

    private FragmentManager fragmentManager = null;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentManager = this.getSupportFragmentManager();
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        mMenuTitles = ResUtils.getStringArray(R.array.menu_titles);
        mMenuIcons = ResUtils.getDrawableArray(this, R.array.menu_icons);
        initSlidingMenu(savedInstanceState);
        setSupportActionBar(toolbar);//设置自定义bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Kiba518");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        }
        WidgetUtils.clearActivityBackground(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.openMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initSlidingMenu(null);
    }

    public void openMenu() {
        if (mSlidingRootNav != null) {
            mSlidingRootNav.openMenu();
        }
    }

    public void closeMenu() {
        if (mSlidingRootNav != null) {
            mSlidingRootNav.closeMenu();
        }
    }

    public boolean isMenuOpen() {
        if (mSlidingRootNav != null) {
            return mSlidingRootNav.isMenuOpened();
        }
        return false;
    }

    private void initSlidingMenu(Bundle savedInstanceState) {
        mSlidingRootNav = new SlidingRootNavBuilder(this)
                .withGravity(ResUtils.isRtl() ? SlideGravity.RIGHT : SlideGravity.LEFT)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        mLLMenu = mSlidingRootNav.getLayout().findViewById(R.id.ll_menu);
        final AppCompatImageView ivQrcode = mSlidingRootNav.getLayout().findViewById(R.id.iv_qrcode);
        ivQrcode.setOnClickListener(v -> ShowMessage("您点击的是二维码"));

        final AppCompatImageView ivSetting = mSlidingRootNav.getLayout().findViewById(R.id.iv_setting);
        ivSetting.setOnClickListener(v -> ShowMessage("您点击的是系统设置"));
        ViewUtils.setVisibility(mLLMenu, false);

        mAdapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_main).setChecked(true),
                createItemFor(POS_Other),
                createItemFor(POS_EXPANDS),
                createItemFor(POS_ABOUT),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        mAdapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(mAdapter);

        mAdapter.setSelected(POS_main);
        mSlidingRootNav.setMenuLocked(false);
        mSlidingRootNav.getLayout().addDragStateListener(new DragStateListener() {
            @Override
            public void onDragStart() {
                ViewUtils.setVisibility(mLLMenu, true);
            }

            @Override
            public void onDragEnd(boolean isMenuOpened) {
                ViewUtils.setVisibility(mLLMenu, isMenuOpened);
                if (isMenuOpened) {
                    if (!GuideCaseView.isShowOnce(MainActivity.this, "GuideCaseView_key_kiba")) {
                        final GuideCaseView guideStep1 = new GuideCaseView.Builder(MainActivity.this)
                                .title("点击进入，可进行系统设置")
                                .titleSize(18, TypedValue.COMPLEX_UNIT_SP)
                                .focusOn(ivSetting)
                                .build();

                        final GuideCaseView guideStep2 = new GuideCaseView.Builder(MainActivity.this)
                                .title("点击进入，扫码关注")
                                .titleSize(18, TypedValue.COMPLEX_UNIT_SP)
                                .focusOn(ivQrcode)
                                .build();

                        new GuideCaseQueue()
                                .add(guideStep1)
                                .add(guideStep2)
                                .show();
                        GuideCaseView.setShowOnce(MainActivity.this, "GuideCaseView_key_kiba");
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(int position) {
        switch (position) {
            case POS_main:
                replaceFragment(new NewsListEditFragment());
                mSlidingRootNav.closeMenu();
                break;
            case POS_Other:
                replaceFragment(new OtherFragment());
                mSlidingRootNav.closeMenu();
                break;
            case POS_EXPANDS:
                ShowMessage("扩展菜单呗点击 position：" + position);
                mSlidingRootNav.closeMenu();
                break;
            case POS_ABOUT:
                ShowMessage("关于菜单呗点击 position" + position);
                break;
            case POS_LOGOUT:
                DialogLoader.getInstance().showConfirmDialog(
                        this,
                        "是否确认退出",
                        "是",
                        (dialog, which) -> {
                            dialog.dismiss();
                            ShowMessage("登出成功！");
                            //跳转到登录页
                            ActivityUtils.startActivity(LoginActivity.class);
                            finish();
                        },
                        "否",
                        (dialog, which) -> dialog.dismiss()
                );
                break;
            default:
                break;
        }
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(mMenuIcons[position], mMenuTitles[position])
                .withIconTint(ThemeUtils.resolveColor(this, R.attr.xui_config_color_content_text))
                .withTextTint(ThemeUtils.resolveColor(this, R.attr.xui_config_color_content_text))
                .withSelectedIconTint(ThemeUtils.getMainThemeColor(this))
                .withSelectedTextTint(ThemeUtils.getMainThemeColor(this));
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isMenuOpen()) {
                closeMenu();
            } else {
                ClickUtils.exitBy2Click(2000, this);
            }
        }
        return true;
    }

    /**
     * 再点击一次
     */
    @Override
    public void onRetry() {
        ShowMessage("再按一次返回键退出程序");
    }

    /**
     * 退出
     */
    @Override
    public void onExit() {
        moveTaskToBack(true);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.replace(R.id.fragment_container, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }


}