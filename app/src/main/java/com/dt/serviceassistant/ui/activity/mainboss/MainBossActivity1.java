package com.dt.serviceassistant.ui.activity.mainboss;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.mvp.MVPBaseActivity;
import com.dt.serviceassistant.ui.fragment.analysis.AnalysisFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainBossActivity1 extends MVPBaseActivity<MainBossContract.View, MainBossPresenter> implements MainBossContract.View,NavigationView.OnNavigationItemSelectedListener {

    private AnalysisFragment mSalespersonFragment;
    private AnalysisFragment mSalespersonFragment1;
    private AnalysisFragment mSalespersonFragment2;
    private AnalysisFragment mSalespersonFragment3;
    private AnalysisFragment mSalespersonFragment4;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_boss;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        //标题栏
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_insurance);
        toolbar.setTitle("  配置");
        setSupportActionBar(toolbar);

        //主界面内容
        setFragment();
        //侧滑菜单
        setNavigationView();
        //浮动按钮
        setFloatingActionButton();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * 主页内容（默认第一页配置页）
     */
    private void setFragment(){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (mSalespersonFragment == null) {
            mSalespersonFragment = new AnalysisFragment();
        }
        // 使用当前Fragment的布局替代content的控件
        transaction.replace(R.id.content, mSalespersonFragment);
        // 事务提交
        transaction.commit();
    }

    /**
     * 侧滑菜单初始化
     */
    public void setNavigationView(){
        //侧滑菜单
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        //侧滑菜单头部控件初始化
        View headerLayout  = (LinearLayout) navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView head = (ImageView) headerLayout.findViewById(R.id.civ_avatar);
        head.setImageResource(R.mipmap.ic_launcher);
        TextView myName = (TextView) headerLayout.findViewById(R.id.tv_1);
        myName.setText("Tsien");

//        //可以隐藏某一个按钮
//        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_send);
//        menuItem.setVisible(false);	// true 为显示，false 为隐藏

        //设置默认显示第一页（配置）
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    /**
     * 浮动按钮
     */
    private void setFloatingActionButton(){
        //浮动按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    /**
     * 创建右上角三个点的功能
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        switch (item.getItemId()){
            case R.id.nav_configuration:
                toolbar.setLogo(R.drawable.ic_home_black_24dp);
                toolbar.setTitle("  配置");

                if (mSalespersonFragment == null) {
                    mSalespersonFragment = new AnalysisFragment();
                }
                // 使用当前Fragment的布局替代content的控件
                transaction.replace(R.id.content, mSalespersonFragment);
                break;
            case R.id.nav_account:
                toolbar.setLogo(R.drawable.ic_home_black_24dp);
                toolbar.setTitle("  账户");
                if (mSalespersonFragment1 == null) {
                    mSalespersonFragment1 = new AnalysisFragment();
                }
                // 使用当前Fragment的布局替代content的控件
                transaction.replace(R.id.content, mSalespersonFragment1);
                break;
            case R.id.nav_buy:
                toolbar.setLogo(R.drawable.ic_home_black_24dp);
                toolbar.setTitle("  购买");
                if (mSalespersonFragment2 == null) {
                    mSalespersonFragment2 = new AnalysisFragment();
                }
                // 使用当前Fragment的布局替代content的控件
                transaction.replace(R.id.content, mSalespersonFragment2);
                break;
            case R.id.nav_notice:
                toolbar.setLogo(R.drawable.ic_home_black_24dp);
                toolbar.setTitle("  公告");
                if (mSalespersonFragment3 == null) {
                    mSalespersonFragment3 = new AnalysisFragment();
                }
                // 使用当前Fragment的布局替代content的控件
                transaction.replace(R.id.content, mSalespersonFragment3);
                break;
//            case R.id.nav_share:
//                ToastUtils.showLong("share");
//                return true;
            case R.id.nav_send:
                ToastUtils.showLong("share");
                return true;
        }
        // 事务提交
        transaction.commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
