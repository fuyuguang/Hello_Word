package com.levin.qiba.activity.fragment;

import android.os.Bundle;
import android.view.View;

import com.levin.qipa.base.BaseListFragment;

public class OrderFragment extends BaseListFragment {
    public static OrderFragment newInstance(String dayFlag) {
        
        OrderFragment f = new OrderFragment();
        
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
    
    @Override
    public void onLoadSuccess(Object t, boolean isRefresh) {
    	
    	super.onLoadSuccess(t, isRefresh);
    }
}
