package com.ruijian.disk.common;

public enum Code {
    SUCCESS(2000, "操作成功!"),

    UPLOAD_FAIL(5000, "上传错误"),

    DOWNLOAD_ERROR(5001, "下载出错"),

    NO_PERMISSION(5002, "您没有权限执行该操作"),

    DISABLE_DISK_FAIL(5003, "禁用用户出错"),

    RENAME_ERR(5004, "重命名失败"),

    NEW_FOLDER_FAIL(5005, "新建文件夹失败");


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
