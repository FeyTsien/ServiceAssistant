package com.dt.serviceassistant.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.ui.activity.login.LoginActivity;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;
import com.dt.serviceassistant.ui.fragment.information.InformationFragment;
import com.dt.serviceassistant.ui.fragment.insurance.InsuranceFragment;
import com.dt.serviceassistant.ui.fragment.me.MeFragment;
import com.dt.serviceassistant.ui.fragment.message.MessageFragment;
import com.dt.serviceassistant.ui.fragment.shipments.ShipmentsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            finish();
            ActivityUtils.startActivity(MainBossActivity.class);
        } else {
            initView();
        }
    }

    private void initData() {

    }

    private void initView() {
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
            ActivityUtils.startActivity(LoginActivity.class);
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


}
