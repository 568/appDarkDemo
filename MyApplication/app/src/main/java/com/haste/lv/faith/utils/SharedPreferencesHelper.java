package com.haste.lv.faith.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Set;

/**
 * 操作SharedPreferences工具封装
 * Created by lv on 18-11-21.
 * 使用方法：
 * <p>
 * 在application中的 onCreate() 初始化
 * <p>
 * SharedPreferencesHelper.init(applicationContext,spName);
 * 调用如下：
 * //通过apply()方式异步存入数据
 * SharedPreferencesHelper.applyInt("int_key", 1);
 * SharedPreferencesHelper.applyLong("long_key", 1L);
 * SharedPreferencesHelper.applyFloat("float_key", 1.0f);
 * SharedPreferencesHelper.applyString("str_key", "1");
 * SharedPreferencesHelper.applyBoolean("boolean_key", true);
 * SharedPreferencesHelper.applyStringSet("set_test",mySet);
 * <p>
 * //通过commit()方式存入数据
 * SharedPreferencesHelper.commitInt("int_key", 1);
 * SharedPreferencesHelper.commitLong("long_key", 1L);
 * SharedPreferencesHelper.commitFloat("float_key", 1.0f);
 * SharedPreferencesHelper.commitString("str_key", "1");
 * SharedPreferencesHelper.commitBoolean("boolean_key", true);
 * SharedPreferencesHelper.commitStringSet("set_test",mySet);
 * <p>
 * //获取数据
 * SharedPreferencesHelper.getInt("int_key", 1);
 * SharedPreferencesHelper.getLong("long_key", 1L);
 * SharedPreferencesHelper.getFloat("float_key", 1.0f);
 * SharedPreferencesHelper.getString("str_key", "1");
 * SharedPreferencesHelper.getBoolean("boolean_key", true);
 * SharedPreferencesHelper.getStringSet("set_test", null);
 * <p>
 * //清空
 * SharedPreferencesHelper.clear();
 * <p>
 * //删除
 * SharedPreferencesHelper.removeKey("remove_key");
 **/

public class SharedPreferencesHelper {
    private static SharedPreferences mSharedPreferences = null;

    /**
     * 初始化 ** @param applicationContext * @param spName
     */
    public static void init(Context applicationContext, String spName) {
        if (TextUtils.isEmpty(spName)) {
            throw new NullPointerException("When SharedPreferencesHelper init, SharedPreferences name is null!");
        }
        if (applicationContext == null) {
            throw new NullPointerException("When SharedPreferencesHelper init, applicationContext is null!");
        }
        mSharedPreferences = applicationContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        //初始化默认值
        applyBoolean("APP_NIGHT_MODE", false);
    }

    /**
     * 删除key * * @param key The name of the preference to remove.
     */
    public static void removeKey(String key) {
        if (mSharedPreferences != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.remove(key);
            editor.apply();
        } else {
            //mSharedPreferences is null
        }
    }

    public static void clear() {
        if (mSharedPreferences != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.clear();
            editor.apply();
        } else {
            //mSharedPreferences is null
        }
    }

    public static void applyInt(String key, int value) {
        applyKeyValue(key, value);
    }

    public static void applyLong(String key, long value) {
        applyKeyValue(key, value);
    }

    public static void applyFloat(String key, float value) {
        applyKeyValue(key, value);
    }

    public static void applyBoolean(String key, boolean value) {
        applyKeyValue(key, value);
    }

    public static void applyString(String key, String value) {
        if (null == value) {//防止传入null时无法判断存储类型
            applyKeyValue(key, "");
            return;
        }
        applyKeyValue(key, value);
    }

    public static void applyStringSet(String key, Set<String> defValue) {
        if (mSharedPreferences != null && null == defValue) {//防止传入null时无法判断存储类型
            mSharedPreferences.edit().putStringSet(key, null).apply();
        }
        commitKeyValue(key, defValue);
    }

    public static boolean commitInt(String key, int value) {
        return commitKeyValue(key, value);
    }

    public static boolean commitLong(String key, long value) {
        return commitKeyValue(key, value);
    }

    public static boolean commitFloat(String key, float value) {
        return commitKeyValue(key, value);
    }

    public static boolean commitBoolean(String key, boolean value) {
        return commitKeyValue(key, value);
    }

    public static boolean commitString(String key, String value) {
        if (null == value) {//防止传入null时无法判断存储类型
            return commitKeyValue(key, "");
        }
        return commitKeyValue(key, value);
    }

    public static boolean commitStringSet(String key, Set<String> defValue) {
        if (mSharedPreferences != null && null == defValue) {//防止传入null时无法判断存储类型
            return mSharedPreferences.edit().putStringSet(key, null).commit();
        }
        return commitKeyValue(key, defValue);
    }


    public static int getInt(String key, int defValue) {
        return (int) getValue(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return (long) getValue(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return (float) getValue(key, defValue);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return (boolean) getValue(key, defValue);
    }

    public static String getString(String key, String defValue) {
        if (null == defValue) {//防止传入null时无法判断存储类型
            return (String) getValue(key, "");
        }
        return (String) getValue(key, defValue);
    }

    public static Set<String> getStringSet(String key, Set<String> defValue) {
        if (mSharedPreferences != null && null == defValue) {//防止传入null时无法判断存储类型
            return mSharedPreferences.getStringSet(key, null);
        }
        return (Set<String>) getValue(key, defValue);
    }

    /**
     * 异步提交保存
     * 注：不提供该方法给外部是因为，传入的是Object，类型表意不够明确，使用易误导。
     *
     * @param key
     * @param value
     * @return
     */
    private static void applyKeyValue(String key, Object value) {
        if (mSharedPreferences == null) {
            //mSharedPreferences is null
            return;
        }

        SharedPreferences.Editor editor = setEditor(key, value);

        editor.apply();
    }


    /**
     * 立即提交保存，立即生效，立即返回保存到磁盘的成功与否
     * 注：不提供该方法给外部是因为，传入的是Object，类型表意不够明确，使用易误导。
     *
     * @param key
     * @param value
     * @return
     */
    private static boolean commitKeyValue(String key, Object value) {
        if (mSharedPreferences == null) {
            //mSharedPreferences is null
            return false;
        }

        SharedPreferences.Editor editor = setEditor(key, value);

        return editor.commit();

    }

    /**
     * 统一保存处理
     *
     * @param key
     * @param value
     * @return
     */
    private static SharedPreferences.Editor setEditor(String key, Object value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        }
        return editor;
    }

    /**
     * 统一获取处理
     *
     * @param key
     * @param defValue
     * @return
     */
    private static Object getValue(String key, Object defValue) {

        if (mSharedPreferences == null) {
            //mSharedPreferences is null
            return defValue;
        }

        if (defValue instanceof String) {
            return mSharedPreferences.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return mSharedPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Long) {
            return mSharedPreferences.getLong(key, (Long) defValue);
        } else if (defValue instanceof Float) {
            return mSharedPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Boolean) {
            return mSharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Set) {
            return mSharedPreferences.getStringSet(key, (Set<String>) defValue);
        }
        return defValue;
    }

}