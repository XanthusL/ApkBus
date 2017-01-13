package com.apkbus.mobile.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.apkbus.mobile.R;
import com.apkbus.mobile.adapter.FinalAdapter;
import com.apkbus.mobile.bean.GiftWrapper;
import com.apkbus.mobile.constract.GiftContract;
import com.apkbus.mobile.presenter.GiftPresenter;
import com.apkbus.mobile.utils.LToast;
import com.apkbus.mobile.utils.SwipeRefresh;
import com.apkbus.mobile.widget.ScrollToEndListener;

import java.util.List;

public class GiftsActivity extends BaseActivity<GiftContract.Presenter> implements GiftContract.View, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefresh;
    private FinalAdapter<GiftWrapper.Gift> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts);

        swipeRefresh = ((SwipeRefreshLayout) findViewById(R.id.gift_swipe_refresh));
        SwipeRefresh.initSwipeRefreshLayout(swipeRefresh, mContext);
        swipeRefresh.setOnRefreshListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gift_recyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        mAdapter = new FinalAdapter<>(R.layout.item_gift);
        mAdapter.setFooterView(R.layout.item_footer,true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new ScrollToEndListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });
        findViewById(R.id.fab_gifts)
                .setOnClickListener((View v) -> recyclerView.scrollToPosition(0));
        mPresenter.initData();
    }


    @Override
    GiftContract.Presenter getPresenter() {
        return new GiftPresenter(this);
    }

    @Override
    public void updateData(List<GiftWrapper.Gift> data) {
        swipeRefresh.setRefreshing(false);
        mAdapter.updateRes(data);
    }

    @Override
    public void addData(List<GiftWrapper.Gift> data) {
        mAdapter.addRes(data);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showMsg(CharSequence msg) {
        LToast.show(mContext, msg);
    }

    @Override
    public void onRefresh() {
        mPresenter.initData();
    }
}
