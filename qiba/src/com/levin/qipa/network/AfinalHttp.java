package com.levin.qipa.network;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.client.CookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.libs.utils.LogUtil;

public class AfinalHttp<T> {
    private final boolean DEBUG = false;
    static String cur_url;
    ArrayList<String> err_list = new ArrayList<String>();
    ArrayList<String> all_list = new ArrayList<String>();
    
    public static CookieStore cookieStore;
    public static HttpContext localContext;
    // afinal相关变量
    private FinalHttp mFinalHttp;
    private Gson gson = new Gson();
    
    private Context context;
    
    public AfinalHttp(Context context) {
        this.context = context;
        // finalHttp初始化
        mFinalHttp = new FinalHttp();// 与更换域名重试相关的构造
        // mFinalHttp.addHeader("Host", "online.test.spriteapp.com");
        mFinalHttp.configTimeout(20 * 1000);// 设置超时时间，三种超时
        mFinalHttp.configCharset("utf-8");
        // 如果想重试，并且是更换域名的重试,必须设置以下信息
        mFinalHttp.configRequestExecutionRetryCount(3);// 错误重试
        // mFinalHttp.configRetryHostUrl(Url.HOSTNAME);// 重试主机域名
        // mFinalHttp.configRequestMainHost(Url.HOSTNAME);//
        // 配置主要请求的主机，即应用发起的每个请求的主机，如：http://api.budejie.com
        // 设置cookieStore
        mFinalHttp.configCookieStore(getCookieStoreInstance(context));
    }
    
    /* 清除缓存cookie */
    public void clearCookie() {
        cookieStore = null;
        mFinalHttp.configCookieStore(null);
    }
    
    public static CookieStore getCookieStoreInstance(Context context) {
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(context);
        }
        return cookieStore;
    }
    
    public HttpContext getHttpContextInstance() {
        if (localContext == null) {
            localContext = new BasicHttpContext();
        }
        return localContext;
    }
    
    /**
     * 新的网络请求方法，post方式
     * 
     * @param context
     * @param url
     * @param params
     * @param ajaxCallBack
     */
    @SuppressWarnings("unchecked")
    public void requestNetworkPost(Context context, String url, AjaxParams params, OnRequestListener listener,
            Class<T> clazz) {
        try {
            // 参数
            if (params == null)
                params = new AjaxParams();
            // 设置参数
            // addPublicParams(context, params);
            // url = Url.getUrl("", url);
            mFinalHttp.post(url, params, new MyAjaxCallBack<T>(listener, clazz));
            LogUtil.i("network", "post:" + url + params.toString());
        } catch (OutOfMemoryError e) {
            // LogUtil.e("connet", "OutOfMemoryError: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    
    /**
     * 
     * @param context
     * @param url
     * @param params
     * @param ajaxCallBack
     */
    @SuppressWarnings("unchecked")
    public void requestNetworkGet(Context context, String url, AjaxParams params, OnRequestListener listener,
            Class<T> clazz) {
        try {
            // 参数
            if (params == null)
                params = new AjaxParams();
            // 设置参数
            addPublicParams(context, params);
            mFinalHttp.get(url, params, new MyAjaxCallBack<T>(listener, clazz));
            LogUtil.i("network", "get:" + url + params.toString());
        } catch (OutOfMemoryError e) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    
    /**
     * 同步请求
     * 
     * @param context
     * @param url
     * @param params
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public T requestSyncGet(Context context, String url, AjaxParams params, Class<T> clazz) {
        Object object = null;
        T rusult = null;
        try {
            
            // 参数
            if (params == null)
                params = new AjaxParams();
            // 设置参数
            addPublicParams(context, params);
            object = mFinalHttp.getSync(url, params);
            if (object == null)
                return null;
            rusult = gson.fromJson(object.toString(), clazz);
            LogUtil.e("requestSyncGet", "get:" + object.toString());
        } catch (OutOfMemoryError e) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return rusult;
    }
    
    public AjaxParams addPublicParams(Context context, AjaxParams params) {
        
        return params;
    }
    
    class MyAjaxCallBack<T> extends AjaxCallBack<T> {
        private OnRequestListener listener;
        private Class<T> clazz;
        
        public MyAjaxCallBack(OnRequestListener listener, Class<T> clazz) {
            this.listener = listener;
            this.clazz = clazz;
        }
        
        @Override
        public void onLoading(long count, long current) {
            LogUtil.e("network", "http json:onLoading  " + count);
        }
        
        @Override
        public void onFailure(Throwable t, int errorNo, String strMsg) {
            LogUtil.e("network", "onFailure" + "|" + t.toString() + "|" + errorNo + "|" + strMsg);
            String errorMsg = "服务器超时，请稍后重试";
            if (listener != null)
                listener.loadDataError(t, errorNo, errorMsg);
        }
        
        @Override
        @SuppressLint("ShowToast")
        public void onSuccess(Object t) {
            if (t == null)
                return;
            LogUtil.e("network", "http json:onSuccess   " + t.toString());
            if (TextUtils.isEmpty(t.toString())) {
                onFailure(new Throwable(), 500, "server error");
                return;
            }
            T result = null;
            try {
                result = gson.fromJson(t.toString(), clazz);
                
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                // if (listener != null)
                // listener.loadDataError(e);
            }
            if (listener != null)
                listener.loadDataSuccess(result);
            
        }
    }
    
}