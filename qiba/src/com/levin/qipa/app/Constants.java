package com.levin.qipa.app;

import java.io.File;

import android.os.Environment;

/**
 * 常量
 * 
 * @author wuzhenlin
 * 
 */
public interface Constants {
    String VERSION_CODE = "81";// 这里不能格式化
    String VERSION_NAME = "3.5.1";// 这里不能格式化
    boolean DEBUG = false;// 这里不能格式化,影响打包
    String MARKET = "anzhuo";
    /** 网络请求返回成功的结果码**/
    String SUCC_CODE = "E00000000";
    /** 缓存图片内存大小**/
    int MEMORY_CACHE_SIZE = 6 * 1024 * 1024;// 6M
    /** 缓存图片文件夹大小**/
    long DISK_CACHE_SIZE = 100 * 1024 * 1024;// 100M
    /** 缓存图片文件夹位置**/
    File IMAGE_CACHE_DIR = new File(Environment.getExternalStorageDirectory(), "chequan"
            + File.separator + "ImageCache");
    /** 精选feed列表类型 **/
    String FEED_FEATURED = "0";
    /** 最新feed列表类型 **/
    String FEED_NEW = "1";
    /** 动态feed列表类型 **/
    String FEED_DYNIMAC = "2";
    
    /** 添加喜欢请求参数  **/
    String LOVE_FEED_ADD = "0";
    /** 取消喜欢请求参数  **/
    String LOVE_FEED_CANCLE = "1";
    
    /** 添加喜欢时 是否转发  是 **/
    String LOVE_FEED_FORWARD = "1";
    /** 添加喜欢时 是否转发  否 **/
    String LOVE_FEED_NO_FORWARD = "0";
    
    /** 用户性别 男  类型 **/
    int GENDER_MALE_TYPE = 2;
    /** 用户性别 女  类型 **/
    int GENDER_FEMALE_TYPE = 1;
    /** 用户性别 男 **/
    String GENDER_MALE = "男";
    /** 用户性别 女**/
    String GENDER_FEMALE = "女";
    
    /** 举报类型 色情 **/
    int REPORT_TYPE_SE = 1;
    /** 举报类型 政治 **/
    int REPORT_TYPE_ZZ = 2;
    /** 举报类型 广告 **/
    int REPORT_TYPE_AD = 3;
    
    
    
    int BASE_EDIT_ID = 10;
    int RESULT_EDIT_NAME_ID = BASE_EDIT_ID + 1;
    
    int REQUEST_CODE_EDIT_IMAGE = BASE_EDIT_ID + 2;
    
    int RESULT_CUT_PICTURE_BY_CAMERA = BASE_EDIT_ID + 3;
    
    public int RESULT_CUT_PICTURE_BY_ALBUM = BASE_EDIT_ID + 4;
    
    int EDITNICKNAMERESULT = BASE_EDIT_ID + 5;
    int REQUEST_FOR_LOGIN = BASE_EDIT_ID + 6;
    int MY_PROFILE_ACTIVITY_RESULT = BASE_EDIT_ID + 7;
    int LOGOUT = BASE_EDIT_ID + 8;
    /** 评论发表成功 **/
    int RESULT_COMMENT_SEND_SUCCESS = BASE_EDIT_ID + 9;
    /** 修改用户简介成功 **/
    int RESULT_EDIT_DESC_SUCCESS = BASE_EDIT_ID + 10;
    /** 编辑头像图片请求码**/
    int REQUEST_CODE_CROP_IMAGE= 728; 
    
    /** 最大评论长度**/
    int MAX_COMMENT_LEN = 140;
    /** 最小评论长度**/
    int MIN_COMMENT_LEN = 5;
    /** 最大简介长度**/
    int MAX_DESC_LEN = 30;
    /** 最小简介长度**/
    int MIN_DESC_LEN = 5;
    int HTTP_CONNECTION_TIMEOUT = 15;
    int SOCKET_TIMEOUT = 15;

    
    /*************** 轮询 常量 开始********************/
    /** 轮询服务 NotifyService 的action **/
    String NOTIFYSERVICE_ACTION = "com.spriteapp.microvideo.service.notifyservice.action";
    
    /** 轮询接收广播NotifyReceiver 的action 精选feed **/
    String RECEIVER_FEATURE_ACTION = "com.spriteapp.microvideo.feed.feature.action";
    /** 轮询接收广播NotifyReceiver 的action 最新feed **/
    String RECEIVER_NEW_ACTION = "com.spriteapp.microvideo.feed.new.action";
    /** 轮询接收广播NotifyReceiver 的action 动态 **/
    String RECEIVER_DYNIMAC_ACTION = "com.spriteapp.microvideo.dynimac.action";
    /** 轮询接收广播NotifyReceiver 的action 粉丝 **/
    String RECEIVER_FANS_ACTION = "com.spriteapp.microvideo.fans.action";
    

    

}
