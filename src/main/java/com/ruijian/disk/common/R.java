package com.ruijian.disk.common;


import java.util.HashMap;


public class R {

    // 结果状态码
    private int code;

    // 响应信息
    private String msg;

    // 响应数据
    private HashMap<String, Object> data = new HashMap<>();

    //是否成功
    private boolean success;


    public static R success() {
        R r = new R();
        r.setSuccess(true).setCode(Code.SUCCESS.getCode()).setMsg(Code.SUCCESS.getMsg());
        return r;
    }

    public static R fail(Code code) {
        R r = new R();
        r.setSuccess(false).setCode(code.getCode()).setMsg(code.getMsg());
        return r;
    }


    public int getCode() {
        return code;
    }

    public R setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public R setData(String key, Object val) {
        this.data.put(key, val);
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public R setSuccess(boolean success) {
        this.success = success;
        return this;
    }
}