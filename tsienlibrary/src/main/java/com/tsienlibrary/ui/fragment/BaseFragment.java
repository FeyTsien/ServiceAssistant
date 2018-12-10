
/*
 * Copyright 2016 ikidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tsienlibrary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.tsienlibrary.loadsir.callback.ErrorCallback;
import com.tsienlibrary.loadsir.callback.LoadingCallback;
import com.tsienlibrary.loadsir.callback.LottieEmptyCallback;
import com.tsienlibrary.ui.fragment.fragmentBackHandler.BackHandlerHelper;
import com.tsienlibrary.ui.fragment.fragmentBackHandler.FragmentBackHandler;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements FragmentBackHandler {

    protected Unbinder unbinder;
    protected LoadService mBaseLoadService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = View.inflate(getActivity(), getLayoutId(), null);
        unbinder = ButterKnife.bind(this, rootView);
        initData();
        initView();
        if (isLoadSir()) {
            return initLoadSir(rootView);
        } else {
            return rootView;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNet();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    protected boolean isLoadSir() {//是否需要使用LoadSir
        return false;
    }

    //初始化LoadSir
    private View initLoadSir(View rootView) {
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new ErrorCallback())
                .addCallback(new LottieEmptyCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .build();
        mBaseLoadService = loadSir.register(rootView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onNetReload(v);
            }
        });
        return mBaseLoadService.getLoadLayout();
    }

    protected void loadNet() {

    }

    protected void onNetReload(View v) {

    }

    @Override
    public boolean onBackPressed() {
        return interceptBackPressed()
                || (getBackHandleViewPager() == null
                ? BackHandlerHelper.handleBackPress(this)
                : BackHandlerHelper.handleBackPress(getBackHandleViewPager()));
    }

    public boolean interceptBackPressed() {
        return false;
    }

    /**
     * 2.1 版本已经不在需要单独对ViewPager处理
     *
     * @deprecated
     */
    @Deprecated
    public ViewPager getBackHandleViewPager() {
        return null;
    }
}
