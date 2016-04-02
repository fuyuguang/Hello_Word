package com.levin.qiba.activity;

import android.os.Bundle;
import android.view.View;

import com.levin.qiba.R;
import com.levin.qipa.base.BaseLoadingActivity;

public class SettingActivity extends BaseLoadingActivity {
    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        View view = View.inflate(this, R.layout.activity_setting, null);
        return view;
    }
    
    @Override
    public void initView() {
        super.initView();
        setTitle(R.string.setting_title);
    }
    
    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
    }
}
