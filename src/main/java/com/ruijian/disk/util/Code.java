package com.ruijian.disk.util;

public enum Code {
    SUCCESS(2000, "操作成功!");

    private final int code;
    private final String msg;

    Code(int code, String msg) {
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
