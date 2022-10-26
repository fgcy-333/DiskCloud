package com.ruijian.disk.util;


public class R<T> {

    // 结果状态码
    private int code;

    // 响应信息
    private String msg;

    // 响应数据
    private T data;

    // 接口请求时间
    private boolean success;


    public static <T> R<T> success(T data) {
        R r = new R();
        r.setSuccess(true);
        r.setCode(Code.SUCCESS.getCode());
        r.setMsg(Code.SUCCESS.getMsg());
        r.setData(data);
        return r;
    }

    public static <T> R<T> fail(Code code) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(code.getCode());
        r.setMsg(code.getMsg());
        return r;
    }

    public int getCode() {
        return code;
    }

    public R<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public R<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public R<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

}