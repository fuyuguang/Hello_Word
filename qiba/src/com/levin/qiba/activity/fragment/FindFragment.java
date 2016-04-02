package com.levin.qiba.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levin.qiba.R;
import com.levin.qipa.base.BaseTitleFragment;

public class FindFragment extends BaseTitleFragment {
    
    @Override
    protected View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_find, container, false);
        return view;
    }
    
    @Override
    public void initData() {
        super.initData();
    }
    
    @Override
    public void initView() {
        super.initView();
        setTitle(R.string.tab_find);
    }
}
