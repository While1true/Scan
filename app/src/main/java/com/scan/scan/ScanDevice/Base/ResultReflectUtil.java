package com.scan.scan.ScanDevice.Base;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ck on 2018/12/6.
 */

public class ResultReflectUtil {
    public static void onResult(Activity mActivity, String result){
        Method decodeComplete = getMethod(mActivity.getClass(), "onDecodeComplete", String.class);
        try {
            decodeComplete.invoke(mActivity,result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    private static Method getMethod(Class<?> clazz, String methodName, Class<?>... ParamsType) {
        Method method = null;
        while (clazz != null) {
            try {
                method = clazz.getDeclaredMethod(methodName, ParamsType);
                method.setAccessible(true);
                return method;
            } catch (Exception e) {
            }
            clazz = clazz.getSuperclass();
        }
        return method;
    }

}
