package com.haste.lv.faith.network.exception;


/**
 * Created by lv on 18-12-13.
 * 接口返回的错误异常
 */
public class ResponseThrowable extends Exception {
    public int code;
    public String message;

    public ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

}
