package com.dubbo.utils;

import com.dubbo.domain.Result;
import com.dubbo.resultenum.ResEnum;

/**
 * Created by Wangpl
 * Time 2018/8/7 9:27
 */
public class ResultUtil{

    public static Result success(ResEnum resEnum, Object object) {
        Result result = new Result();
        result.setCode(resEnum.getCode());
        result.setMsg(resEnum.getMsg());
        result.setData(object);
        return result;
    }

    public static Result success(ResEnum resEnum) {
        return success(resEnum ,null);
    }

    public static Result error(ResEnum resEnum) {
        Result result = new Result();
        result.setCode(resEnum.getCode());
        result.setMsg(resEnum.getMsg());
        return result;
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}