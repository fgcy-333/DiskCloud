package com.ruijian.disk.common;

import java.io.File;

public class Const {

    //磁盘用户状态
    public static final Integer DISK_DISABLE = 0;//禁用
    public static final Integer DISK_ENABLE = 1; //正常
    public static final long ROOT_PARENT_ID = 0L;

    //文件类型
    private static final int FILE_TYPE_IMG = 1;//图片
    private static final int FILE_TYPE_TXT = 2;//文本
    private static final int FILE_TYPE_VIDEO = 3;//视频
    private static final int FILE_TYPE_VOICE = 4;//音频
    private static final int FILE_TYPE_OTHER = 5;//其他

    //头像文件夹名称
    public static final String AVATAR = "avatar";
    //存储文件夹的路径
    public static final String AVATAR_PATH = System.getProperty("user.dir") + File.separator + AVATAR + File.separator;

    //操作选项
    public static String ADD_DISK_SPACE = "ADD";//加
    public static String SUBTRACT_DISK_SPACE = "SUBTRACT";//减


    public static int getFileType(String postfix) {
        postfix = postfix.toLowerCase();
        int type = FILE_TYPE_OTHER;
        switch (postfix) {
            case "png":
            case "jpg":
            case "jpeg":
            case "bmp":
            case "gif":
            case "pic":
            case "webp":
            case "svg":
                type = FILE_TYPE_IMG;
                break;
            case "doc":
            case "docx":
            case "pdf":
            case "xls":
            case "xlsx":
            case "csv":
            case "txt":
            case "html":
            case "js":
            case "c":
            case "md":
            case "md5":
            case "java":
            case "php":
            case "py":
            case "cpp":
            case "cs":
            case "conf":
            case "ppt":
            case "pptx":
                type = FILE_TYPE_TXT;
                break;
            case "flv":
            case "mp4":
            case "rmvb":
            case "avi":
            case "wav":
                type = FILE_TYPE_VIDEO;
                break;
            case "mp3":
            case "wma":
            case "flac":
                type = FILE_TYPE_VOICE;
                break;
        }
        return type;
    }


}
