package com.levin.qipa.provider;




import android.net.Uri;
import static com.levin.qipa.provider.AppProvider.*;
public class AppProviderSettings {
    
    /**
     * 创建表的uri
     * 
     * @param table
     * @param notify
     * @return
     */
    public static Uri buildContentUri(String table, boolean notify) {
        String notifyStr = "=false";
        if (notify) {
            notifyStr = "=true";
        }
        return Uri.parse("content://" + AUTHORITY + "/" + table + "?" + PARAMETER_NOTIFY + notifyStr);
    }

    /**
     * 获取表的uri
     * 
     * @param table
     * @param id
     * @param notify
     * @return
     */
    public static Uri getContentUri(String table, long id, boolean notify) {
        return Uri.parse("content://" + AUTHORITY + "/" + table + "/" + id + "?" + PARAMETER_NOTIFY + "=" + notify);
    }
    
}
