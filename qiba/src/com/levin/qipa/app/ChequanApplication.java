package com.levin.qipa.app;

import android.app.Application;
import android.text.TextUtils;

import com.androidex.widget.asyncimage.AsyncImageView;
import com.levin.qipa.manager.LocalDataManger;

public class ChequanApplication extends Application {
    private ChequanSettings mSettings;
    private LocalDataManger mLocalDataManger;
    /** 用户全局 id **/
    public String userid = "";
    public static ChequanApplication INSTANCE;
    /** 用于用户退出登录时关闭 **/
//    public static MainActivity mainActivity = null;
    
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        mSettings = new ChequanSettings(this);
        mLocalDataManger = LocalDataManger.getInstance(this);
        // 设置缓存文件夹
        AsyncImageView.setAsyncImageParams(this, Constants.MEMORY_CACHE_SIZE, Constants.IMAGE_CACHE_DIR,
                Constants.DISK_CACHE_SIZE);
        
    }
    

    
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    
    public ChequanSettings getSetting() {
        return mSettings;
    }
    
    public LocalDataManger getLocalDataManger() {
        return mLocalDataManger;
    }
    
    public String getUid() {
        if (TextUtils.isEmpty(userid)) {
            userid = mSettings.unpersistenceUser().getId();
        }
        return userid;
    }
}
