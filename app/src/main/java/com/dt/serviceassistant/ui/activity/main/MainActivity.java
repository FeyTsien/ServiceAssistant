package com.dt.serviceassistant.ui.activity.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.ui.activity.login.LoginPwdActivity;
import com.dt.serviceassistant.ui.fragment.information.InformationFragment;
import com.dt.serviceassistant.ui.fragment.insurance.InsuranceFragment;
import com.dt.serviceassistant.ui.fragment.me.MeFragment;
import com.dt.serviceassistant.ui.fragment.message.MessageFragment;
import com.dt.serviceassistant.ui.fragment.shipments.ShipmentsFragment;
import com.tsienlibrary.ui.activity.BaseActivity;
import com.tsienlibrary.utils.OpenFileUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private List<Fragment> mFragmentList;
    private Fragment mCurrentFragment;
    private Toolbar mToolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_message:
                    switchFragment(mCurrentFragment, mFragmentList.get(0), item.getTitle());
                    return true;
                case R.id.navigation_information:
                    switchFragment(mCurrentFragment, mFragmentList.get(1), item.getTitle());
                    return true;
//                case R.id.navigation_center:
//                    //由 BottomNavigationView 上面的 imageview 处理点击事件
//                    return true;
                case R.id.navigation_shipments:
                    switchFragment(mCurrentFragment, mFragmentList.get(2), item.getTitle());
                    return true;
                case R.id.navigation_insurance:
                    switchFragment(mCurrentFragment, mFragmentList.get(3), item.getTitle());
                    return true;
                case R.id.navigation_me:
                    switchFragment(mCurrentFragment, mFragmentList.get(4), item.getTitle());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            finish();
            ActivityUtils.startActivity(MainActivity.class);
            return;
        }
    }

    @Override
    protected void setStstus(boolean isfitsSystemWindows,boolean isWhite) {
        super.setStstus(false,true);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.title_message);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(MessageFragment.newInstance());
        mFragmentList.add(InformationFragment.newInstance());
        mFragmentList.add(ShipmentsFragment.newInstance());
        mFragmentList.add(InsuranceFragment.newInstance());
        mFragmentList.add(MeFragment.newInstance());
        addFragmentToActivity(getSupportFragmentManager(), mFragmentList.get(0), R.id.fragment);
        mCurrentFragment = mFragmentList.get(0);

        if (TextUtils.equals(AppData.getRoleType(), "1")) {
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            BottomNavigationViewHelper.disableShiftMode(navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(AppData.getRoleType(), "3")) {
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_three);
            BottomNavigationViewHelper.disableShiftMode(navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setVisibility(View.VISIBLE);
        }else {
            ActivityUtils.startActivity(LoginPwdActivity.class);
            finish();
        }

    }

    public void switchFragment(Fragment from, Fragment to, CharSequence title) {
        if (mCurrentFragment != to) {
            mCurrentFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().
                    beginTransaction();
            mToolbar.setTitle(title);
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.fragment, to).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }

    public void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                      @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }



    /**
     * 下载完成返回
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isInstallEvent(MessageEvent messageEvent) {
        ToastUtils.showLong(messageEvent.getMessage());
        showInstallDialog();
    }

    /**
     * 下载完成弹出安装提示框
     */
    private void showInstallDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("下载完成");
        builder.setMessage("是否安装");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = new File(OpenFileUtils.getApkPath(MainActivity.this), "INanMing.apk");
//                File file = new File(getApkPath(), "abc.jpg");
                OpenFileUtils.installApk(MainActivity.this, file);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }


    public static class MessageEvent {
        private String message;

        public MessageEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
