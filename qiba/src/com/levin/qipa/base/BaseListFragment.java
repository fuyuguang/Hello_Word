package com.levin.qipa.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.levin.qiba.R;
import com.libs.utils.AndroidUtils;
import com.libs.widget.xlistview.XListView;
import com.libs.widget.xlistview.XListView.IXListViewListener;

public class BaseListFragment extends BaseTitleFragment implements IXListViewListener {
    private Context mContext;
    private XListView listview;
    private BaseListAdapter adapter;
    public boolean isRefresh = true;// 设置下拉标志
    private boolean isRequesting = false;// 是否正在请求
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }
    
    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_list_layout, container, false);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
    }
    
    @Override
    public void initView() {
        initListView(getView());
    }
    
    @Override
    public void initData() {
        adapter = getListAapter();
        if (adapter != null) {
            listview.setAdapter(adapter);
        }
    }
    
    /**
     * 初始化 listview
     * 
     * @param view
     */
    public void initListView(View view) {
        listview = (XListView) view.findViewById(R.id.listview);
        listview.setXListViewListener(this);
        listview.setPullLoadEnable(false);
        
    }
    
    /**
     * 获取xlistview
     * 
     * @return
     */
    public XListView getListView() {
        return listview;
    }
    
    /**
     * 获取xlistview的适配器
     * 
     * @return
     */
    public BaseListAdapter getListAapter() {
        return null;
    }
    
    @Override
    public void onRefresh() {
        isRefresh = true;
        // getListView().setLastRefreshTime(System.currentTimeMillis());
        loadData();
    }
    
    @Override
    public void onLoadMore() {
        isRefresh = false;
        if (!AndroidUtils.isNetworkAvailable(getActivity())) {
            Toast.makeText(mContext, mContext.getString(R.string.net_msg_error), Toast.LENGTH_SHORT).show();
            loadLocalData();
        } else {
            loadData();
        }
    }
    
    /**
     * 设置加载后没有数据时，listview底部显示view，并设置listview不能上拉
     * 
     * @param msg 显示的信息
     */
    public void setListViewNoMoreData(String msg) {
        getListView().setPullLoadEnable(false);
        getListView().setNoLoadFooterView(msg, null);
    }
    
    @Override
    public void loadData() {
        if (!AndroidUtils.isNetworkAvailable(mContext)) {
            getListView().stopRefresh();
            getListView().stopLoadMore();
            hideLoadingView();
            Toast.makeText(mContext, mContext.getString(R.string.net_msg_error), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isRequesting)
            return;
        if (isRefresh) {
            initRefreshParams();
        } else {
            initLoadMoreParams();
        }
        isRequesting = true;
        super.loadData();
    }
    
    @Override
    public void loadDataError(Throwable t, int errorNo, String strMsg) {
        super.loadDataError(t, errorNo, strMsg);
        listview.stopLoadMore();
        listview.stopRefresh();
        isRequesting = false;
        isRefresh = false;
    }
    
    @Override
    public void loadDataSuccess(Object result) {
        super.loadDataSuccess(result);
        listview.stopLoadMore();
        listview.stopRefresh();
        isRequesting = false;
        if (isRefresh) {
            onLoadSuccess(result, true);
            // onRefreshSucess(t);
        } else {
            onLoadSuccess(result, false);
            // onLoadMoreSuccess(t);
        }
        isRefresh = false;
    }
    
    /**
     * 加载刷新时的参数
     */
    public void initRefreshParams() {
        
    }
    
    /**
     * 加载下拉加载更多时的参数
     */
    public void initLoadMoreParams() {
        
    }
    
    /**
     * 请求成功处理
     * 
     * @param t
     * @param isRefresh
     */
    public void onLoadSuccess(Object t, boolean isRefresh) {
        
    }
    
}
