package com.levin.qipa.manager;

import android.content.ContentResolver;
import android.content.Context;

/**
 * 本地缓存数据管理器
 * 
 * @author zhangchengfu
 * 
 */
public class LocalDataManger {
    private Context mContext;
    private ContentResolver mResolver;
    private static LocalDataManger INSTANCE;
    
    private LocalDataManger(Context mContext) {
        this.mContext = mContext;
        this.mResolver = mContext.getContentResolver();
    }
    
    public synchronized static LocalDataManger getInstance(Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataManger(ctx);
        }
        return INSTANCE;
    }
    
}
