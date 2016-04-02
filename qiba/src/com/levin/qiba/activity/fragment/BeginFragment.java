package com.levin.qiba.activity.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levin.qiba.R;
import com.levin.qiba.activity.adapter.WashTabsPagerAdapter;
import com.levin.qiba.activity.view.BGABanner;
import com.levin.qiba.activity.view.PagerSlidingTabStrip;
import com.levin.qipa.base.BaseTitleFragment;

public class BeginFragment extends BaseTitleFragment {
    private BGABanner banner;
    private PagerSlidingTabStrip tabTrip;
    private ViewPager pager;
    private WashTabsPagerAdapter adapter;
    
    @Override
    protected View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wash_fragment_home, container, false);
        return view;
    }
    
    @Override
    public void initView() {
        banner = (BGABanner) getView().findViewById(R.id.banner1);
        tabTrip = (PagerSlidingTabStrip) getView().findViewById(R.id.indicator);
        pager = (ViewPager) getView().findViewById(R.id.pager);
        adapter = new WashTabsPagerAdapter(getActivity(), getChildFragmentManager());
        pager.setAdapter(adapter);
        tabTrip.setViewPager(pager);
        setTitle(R.string.tab_washcar);
    }
    
    @Override
    public void initData() {
        
    }
}
