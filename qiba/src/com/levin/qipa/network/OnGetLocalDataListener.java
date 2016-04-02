package com.levin.qipa.network;
/**
 * 获取本地数据回调接口
 *
 */
public interface OnGetLocalDataListener {
    /**
     * 获取本地数据回调方法
     * @return 
     */
    public Object onGetLocalData();
    /**
     * 获取本地数据成功后，回调方法
     * @param object
     * @return 
     */
    public void OnGetDataSuccess(Object object);
}
