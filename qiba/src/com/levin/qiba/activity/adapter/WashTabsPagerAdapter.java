package com.levin.qiba.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.levin.qiba.activity.fragment.WashCarFragment;
import com.libs.utils.LogUtil;

public class WashTabsPagerAdapter extends FragmentPagerAdapter {
    
    private final String TAG = "TabsPagerAdapter";
    // private Map<Integer, Fragment> ref = new HashMap<Integer, Fragment>();
    private String[] tabs = { "距离排序", "人气排序", "价格排序", "个人收藏" };
    private String[] flags = { "1", "2", "3", "4" };
    
    public WashTabsPagerAdapter(FragmentActivity fragmentActivity, FragmentManager fm) {
        super(fm);
    }
    
    @Override
    public int getCount() {
        return tabs.length;
    }
    
    @Override
    public Fragment getItem(int position) {
        LogUtil.e(TAG, "getItem");
        Fragment fragment = getFragment(position);
        if (fragment == null) {
            fragment = WashCarFragment.newInstance(flags[position]);
            // ref.put(Integer.valueOf(position), fragment);
        }
        
        return fragment;
        
    }
    
    public Fragment getFragment(int position) {
        
        // Fragment fragment = ref.get(Integer.valueOf(position));
        
        return null;
    }
    
    @Override
    public void destroyItem(View container, int position, Object object) {
        LogUtil.e(TAG, "destroyItem");
        // TODO Auto-generated method stub
        super.destroyItem(container, position, object);
        // ref.remove(Integer.valueOf(position));
        
    }
    
    public void clear() {
        // ref.clear();
        // datas.clear();
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LogUtil.e(TAG, "instantiateItem");
        
        return super.instantiateItem(container, position);
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
    
}
