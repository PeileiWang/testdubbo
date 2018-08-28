package com.dubbo.resultenum;

/**
 * Created by Wangpl
 * Time 2018/8/7 14:41
 */
public enum ResEnum {
    UNKNOWN_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    ABC(100, "名字是ABC"),
    DEF(101, "名字是DEF")
    ;
    private int code;
    private String msg;

    ResEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
