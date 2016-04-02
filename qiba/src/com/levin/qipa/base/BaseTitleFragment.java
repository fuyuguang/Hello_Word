package com.levin.qipa.base;

import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.levin.qiba.R;
import com.levin.qiba.activity.view.NavigationBar;
import com.levin.qiba.activity.view.NavigationBar.IProvideNavigationBar;
import com.levin.qipa.manager.HttpManager;
import com.levin.qipa.network.OnGetLocalDataListener;
import com.levin.qipa.network.OnRequestListener;
import com.levin.qipa.network.Url;

@SuppressLint("ResourceAsColor")
public class BaseTitleFragment extends Fragment implements OnClickListener, OnRequestListener, OnGetLocalDataListener {
    
    public static final String TAG = "BaseTitleFragment";
    private IProvideNavigationBar mActivity;
    private NavigationBar mNavigationBar;
    private TextView leftView;
    private TextView rightView;
    private TextView titleView;
    public OnGetLocalDataListener mOnGetLocalDataListener;
    private FrameLayout mFrameLayout;
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IProvideNavigationBar) {
            mActivity = (IProvideNavigationBar) activity;
        }
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    public NavigationBar getNavigationBar() {
        return mNavigationBar;
    }
    
    public void setOnGetLocalDataListener(OnGetLocalDataListener mOnGetLocalDataListener) {
        this.mOnGetLocalDataListener = mOnGetLocalDataListener;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base_title_layout, container, false);
        View content = onCreateFragmentView(inflater, container, savedInstanceState);
        if (content != null)
            ((ViewGroup) rootView.findViewById(R.id.fragment_content)).addView(content);
        return rootView;
    }
    
    protected View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupNavigationBar();
        setOnGetLocalDataListener(this);
        initView();
        initData();
    }
    
    /**
     * 加载数据
     */
    public void initData() {
        // loadLocalData();
    }
    
    /**
     * 初始化控件
     */
    public void initView() {
        
    }
    
    private View navigationBarRootView;
    
    protected void setupNavigationBar() {
        mNavigationBar = (NavigationBar) getView().findViewById(R.id.navigation_bar);
        navigationBarRootView = getNavigationBar().findViewById(R.id.navigation_bar);
        if (mNavigationBar == null) {
            throw new RuntimeException("R.id.navigation_bar_ex resouce not found!!");
        }
        View middleview = onAddMiddleView();
        if (middleview != null)
            mNavigationBar.setMiddleView(middleview); // 自定义中间title
            
        View leftView = onAddLeftView();
        if (leftView != null)
            mNavigationBar.setLeftView(leftView);// 自定义左边title
            
        View rightView = onAddRightView();
        if (rightView != null)
            mNavigationBar.setRightView(rightView);// 自定义右边title
            
    }
    
    protected View onAddMiddleView() {
        titleView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.navigation_bar_title, null);
        titleView.setOnClickListener(this);
        return titleView;
    }
    
    protected View onAddLeftView() {
        leftView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.navigation_bar_left, null);
        leftView.setOnClickListener(this);
        return leftView;
    }
    
    protected View onAddRightView() {
        rightView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.navigation_bar_right, null);
        rightView.setOnClickListener(this);
        return rightView;
    }
    
    public void setTitle(int titleId) {
        titleView.setText(titleId);
    }
    
    public void setTitle(String title) {
        titleView.setText(title);
    }
    
    public void setLeftDrawable(int resid) {
        if (leftView != null) {
            Drawable drawable = getResources().getDrawable(resid);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            leftView.setCompoundDrawables(drawable, null, null, null);
            // leftView.setBackgroundResource(resid);
        }
    }
    
    public void setRightDrawable(int resid) {
        if (rightView != null) {
            Drawable drawable = getResources().getDrawable(resid);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            rightView.setCompoundDrawables(null, null, drawable, null);
            // rightView.setBackgroundResource(resid);
        }
    }
    
    public void setRightTitle(int resid) {
        if (rightView != null) {
            rightView.setText(resid);
        }
    }
    
    public void setRightTitle(String title) {
        if (rightView != null) {
            rightView.setText(title);
        }
    }
    
    public void setLefttTitle(int resid) {
        if (leftView != null) {
            leftView.setText(resid);
        }
    }
    
    public void setLeftTitle(String title) {
        if (leftView != null) {
            leftView.setText(title);
        }
    }
    
    /**
     * 设置没有数据时，显示的view
     */
    public void setEmptyView() {
        View rootView = getView();
        if (rootView == null)
            return;
        LinearLayout parent = (LinearLayout) rootView.findViewById(R.id.empty_list_ll);
        if (parent == null) {
            throw new RuntimeException("R.id.empty_list_ll resouce not found!!");
        }
        if (parent.getChildCount() > 1) {
            parent.removeViewAt(1);
        }
        parent.getChildAt(0).setVisibility(View.VISIBLE);
        parent.setVisibility(View.VISIBLE);
        // if (parent.getChildCount() > 1) {
        // parent.removeViewAt(1);
        // }
        // if (view.getParent() != null) {
        // ViewGroup parentView = (ViewGroup) view.getParent();
        // parentView.removeView(view);
        // }
        // parent.addView(view);
    }
    
    /**
     * 隐藏没有数据时，显示的view
     */
    public void hideEmptyView() {
        View rootView = getView();
        if (rootView == null)
            return;
        LinearLayout parent = (LinearLayout) rootView.findViewById(R.id.empty_list_ll);
        if (parent == null) {
            throw new RuntimeException("R.id.empty_list_ll resouce not found!!");
        }
        parent.getChildAt(0).setVisibility(View.GONE);
        parent.setVisibility(View.GONE);
    }
    
    public void hideLoadingView() {
        View rootView = getView();
        if (rootView == null)
            return;
        LinearLayout parent = (LinearLayout) rootView.findViewById(R.id.loadableListHolder);
        if (parent == null) {
            throw new RuntimeException("R.id.loadableListHolder resouce not found!!");
        }
        parent.getChildAt(0).setVisibility(View.GONE);
    }
    
    public void setLoadingView() {
        View rootView = getView();
        if (rootView == null)
            return;
        LinearLayout parent = (LinearLayout) rootView.findViewById(R.id.loadableListHolder);
        if (parent == null) {
            throw new RuntimeException("R.id.loadableListHolder resouce not found!!");
        }
        if (parent.getChildCount() > 1) {
            parent.removeViewAt(1);
        }
        
        parent.getChildAt(0).setVisibility(View.VISIBLE);
    }
    
    /**
     * 隐藏左边
     * 
     * @param ishide
     */
    public void hideLeftView(boolean ishide) {
        if (ishide && leftView != null) {
            leftView.setVisibility(View.GONE);
        } else if (leftView != null) {
            leftView.setVisibility(View.VISIBLE);
        }
    }
    
    /**
     * 隐藏右边
     * 
     * @param ishide
     */
    public void hideRightView(boolean ishide) {
        if (ishide && rightView != null) {
            rightView.setVisibility(View.GONE);
        } else if (rightView != null) {
            rightView.setVisibility(View.VISIBLE);
        }
    }
    
    protected void onLeftCLick() {
        
    }
    
    protected void onRightClick() {
        
    }
    
    protected void onSearchClick() {
        
    }
    
    @Override
    public void onClick(View v) {
        if (v == leftView) {
            onLeftCLick();
        } else if (v == rightView) {
            onRightClick();
        }
    }
    
    /**
     * 用于异步请求
     */
    public void loadData() {
        
        HttpManager.getInstance(getActivity()).loadData(getMethod(), getUrl(), getParams(), this, getClazz());
        
    }
    
    /**
     * 请求 成功后 结果bean对象
     * 
     * @return
     */
    public Class getClazz() {
        return null;
    }
    
    /**
     * 请求参数
     * 
     * @return
     */
    public String getMethod() {
        return "get";
    }
    
    /**
     * 请求的url
     * 
     * @return
     */
    public String getUrl() {
        return Url.HOSTNAME;
    }
    
    /**
     * 请求参数
     * 
     * @return
     */
    public AjaxParams getParams() {
        return null;
    }
    
    /**
     * 处理加载更多成功的监听
     */
    @Override
    public void loadDataSuccess(Object t) {
        hideLoadingView();
    }
    
    /**
     * 处理加载更多失败的监听
     */
    @Override
    public void loadDataError(Throwable t, int errorNo, String strMsg) {
        hideLoadingView();
    }
    
    private class LoadLocalDataTask extends AsyncTask<Void, Void, Object> {
        
        @Override
        protected Object doInBackground(Void... params) {
            Object object = null;
            if (mOnGetLocalDataListener != null) {
                object = mOnGetLocalDataListener.onGetLocalData();
            }
            return object;
        }
        
        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (mOnGetLocalDataListener != null) {
                mOnGetLocalDataListener.OnGetDataSuccess(result);
            }
        }
    }
    
    /**
     * 获取本地数据
     */
    public void loadLocalData(OnGetLocalDataListener mOnGetLocalDataListener) {
        setOnGetLocalDataListener(mOnGetLocalDataListener);
        new LoadLocalDataTask().execute();
    }
    
    /**
     * 获取本地数据
     */
    public void loadLocalData() {
        setOnGetLocalDataListener(this);
        new LoadLocalDataTask().execute();
    }
    
    /**
     * 获取网络请求 管理类
     * 
     * @return
     */
    public HttpManager getHttpManager() {
        return HttpManager.getInstance(getActivity());
    }
    
    @Override
    public Object onGetLocalData() {
        
        return null;
    }
    
    @Override
    public void OnGetDataSuccess(Object object) {
        
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
    }
    
}
