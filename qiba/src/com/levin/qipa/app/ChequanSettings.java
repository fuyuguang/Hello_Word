package com.levin.qipa.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.levin.qipa.bean.User;

/**
 * 设置定义，提供统一的设置数据保存和获取方式。
 */
public class ChequanSettings {
    private String TAG = "ChequanSettings";
    private static final String SHARED_PREFERENCES_NAME = "com.chequan.settings";
    
    private final Context mContext;
    private SharedPreferences mGlobalPreferences;
    
    public ChequanSettings(Context context) {
        this.mContext = context;
        mGlobalPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
    }
    
    // 用户id
    public final StringPreference USER_ID = new StringPreference("user_id", "");
    public final StringPreference USER_NAME = new StringPreference("user_name", "");
    
    /**
     * 将用户信息持久化
     * 
     * @param user
     */
    public void persistenceUser(User user) {
        if (!TextUtils.isEmpty(user.getId())) {
            USER_ID.setValue(user.getId());
        }
    }
    
    /**
     * 获取当前用户信息
     * 
     * @return
     */
    public User unpersistenceUser() {
        String uid = USER_ID.getValue();
        User user = null;
        
        return user;
    }
    
    /**
     * 清空用户信息
     */
    public void clearUserInfo() {
        USER_ID.resetToDefault();
        USER_NAME.resetToDefault();
        
    }
    
    // ///////////////////////////////////////
    
    public abstract class CommonPreference<T> {
        private final String id;
        private T defaultValue;
        
        public CommonPreference(String id, T defaultValue) {
            this.id = id;
            this.defaultValue = defaultValue;
        }
        
        protected T getDefaultValue() {
            return defaultValue;
        }
        
        public abstract T getValue();
        
        public abstract boolean setValue(T val);
        
        public String getId() {
            return id;
        }
        
        /** 重置为初始值 */
        public void resetToDefault() {
            setValue(getDefaultValue());
        }
    }
    
    /** 整型数值参数保存 */
    public class IntPreference extends CommonPreference<Integer> {
        private IntPreference(String id, int defaultValue) {
            super(id, defaultValue);
        }
        
        @Override
        public Integer getValue() {
            return mGlobalPreferences.getInt(getId(), getDefaultValue());
        }
        
        @Override
        public boolean setValue(Integer val) {
            return mGlobalPreferences.edit().putInt(getId(), val).commit();
        }
    }
    
    /** 长整型数值参数保存 */
    public class LongPreference extends CommonPreference<Long> {
        private LongPreference(String id, long defaultValue) {
            super(id, defaultValue);
        }
        
        @Override
        public Long getValue() {
            return mGlobalPreferences.getLong(getId(), getDefaultValue());
        }
        
        @Override
        public boolean setValue(Long val) {
            return mGlobalPreferences.edit().putLong(getId(), val).commit();
        }
    }
    
    /** 布尔型参数保存 */
    public class BooleanPreference extends CommonPreference<Boolean> {
        private BooleanPreference(String id, boolean defaultValue) {
            super(id, defaultValue);
        }
        
        @Override
        public Boolean getValue() {
            return mGlobalPreferences.getBoolean(getId(), getDefaultValue());
        }
        
        @Override
        public boolean setValue(Boolean val) {
            return mGlobalPreferences.edit().putBoolean(getId(), val).commit();
        }
    }
    
    /** String参数保存 */
    public class StringPreference extends CommonPreference<String> {
        
        public StringPreference(String id, String defaultValue) {
            super(id, defaultValue);
        }
        
        @Override
        public String getValue() {
            return mGlobalPreferences.getString(getId(), getDefaultValue());
        }
        
        @Override
        public boolean setValue(String val) {
            return mGlobalPreferences.edit().putString(getId(), val).commit();
        }
    }
    
    /** 枚举型设置参数保存，适用于多选一。 */
    public class EnumIntPreference<E extends Enum<E>> extends CommonPreference<E> {
        private final E[] values;
        
        private EnumIntPreference(String id, E defaultValue, E[] values) {
            super(id, defaultValue);
            this.values = values;
        }
        
        @Override
        public E getValue() {
            try {
                int i = mGlobalPreferences.getInt(getId(), -1);
                if (i >= 0 && i < values.length) {
                    return values[i];
                }
            } catch (ClassCastException ex) {
                setValue(getDefaultValue());
            }
            return getDefaultValue();
        }
        
        @Override
        public boolean setValue(E val) {
            return mGlobalPreferences.edit().putInt(getId(), val.ordinal()).commit();
        }
    }
    
}
