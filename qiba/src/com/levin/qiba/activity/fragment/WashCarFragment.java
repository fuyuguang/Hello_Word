package com.levin.qiba.activity.fragment;

import android.os.Bundle;
import android.view.View;

import com.levin.qipa.base.BaseListFragment;

public class WashCarFragment extends BaseListFragment {
    public static WashCarFragment newInstance(String dayFlag) {
        
        WashCarFragment f = new WashCarFragment();
        
        Bundle bundle = new Bundle();
        bundle.putSerializable("flag", dayFlag);
        f.setArguments(bundle);
        
        return f;
    }
    
    @Override
    public void initView() {
        super.initView();
        getNavigationBar().setVisibility(View.GONE);
    }
    
    @Override
    public void initData() {
        super.initData();
    }
}
