package com.levin.qipa.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.levin.qiba.R;
import com.libs.utils.AndroidUtils;
import com.libs.widget.xlistview.XListView;
import com.libs.widget.xlistview.XListView.IXListViewListener;

public class BaseListActivity extends BaseLoadingActivity implements IXListViewListener {
    
    private XListView listview;
    private BaseListAdapter adapter;
    public boolean isRefresh = false;// 设置下拉标志
    public boolean isRequesting = false;// 正在请求
    
    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.base_list_layout, null);
        return view;
    }
    
    public void initListView() {
        listview = (XListView) findViewById(R.id.listview);
        listview.setXListViewListener(this);
    }
    
    @Override
    public void initView() {
        initListView();
    }
    
    @Override
    public void initData() {
        adapter = getListAapter();
        if (adapter != null) {
            listview.setAdapter(adapter);
        }
    }
    
    public XListView getListView() {
        return listview;
    }
    
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
        if (!AndroidUtils.isNetworkAvailable(this)) {
            loadLocalData();
        } else {
            loadData();
        }
    }
    
    @Override
    public void loadData() {
        if (!AndroidUtils.isNetworkAvailable(this)) {
            getListView().stopRefresh();
            getListView().stopLoadMore();
            hideLoadingView();
            Toast.makeText(this, getString(R.string.no_net), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isRequesting)
            return;
        isRequesting = true;
        if (isRefresh) {
            initRefreshParams();
        } else {
            initLoadMoreParams();
        }
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
    public void loadDataSuccess(Object t) {
        super.loadDataSuccess(t);
        listview.stopLoadMore();
        listview.stopRefresh();
        isRequesting = false;
        
        if (isRefresh) {
            onLoadSuccess(t, true);
            // onRefreshSucess(t);
        } else {
            onLoadSuccess(t, false);
            // onLoadMoreSuccess(t);
        }
        isRefresh = false;
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
    
    public void initRefreshParams() {
        
    }
    
    public void initLoadMoreParams() {
        
    }
    
    public void onLoadSuccess(Object t, boolean isRefresh) {
        
    }
    
}
