package com.huitu.sjclub.util;

/**
 * Created by gsp on 2017-09-18.
 */


public class Result {
    /**
     * 错误消息，为空时，代表验证通过
     */
    private String error;


    public boolean isLegal() {
        //两个变量为默认值,即认为是合法的
        return error == null || error.equals("");
    }

    public String getError() {
        return error;
    }

    public void setError(String message) {
        this.error = message;
    }


}