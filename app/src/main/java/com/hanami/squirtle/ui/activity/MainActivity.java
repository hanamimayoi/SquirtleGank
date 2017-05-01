package com.hanami.squirtle.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hanami.squirtle.app.MyApplication;
import com.hanami.squirtle.bean.GankEntity;
import com.hanami.squirtle.ui.adapter.RecyclerPicAdapter;
import com.hanami.squirtle.ui.iVew.IPublicView;
import com.hanami.squirtle.ui.presenter.impl.PublicPresenterImpl;
import com.hanami.squirtlegank.R;
import com.hanami.squirtlegank.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IPublicView,
        OnRefreshListener, OnLoadMoreListener, Toolbar.OnMenuItemClickListener,
        View.OnClickListener {

    @BindView(R2.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R2.id.navigation_view)
    NavigationView navigation_view;

    @BindView(R2.id.tool_bar)
    Toolbar tool_bar;

    @BindView(R2.id.swipe_target)
    RecyclerView swipe_target;

    @BindView(R2.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private PublicPresenterImpl mPublicPresenter;

    private RecyclerPicAdapter recyclerPicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();

        //ViewStub viewStub = (ViewStub) findViewById(R.id.stub_import);
        //View inflate = viewStub.inflate();

        mPublicPresenter = new PublicPresenterImpl(this, this);

        initSwipeToLoadLayout();

        initNavigationView();
    }

    private void initSwipeToLoadLayout() {
        swipeToLoadLayout.setRefreshing(true);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

    }

    private void initToolbar() {
        //设置Toolbar
        setSupportActionBar(tool_bar);
        //设置Menu点击事件监听
        tool_bar.setOnMenuItemClickListener(this);
        tool_bar.setOnClickListener(this);
        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initNavigationView() {
        navigation_view.setCheckedItem(R.id.welfare);

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawer_layout.closeDrawers();

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //创建Menu菜单
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(MainActivity.this, "It's searching", Toast.LENGTH_SHORT).show();
                break;
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_bar:
                swipe_target.smoothScrollToPosition(0);
                break;

        }
    }


    @Override
    public void setGankList(List<GankEntity> results) {
        initRecyclerPicAdapter(results);
    }

    private void initRecyclerPicAdapter(List<GankEntity> results) {

        if (recyclerPicAdapter == null) {
            recyclerPicAdapter = new RecyclerPicAdapter(MainActivity.this, results);
            swipe_target.setAdapter(recyclerPicAdapter);
            swipe_target.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerPicAdapter.setOnItemClickListener(new RecyclerPicAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int size, int position) {
                    Log.d(MyApplication.LOG_TAG, "item被点击:" + position + "size:" + size);
                    mPublicPresenter.clickItem(size, position);

                }
            });
        } else {
            recyclerPicAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void finishRefresh() {
        if (swipeToLoadLayout == null) {
            return;
        }

        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }

        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void onLoadMore() {
        mPublicPresenter.getMoreDatas();

    }

    @Override
    public void onRefresh() {

        mPublicPresenter.getNewDatas();
    }

    @Override
    protected void onDestroy() {
        //publicPresenter解除绑定
        mPublicPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackBar() {

    }
}
