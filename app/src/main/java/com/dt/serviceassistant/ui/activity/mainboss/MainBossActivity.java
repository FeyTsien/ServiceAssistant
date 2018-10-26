package com.dt.serviceassistant.ui.activity.mainboss;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.mvp.MVPBaseActivity;
import com.dt.serviceassistant.ui.fragment.analysis.AnalysisFragment;
import com.dt.serviceassistant.ui.fragmentBackHandler.BackHandlerHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainBossActivity extends MVPBaseActivity<MainBossContract.View, MainBossPresenter> implements MainBossContract.View, NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;
    private List<Fragment> fragmentList = new ArrayList<>();
    private Fragment mFrag;

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

    private void initView() {
        //标题栏
        toolbar.setLogo(R.drawable.ic_insurance);
        toolbar.setTitle("  配置");
        setSupportActionBar(toolbar);
        //主界面内容
        setFragment();
        //侧滑菜单
        setNavigationView();
        //浮动按钮
        setFloatingActionButton();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * 主页内容（默认第一页配置页）
     */
    private void setFragment() {
        fragmentList.add(AnalysisFragment.newInstance());
        fragmentList.add(AnalysisFragment.newInstance());
        fragmentList.add(AnalysisFragment.newInstance());
        fragmentList.add(AnalysisFragment.newInstance());
        loadFragment(0);
    }

    /**
     * TODO:侧滑菜单初始化
     */
    public void setNavigationView() {
        //侧滑菜单
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        //侧滑菜单头部控件初始化
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView head = headerLayout.findViewById(R.id.civ_avatar);
        head.setImageResource(R.mipmap.ic_launcher);
        TextView myName = headerLayout.findViewById(R.id.tv_1);
        myName.setText(AppData.getPhoneNumber());

//        //可以隐藏某一个按钮
//        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_send);
//        menuItem.setVisible(false);	// true 为显示，false 为隐藏

        //设置默认显示第一页（配置）
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    /**
     * 浮动按钮
     */
    private void setFloatingActionButton() {
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
     *
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

    //    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_configuration:
                toolbar.setLogo(R.drawable.ic_insurance);
                toolbar.setTitle("  配置");

                loadFragment(0);
                break;
            case R.id.nav_account:
                toolbar.setLogo(R.drawable.ic_insurance);
                toolbar.setTitle("  账户");
                loadFragment(1);
                break;
            case R.id.nav_buy:
                toolbar.setLogo(R.drawable.ic_insurance);
                toolbar.setTitle("  购买");
                loadFragment(2);
//                WebViewActivity.loadUrl(this,"https://mangosteen.cloud/webApp?user=" + AppData.getEmail() + "&passwd=" + AppData.getPassword()+ "&isan=true" ,"购买");
                break;
            case R.id.nav_notice:
                toolbar.setLogo(R.drawable.ic_insurance);
                toolbar.setTitle("  公告");
                loadFragment(3);
                break;
//            case R.id.nav_share:
//                ToastUtil.show(this, "share");
//                return true;
            case R.id.nav_send:
                ToastUtils.showLong( "退出");
                return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * TODO:加载切换Fragment，防止反复刷新
     *
     * @param position
     */
    private void loadFragment(int position) {
        //从集合中获取相对序号的Fragment
        Fragment fragment = fragmentList.get(position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //首先判断mFrag 是否为空，如果不为，先隐藏起来，接着判断从List 获取的Fragment是否已经添加到Transaction中，如果未添加，添加后显示，如果已经添加，直接显示
        if (mFrag != null) {
            fragmentTransaction.hide(mFrag);
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.content, fragment);

        } else {
            fragmentTransaction.show(fragment);
        }
        //将获取的Fragment 赋值给声明的Fragment 中，提交
        mFrag = fragment;
        fragmentTransaction.commit();

    }

    private long lastBackPress;

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!BackHandlerHelper.handleBackPress(this)) {
                if (System.currentTimeMillis() - lastBackPress < 1000) {
                    super.onBackPressed();
                } else {
                    lastBackPress = System.currentTimeMillis();
                    ToastUtils.showShort("再按一次退出");
                }
            }
        }
    }
}
