package com.levin.qiba.activity.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levin.qiba.R;
import com.levin.qiba.activity.adapter.OrderTabsPagerAdapter;
import com.levin.qiba.activity.view.PagerSlidingTabStrip;
import com.levin.qipa.base.BaseTitleFragment;

public class OrdersHomeFragment extends BaseTitleFragment {
    private PagerSlidingTabStrip tabTrip;
    private ViewPager pager;
    private OrderTabsPagerAdapter adapter;
    
    @Override
    protected View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment_home, container, false);
        return view;
    }
    
    @Override
    public void initView() {
        tabTrip = (PagerSlidingTabStrip) getView().findViewById(R.id.indicator);
        pager = (ViewPager) getView().findViewById(R.id.pager);
        adapter = new OrderTabsPagerAdapter(getActivity(), getChildFragmentManager());
        pager.setAdapter(adapter);
        tabTrip.setViewPager(pager);
        setTitle(R.string.tab_order);
    }
    
    @Override
    public void initData() {
        
    }
}
