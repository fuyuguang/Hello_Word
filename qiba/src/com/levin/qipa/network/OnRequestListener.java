package com.levin.qipa.network;

public interface OnRequestListener {
    
     void loadDataSuccess(Object t);
    
     void loadDataError(Throwable t, int errorNo, String strMsg);
}
