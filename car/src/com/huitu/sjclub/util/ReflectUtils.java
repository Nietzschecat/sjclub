package com.huitu.sjclub.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhongzhouping on 2017/10/30.
 */
public class ReflectUtils {
    public static Field[] getFields(Class clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        return  fields;
    }

    public static void writeMethod(Object obj, Field field, String value) throws Exception {
        if(field.getType() != String.class){
            System.out.println("类型不匹配，调用失败");
            return;
        }
        Class clazz = obj.getClass();
        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
        Method wM = pd.getWriteMethod();
        wM.invoke(obj, value);
    }

    public static String readMethod(Object obj, Field field) throws Exception {
        if(field.getType() != String.class){
            System.out.println("类型不匹配，调用失败");
            return null;
        }
        Class clazz = obj.getClass();
        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
        Method wM = pd.getReadMethod();
        return (String) wM.invoke(obj);
    }

}
