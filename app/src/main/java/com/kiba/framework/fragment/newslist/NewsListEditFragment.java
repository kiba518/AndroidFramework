

package com.kiba.framework.fragment.newslist;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kiba.framework.fragment.other.OtherFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.kiba.framework.R;
import com.kiba.framework.base_activity.BaseFragment;
import com.kiba.framework.comm.model.NewInfo;
import com.kiba.framework.comm.utility.XToastUtils;
import com.kiba.framework.activity.main.MainActivity;

import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


@Page(name = "下拉刷新列表")
public class NewsListEditFragment extends BaseFragment {


    @BindView(R.id.smartRefreshLayout_1)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private NewsListEditAdapter mAdapter;

    @BindView(R.id.fl_edit)
    FrameLayout flEdit;
    @BindView(R.id.scb_select_all)
    SmoothCheckBox scbSelectAll;

    private TextView mTvSwitch;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list_edit;
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();

        mTvSwitch = (TextView) titleBar.addAction(new TitleBar.TextAction("管理") {
            @SingleClick
            @Override
            public void performAction(View view) {
                if (mAdapter == null) {
                    return;
                }
                mAdapter.switchManageMode();
                refreshManageMode();
            }
        });
        return titleBar;
    }

    private void refreshManageMode() {
        if (mTvSwitch != null) {
            mTvSwitch.setText(mAdapter.isManageMode() ? "退出" :"管理");
        }
        ViewUtils.setVisibility(flEdit, mAdapter.isManageMode());
    }



    @Override
    protected void initViews() {
        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(mAdapter = new NewsListEditAdapter(isSelectAll -> {
            if (scbSelectAll != null) {
                scbSelectAll.setCheckedSilent(isSelectAll);
            }
        }));
        scbSelectAll.setOnCheckedChangeListener((checkBox, isChecked) -> mAdapter.setSelectAll(isChecked));
    }

    @Override
    protected void initListeners() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mAdapter.refresh(getDemoNewInfos());
            refreshLayout.finishRefresh();
        }, 1000));
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mAdapter.loadMore(getDemoNewInfos());
            refreshLayout.finishLoadMore();
        }, 1000));
        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

        mAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (mAdapter.isManageMode()) {
                mAdapter.updateSelectStatus(position);
            } else {
                ShowMessage("点击了行"+item.getTitle());
                ((MainActivity)this.getActivity()).replaceFragment(new OtherFragment());
            }
        });
        mAdapter.setOnItemLongClickListener((itemView, item, position) -> {
            if (!mAdapter.isManageMode()) {
                mAdapter.enterManageMode(position);
                refreshManageMode();
            }
        });
    }



    @OnClick(R.id.btn_submit)
    public void onViewClicked(View view) {
        ShowMessage("选中了" + mAdapter.getSelectedIndexList().size() + "个选项！");

    }
    public static List<NewInfo> getDemoNewInfos() {
        List<NewInfo> list = new ArrayList<>();
        list.add(new NewInfo("RecyclerView", "RecyclerView使用详解").setUserName("kiba518")
                .setSummary("使用RecyclerView要引用对应的jar包，但最新版的项目中，不用引用也可以使用\n")
                .setDetailUrl("https://www.cnblogs.com/kiba/p/15246318.html")
                .setImageUrl("https://pic.cnblogs.com/avatar/243596/20180629143127.png"));

        list.add(new NewInfo("NavigationView", "NavigationView使用简介").setUserName("kiba518")
                .setSummary("Android支持直接创建带有NavigationView的Activity，这里主要介绍NavigationView的逻辑。\n")
                .setDetailUrl("https://www.cnblogs.com/kiba/p/15246116.html")
                .setImageUrl("https://pic.cnblogs.com/avatar/243596/20180629143127.png"));
        list.add(new NewInfo("DataBinding", "Android DataBinding使用详解").setUserName("kiba518")
                .setSummary("DataBinding是一个自动绑定UI的框架。\n")
                .setDetailUrl("https://www.cnblogs.com/kiba/p/15238413.html")
                .setImageUrl("https://pic.cnblogs.com/avatar/243596/20180629143127.png"));
        list.add(new NewInfo("泛型", "Java泛型详解").setUserName("kiba518")
                .setSummary("Java的泛型类比较好理解，和C#的一样，在类后面加<T>。\n")
                .setDetailUrl("https://www.cnblogs.com/kiba/p/15237965.html")
                .setImageUrl("https://pic.cnblogs.com/avatar/243596/20180629143127.png"));
        list.add(new NewInfo("xUtils3", "xUtils3的使用教程").setUserName("kiba518")
                .setSummary("Java的泛型类比较好理解，和C#的一样，在类后面加<T>。\n")
                .setDetailUrl("https://www.cnblogs.com/kiba/p/15237829.html")
                .setImageUrl("https://pic.cnblogs.com/avatar/243596/20180629143127.png"));
        list.add(new NewInfo("OKhttp3", "OKhttp3的使用教程").setUserName("kiba518")
                .setSummary("首先在build.gradle下的dependencies下添加引用。\n")
                .setDetailUrl("https://www.cnblogs.com/kiba/p/15236719.html")
                .setImageUrl("https://pic.cnblogs.com/avatar/243596/20180629143127.png"));
        return list;
    }
}
