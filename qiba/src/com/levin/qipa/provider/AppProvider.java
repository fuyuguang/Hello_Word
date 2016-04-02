package com.levin.qipa.provider;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AppProvider extends ContentProvider {
    public static final String DATABASE_NAME = "super_face.db";
    public static final int DATABASE_VERSION = 1;
    
    public static final String AUTHORITY = "com.sprite.superface";
    public static final String PARAMETER_NOTIFY = "notify";
    
    
    /** 本地收藏表**/
    public static final String TABLE_FAVORITE = "t_favorite";
    /** 本地表情表**/
    public static final String TABLE_FACE = "t_face";
    //
    private BudejieDatabaseHelper mOpenHelper;
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SqlArguments arguments = new SqlArguments(uri, selection, selectionArgs);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = db.delete(arguments.table, arguments.where, arguments.args);
        if (count > 0) {
            sendNotify(uri);
        }
        return count;
    }
    
    @Override
    public String getType(Uri uri) {
        SqlArguments arguments = new SqlArguments(uri, null, null);
        if (TextUtils.isEmpty(arguments.where)) {
            return "vnd.android.cursor.dir/" + arguments.table;
        } else {
            return "vnd.android.cursor.item/" + arguments.table;
        }
    }
    
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SqlArguments arguments = new SqlArguments(uri);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final long rowId = db.insert(arguments.table, null, values);
        if (rowId <= 0) {
            return null;
        }
        uri = ContentUris.withAppendedId(uri, rowId);
        sendNotify(uri);
        return uri;
    }
    
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SqlArguments arguments = new SqlArguments(uri);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int result = 0;//插入成功变为1
        int numValues = 0;
        db.beginTransaction(); //开始事务
        try {
                //数据库操作
            numValues = values.length;
           for (int i = 0; i < numValues; i++) {
               db.insert(arguments.table, null, values[i]);
           }
           db.setTransactionSuccessful(); //别忘了这句 Commit
           result = 1;
        } finally {
            db.endTransaction(); //结束事务
        }
        return result;
    }      
    @Override
    public void shutdown() {
        super.shutdown();
        mOpenHelper.close();
    }
    
    private void sendNotify(Uri uri) {
        String notify = uri.getQueryParameter(PARAMETER_NOTIFY);
        if (notify == null || "true".equals(notify)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
    
    @Override
    public boolean onCreate() {
        mOpenHelper = new BudejieDatabaseHelper(getContext());
        return true;
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SqlArguments arguments = new SqlArguments(uri, selection, selectionArgs);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(arguments.table);
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, arguments.where, arguments.args, null, null, sortOrder);
        return cursor;
    }
    
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SqlArguments arguments = new SqlArguments(uri, selection, selectionArgs);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = db.update(arguments.table, values, arguments.where, arguments.args);
        if (count > 0) {
            sendNotify(uri);
        }
        return count;
    }
    
    public static class BudejieDatabaseHelper extends SQLiteOpenHelper {
        Context context;
        
        public BudejieDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }
        
        @Override
        public void onCreate(SQLiteDatabase db) {
            //TODO
        }
        
        @Override
        public synchronized void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < DATABASE_VERSION) {
                int count = DATABASE_VERSION-oldVersion;
                for(int i=1;i<=count;i++){
                    changeOnUpgrade(db,oldVersion+i);
                }
            }
            
        }
        
        /**
         * 每次升级数据库的改动
         * @param db
         * @param version
         */
        private void changeOnUpgrade(SQLiteDatabase db, int version) {
            switch (version) {
            }
        }
        
        @Override
        public synchronized void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Database", "onDowngrade, Destroying all old data.");
            // TODO 数据库降级时，删除所有内容
        }
        
    }
    
    static class SqlArguments {
        public final String table;
        public final String where;
        public final String[] args;
        
        public SqlArguments(Uri url, String where, String[] args) {
            if (url.getPathSegments().size() == 1) {
                this.table = url.getPathSegments().get(0);
                this.where = where;
                this.args = args;
                // uri path 不等于 1，并且不等于 2 抛出非法的参数异常
            } else if (url.getPathSegments().size() != 2) {
                throw new IllegalArgumentException("Invalid URI :" + url);
                // 如果uri中存在id 将不支持where子句，id是唯一标识。
            } else if (!TextUtils.isEmpty(where)) {
                throw new UnsupportedOperationException("WHERE clause not supported :" + url);
            } else {
                this.table = url.getPathSegments().get(0);
                this.where = "id=" + ContentUris.parseId(url);
                this.args = null;
            }
        }
        
        public SqlArguments(Uri url) {
            if (url.getPathSegments().size() == 1) {
                this.table = url.getPathSegments().get(0);
                this.where = null;
                this.args = null;
            } else {
                throw new IllegalArgumentException("Invalid URI :" + url);
            }
        }
        
    }
}
