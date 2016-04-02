package com.levin.qipa.base;

import net.tsz.afinal.http.AjaxParams;
import android.app.Dialog;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.levin.qiba.R;
import com.levin.qiba.activity.view.NavigationBar;
import com.levin.qiba.activity.view.NavigationBar.IProvideNavigationBar;
import com.levin.qipa.manager.HttpManager;
import com.levin.qipa.network.OnGetLocalDataListener;
import com.levin.qipa.network.OnRequestListener;
import com.levin.qipa.network.Url;
import com.libs.utils.AndroidUtils;
import com.libs.utils.LogUtil;

public class BaseLoadingActivity extends FragmentActivity implements IProvideNavigationBar, OnClickListener,
        OnRequestListener, OnGetLocalDataListener {
    
    public static final int PRASSAGE_DIALOG = 0x01;
    private static final String TAG = "BaseLoadingActivity";
    private Dialog mLoadDialog;
    private NavigationBar mNavigationBar;
    private TextView leftView;
    private TextView rightView;
    private TextView titleView;
    private int screenWidth;
    private int screenHeight;
    private OnGetLocalDataListener mOnGetLocalDataListener;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewConfiguration configuration = ViewConfiguration.get(this);
        // float density = context.getResources().getDisplayMetrics().density;
        
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_base_title_layout);
        setupNavigationBar();
        View content = onCreateView(savedInstanceState);
        if (content != null)
            ((ViewGroup) findViewById(R.id.fragment_content)).addView(content, new ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        DisplayMetrics dm;
        dm = new DisplayMetrics();
        // 取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        LogUtil.i(TAG, "++++++" + dm.widthPixels + " ? " + dm.heightPixels);
        initView();
        initData();
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PRASSAGE_DIALOG:
            mLoadDialog = new Dialog(this, R.style.dialogTheme);
            mLoadDialog.setContentView(R.layout.dialog_loading);
            mLoadDialog.setCancelable(true);
            
            if (!TextUtils.isEmpty(dialogMsg)) {
                TextView dialogTV = (TextView) mLoadDialog.findViewById(R.id.dialog_txt);
                dialogTV.setText(dialogMsg);
            }
            
            return mLoadDialog;
        }
        return super.onCreateDialog(id);
    }
    
    protected void setupNavigationBar() {
        mNavigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
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
        titleView = (TextView) getLayoutInflater().inflate(R.layout.navigation_bar_title, null);
        titleView.setOnClickListener(this);
        return titleView;
    }
    
    protected View onAddLeftView() {
        leftView = (TextView) getLayoutInflater().inflate(R.layout.navigation_bar_left, null);
        leftView.setOnClickListener(this);
        // leftView.setText(R.string.title_bar_back_string);
        return leftView;
    }
    
    protected View onAddRightView() {
        rightView = (TextView) getLayoutInflater().inflate(R.layout.navigation_bar_right, null);
        rightView.setOnClickListener(this);
        return rightView;
    }
    
    @Override
    public NavigationBar getNavigationBar() {
        if (mNavigationBar == null) {
            throw new RuntimeException("you may have forgotten to call setupNavigationBar!!");
        }
        return mNavigationBar;
    }
    
    @Override
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
    
    public void setLeftTitle(int resid) {
        if (leftView != null) {
            leftView.setText(resid);
        }
    }
    
    public void setLeftTitleNoLeftDrawable(int resid) {
        if (leftView != null) {
            leftView.setText(resid);
            leftView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }
    
    public void setLeftTitleNoLeftDrawable(String title) {
        if (leftView != null) {
            leftView.setText(title);
            leftView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }
    
    public void hideLoadingView() {
        if (mLoadDialog != null && mLoadDialog.isShowing()) {
            dismissDialog(PRASSAGE_DIALOG);
        }
    }
    
    private String dialogMsg;
    
    public void setLoadingView() {
        dialogMsg = "";
        if (getWindow() != null) {
            showDialog(PRASSAGE_DIALOG);
        }
    }
    
    /**
     * 显示正在请求对话框，可以设置显示文字内容
     */
    public void setLoadingView(String msg) {
        dialogMsg = msg;
        if (getWindow() != null) {
            showDialog(PRASSAGE_DIALOG);
        }
    }
    
    private boolean dialogCancel = true;
    
    /**
     * 显示正在请求对话框，是否可以被取消
     * 
     * @param dialogCancel true 可以被取消
     */
    public void setLoadingView(boolean dialogCancel) {
        dialogCancel = dialogCancel;
        if (getWindow() != null) {
            showDialog(PRASSAGE_DIALOG);
        }
    }
    
    public void initData() {
        
    }
    
    public void initView() {
        
    }
    
    protected View onCreateView(Bundle savedInstanceState) {
        return null;
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
    }
    
    //
    public void setEmptyView(View view) {
        LinearLayout parent = (LinearLayout) findViewById(R.id.loadableListHolder);
        
        parent.getChildAt(0).setVisibility(View.GONE);
        if (parent.getChildCount() > 1) {
            parent.removeViewAt(1);
        }
        parent.addView(view);
    }
    
    // public void hideLoadingView() {
    // LinearLayout parent = (LinearLayout)
    // findViewById(R.id.loadableListHolder);
    // parent.getChildAt(0).setVisibility(View.GONE);
    // }
    //
    // public void setLoadingView() {
    // LinearLayout parent = (LinearLayout)
    // findViewById(R.id.loadableListHolder);
    //
    // if (parent.getChildCount() > 1) {
    // parent.removeViewAt(1);
    // }
    //
    // parent.getChildAt(0).setVisibility(View.VISIBLE);
    // }
    
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
    
    @Override
    public void setNavigationBar(NavigationBar navigationBar) {
        
    }
    
    protected void onLeftCLick() {
        
    }
    
    protected void onRightClick() {
        
    }
    
    @Override
    public void onClick(View v) {
        if (v == leftView) {
            onLeftCLick();
        } else if (v == rightView) {
            onRightClick();
        }
    }
    
    public int getScreenWidth() {
        return screenWidth;
    }
    
    public int getScreenHeight() {
        return screenHeight;
    }
    
    /**
     * 用于异步请求
     */
    public void loadData() {
        HttpManager.getInstance(this).loadData(getMethod(), getUrl(), getParams(), this, getClazz());
    }
    
    public String getMethod() {
        return "get";
    }
    
    public Class getClazz() {
        return null;
    }
    
    public String getUrl() {
        return Url.HOSTNAME;
    }
    
    public AjaxParams getParams() {
        return null;
    }
    
    @Override
    public void loadDataSuccess(Object t) {
        hideLoadingView();
    }
    
    @Override
    public void loadDataError(Throwable t, int errorNo, String strMsg) {
        hideLoadingView();
    }
    
    public boolean checkNetWorkStatusAndShowToast() {
        if (AndroidUtils.isNetworkAvailable(this)) {
            return true;
        } else {
            // Toast.makeText(this, getString(R.string.no_net),
            // Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    
    /**
     * 获取网络请求 管理类
     * 
     * @return
     */
    public HttpManager getHttpManager() {
        return HttpManager.getInstance(this);
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
    
    private void setOnGetLocalDataListener(OnGetLocalDataListener mOnGetLocalDataListener) {
        this.mOnGetLocalDataListener = mOnGetLocalDataListener;
    }
    
    @Override
    public Object onGetLocalData() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void OnGetDataSuccess(Object object) {
        // TODO Auto-generated method stub
        
    }
    
}
