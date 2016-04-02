package com.levin.qiba.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.levin.qiba.R;
import com.levin.qiba.activity.adapter.WashTabsPagerAdapter;
import com.levin.qiba.activity.view.BGABanner;
import com.levin.qiba.activity.view.PagerSlidingTabStrip;
import com.levin.qipa.base.BaseTitleFragment;

public class RunningFragment extends BaseTitleFragment {
	private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
	private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
	private BGABanner banner;
    private PagerSlidingTabStrip tabTrip;
    private ViewPager pager;
    private WashTabsPagerAdapter adapter;
    private View view;
    private int[] imgIdArray;
    @Override
    protected View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.running_fragment, container, false);
        return view;
    }
    
    @Override
    public void initView() {
    	banner = (BGABanner) getView().findViewById(R.id.runningbanner);
        tabTrip = (PagerSlidingTabStrip) getView().findViewById(R.id.runningindicator);
        pager = (ViewPager) getView().findViewById(R.id.pager);
        adapter = new WashTabsPagerAdapter(getActivity(), getChildFragmentManager());
        pager.setAdapter(adapter);
        tabTrip.setViewPager(pager);
        setTitle(R.string.tab_running);
    }
    
    @Override
    public void initData() {
    	List<View> mViews = new ArrayList<View>();
    	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
        lp.setMargins(5, 0, 5, 0);
        //载入图片资源ID  
        imgIdArray = new int[]{R.drawable.tab_running_press, R.drawable.tab_history_press, R.drawable.tab_find_press,R.drawable.tab_me_press};  
        //将点点加入到ViewGroup中  
        ImageView[] tips = new ImageView[imgIdArray.length];  
        ImageView imageView = null;
        for(int i=0; i<tips.length; i++){  
        	imageView = new ImageView(this.getActivity());
        	imageView.setTag(i);
        	imageView.setLayoutParams(lp);
        	imageView.setBackgroundResource(imgIdArray[i]);
        	imageView.setOnClickListener(new MyOnClickListener());
            mViews.add(imageView);
            banner.setViewPagerViews(mViews); 
        }  
        
    }
    
    @SuppressLint("ShowToast")
	private final class MyOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View arg) {
			ImageView imageView = (ImageView)arg;
			Toast.makeText(getActivity(), imageView.getTag().toString(), Toast.LENGTH_SHORT).show();
			banner.setTag(imageView.getTag());
		}
    	
    }
    
}