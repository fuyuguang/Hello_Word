package com.levin.qiba.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.levin.qiba.R;
import com.levin.qiba.activity.SettingActivity;
import com.levin.qipa.base.BaseTitleFragment;

public class MeFragment extends BaseTitleFragment {
    private RelativeLayout mine_account_rl;
    private RelativeLayout mine_pay_rl;
    private RelativeLayout mine_donate_rl;
    private RelativeLayout mine_msg_rl;
    private RelativeLayout mine_trade_rl;
    private RelativeLayout mine_fav_rl;
    private RelativeLayout mine_carmanager_rl;
    private RelativeLayout mine_setting_rl;
    private RelativeLayout mine_share_rl;
    private RelativeLayout mine_advice_rl;
    private RelativeLayout mine_server_rl;
    
    @Override
    protected View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }
    
    @Override
    public void initView() {
        super.initView();
        setTitle(R.string.tab_mine);
        mine_account_rl = (RelativeLayout) getView().findViewById(R.id.mine_account);
        mine_pay_rl = (RelativeLayout) getView().findViewById(R.id.mine_pay);
        mine_donate_rl = (RelativeLayout) getView().findViewById(R.id.mine_donate);
        mine_msg_rl = (RelativeLayout) getView().findViewById(R.id.mine_message);
        mine_trade_rl = (RelativeLayout) getView().findViewById(R.id.mine_trade);
        mine_fav_rl = (RelativeLayout) getView().findViewById(R.id.mine_carmanager);
        mine_carmanager_rl = (RelativeLayout) getView().findViewById(R.id.mine_fav);
        mine_setting_rl = (RelativeLayout) getView().findViewById(R.id.mine_setting);
        mine_share_rl = (RelativeLayout) getView().findViewById(R.id.mine_share);
        mine_advice_rl = (RelativeLayout) getView().findViewById(R.id.mine_advice);
        mine_server_rl = (RelativeLayout) getView().findViewById(R.id.mine_service);
        setListener();
    }
    
    private void setListener() {
        mine_account_rl.setOnClickListener(this);
        mine_pay_rl.setOnClickListener(this);
        mine_donate_rl.setOnClickListener(this);
        mine_msg_rl.setOnClickListener(this);
        mine_trade_rl.setOnClickListener(this);
        mine_fav_rl.setOnClickListener(this);
        mine_carmanager_rl.setOnClickListener(this);
        mine_setting_rl.setOnClickListener(this);
        mine_share_rl.setOnClickListener(this);
        mine_advice_rl.setOnClickListener(this);
        mine_server_rl.setOnClickListener(this);
    }
    
    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
    }
    
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.mine_account:
            break;
            case R.id.mine_pay:
            break;
            case R.id.mine_donate:
            break;
            case R.id.mine_message:
            break;
            case R.id.mine_trade:
            break;
            case R.id.mine_carmanager:
            break;
            case R.id.mine_setting:
            intent = new Intent();
            intent.setClass(getActivity(), SettingActivity.class);
            getActivity().startActivity(intent);
            break;
            case R.id.mine_share:
            break;
            case R.id.mine_advice:
            break;
            case R.id.mine_service:
            break;
            case R.id.mine_fav:
            break;
        }
    }
}
