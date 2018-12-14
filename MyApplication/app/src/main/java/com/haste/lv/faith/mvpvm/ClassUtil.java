package com.haste.lv.faith.mvpvm;

import android.support.annotation.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by lv on 18-12-12.
 * 通过反射实例化对象的工具
 */
public class ClassUtil {
    public static <T> T getNewInstance(Object object, int i) {
        if (object != null) {
            try {
                return ((Class<T>) ((ParameterizedType) (object.getClass()
                        .getGenericSuperclass())).getActualTypeArguments()[i])
                        .newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }

        }
        return null;

    }
    public static <T> T getNewInstance(ParameterizedType type, int i) {
        if (type != null) {
            try {
                return ((Class<T>) type.getActualTypeArguments()[i])
                        .newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }

        }
        return null;

    }
    /**
     * 获取泛型的Class clazz
     * 1.getGenericSuperclass()返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
     * 2.getActualTypeArguments()[i]中 ‘i’ 代表的是第i个泛型类型。
     */
    public static <T> T getInstance(Object object, int i) {
        if (object != null) {
            return (T) ((ParameterizedType) object.getClass()
                    .getGenericSuperclass())
                    .getActualTypeArguments()[i];
        }
        return null;

    }

    public static <T> T getInstance(ParameterizedType type, int i) {
        if (type != null) {
            return (T) type.getActualTypeArguments()[i];
        }
        return null;

    }

    public static @NonNull
    <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
