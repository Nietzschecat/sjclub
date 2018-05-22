package com.huitu.sjclub.util;

/**
 * Created by zhongzhouping on 2017/10/24.
 */
public class StringUtils {
    public static Boolean isValue(String src){
        if(null == src || "".equals(src) || "null".equals(src)){
            return false;
        }
        return true;
    }
}
