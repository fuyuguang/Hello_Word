package com.levin.qiba.activity;

import java.util.HashMap;

import net.tsz.afinal.http.AjaxParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.levin.qiba.activity.fragment.BeginFragment;
import com.levin.qiba.activity.fragment.FindFragment;
import com.levin.qiba.activity.fragment.HistoryFragment;
import com.levin.qiba.activity.fragment.MeFragment;
import com.levin.qiba.activity.fragment.RunningFragment;
import com.levin.qiba.R;
import com.levin.qipa.app.Constants;
import com.libs.utils.LogUtil;

public class MainActivity extends FragmentActivity implements OnClickListener {
    
    public static final String TAG = "MainActivity";
    private int DEF_TAB_COUNT = 5;
    // 底部Tab栏
    private TabHost mTabHost;
    private TabManager mTabManager;
    private LinearLayout mBottomTabs;
    private Button[] mTabButtons;
    private ImageView noticeFeed;
    private ImageView noticeDynimac;
    
    private int mCurrentIdx = 0;
    // public ImageView notify_icon4;
    
    public int totalUnreadNum = 0;
    private long exitTime;
    public RelativeLayout mainactivity_allmessage;
    public TextView mainactivity_allmessage_number;
    
    //private Timer mTimer;
    //private TimerTask mTimerTask;
    //private int pollTime = 60;// 轮询更新间隔时间
    /** 轮询 是否有新feed 请求参数 **/
    private AjaxParams notifyParams = new AjaxParams();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.fragment_tabs);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        
        initContent();
        initTabButtons();
        
        mTabButtons[0].setSelected(true);
        // initView();
        
        // initReceiver();
    }
    
    @Override
    public void onClick(View v) {
        
        for (int i = 0; i < mTabButtons.length; i++) {
            mTabButtons[i].setSelected(false);
            if (v == mTabButtons[i]) {
                v.setSelected(true);
                // 点击当前页标签刷新。
                if (mCurrentIdx == i) {
                    Fragment fragment = mTabManager.getLastTabFragment();
                    if (fragment instanceof IClickToRefersh) {
                        ((IClickToRefersh) fragment).onClickRefersh();
                    }
                } else {
                    mCurrentIdx = i;
                    mTabHost.setCurrentTab(mCurrentIdx);
                }
                
            }
        }
    }
    
    protected void initTabButtons() {
        mBottomTabs = (LinearLayout) findViewById(R.id.main_radio);
        if (null == mBottomTabs) {
            throw new IllegalArgumentException("Your TabHost must have a RadioGroup whose id attribute is 'main_radio'");
        }
        
        mTabButtons = new Button[DEF_TAB_COUNT];
        for (int j = 0; j < DEF_TAB_COUNT; j++) {
            String str = "radio_button_" + j;
            mTabButtons[j] = (Button) mBottomTabs.findViewWithTag(str);
            mTabButtons[j].setOnClickListener(this);
        }
        
        mTabButtons[0].performClick();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // LogUtil.d("MainActivity", "onNewIntent");
        
    }
    
    public static int sDiff = 0;
    
    public Button[] getmTabButtons() {
        return mTabButtons;
    }
    
    protected void initContent() {
        mTabManager = new TabManager(this, mTabHost, R.id.real_tab_content);
        mTabManager.addTab(mTabHost.newTabSpec("1").setIndicator("One"), RunningFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("2").setIndicator("Two"), HistoryFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("3").setIndicator("Three"), BeginFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("4").setIndicator("Four"), FindFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("5").setIndicator("Five"), MeFragment.class, null);
        /*mTabManager.addTab(mTabHost.newTabSpec("1").setIndicator("One"), WashHomeFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("2").setIndicator("Two"), OrdersHomeFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("3").setIndicator("Three"), Vip4sFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("4").setIndicator("Four"), MineFragment.class, null);*/
    
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }
    
    @Override
    public void onBackPressed() {
        exitApp();
    }
    
    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再点击一次退出应用", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
    
    /**
     * This is a helper class that implements a generic mechanism for
     * associating fragments with the tabs in a tab host. It relies on a trick.
     * Normally a tab host has a simple API for supplying a View or Intent that
     * each tab will show. This is not sufficient for switching between
     * fragments. So instead we make the content part of the tab host 0dp high
     * (it is not shown) and the TabManager supplies its own dummy view to show
     * as the tab content. It listens to changes in tabs, and takes care of
     * switch to the correct fragment shown in a separate content area whenever
     * the selected tab changes.
     */
    public static class TabManager implements TabHost.OnTabChangeListener {
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        TabInfo mLastTab;
        
        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;
            
            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }
        
        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;
            
            public DummyTabFactory(Context context) {
                mContext = context;
            }
            
            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }
        
        public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerId = containerId;
            mTabHost.setOnTabChangedListener(this);
        }
        
        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            String tag = tabSpec.getTag();
            
            TabInfo info = new TabInfo(tag, clss, args);
            
            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state. If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commitAllowingStateLoss();
            }
            
            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }
        
        public Fragment getLastTabFragment() {
            return mLastTab.fragment;
        }
        
        @Override
        public void onTabChanged(String tabId) {
            TabInfo newTab = mTabs.get(tabId);
            
            if (mLastTab != newTab) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastTab != null) {
                    if (mLastTab.fragment != null) {
                        ft.hide(mLastTab.fragment);
                        // ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else {
                        if (newTab.fragment.isHidden())
                            ft.show(newTab.fragment);
                        else
                            ft.attach(newTab.fragment);
                    }
                }
                
                mLastTab = newTab;
                ft.commitAllowingStateLoss();
                mActivity.getSupportFragmentManager().executePendingTransactions();
            }
        }
    }
    
    private NotifyReceiver notifyReceiver;
    private IntentFilter filter = new IntentFilter();
    
    /**
     * 接收轮询 是否有更新的广播
     * 
     * @author zhanglei
     * 
     */
    private class NotifyReceiver extends BroadcastReceiver {
        
        @Override
        public void onReceive(Context context, Intent intent) {
            String actionStr = intent.getAction();
            LogUtil.i(TAG, actionStr + "");
            if (actionStr.equals(Constants.RECEIVER_FEATURE_ACTION)) {
                
            }
            if (actionStr.equals(Constants.RECEIVER_NEW_ACTION)) {
                
            }
            if (actionStr.equals(Constants.RECEIVER_DYNIMAC_ACTION)) {
                
            }
        }
        
    }
    
    /**
     * 轮询更新， 显示小红点处理类
     */
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg) {
            LogUtil.i(TAG, msg.what + "");
            switch (msg.what) {
            
            }
        };
    };
    
    @Override
    public void onPause() {
        super.onPause();
        //mTimer.cancel();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG, "resume");
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notifyReceiver != null)
        unregisterReceiver(notifyReceiver);
        // 取消网络变化的广播接受者
        // unregisterReceiver(netReceive);
    }
    
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }
    
    public static interface IClickToRefersh {
        void onClickRefersh();
    }
    
}