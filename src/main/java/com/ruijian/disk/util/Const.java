package com.ruijian.disk.util;

public class Const {
    private static final int FILE_TYPE_IMG = 1;
    private static final int FILE_TYPE_TXT = 2;
    private static final int FILE_TYPE_VIDEO = 3;
    private static final int FILE_TYPE_VOICE = 4;
    private static final int FILE_TYPE_OTHER = 5;


    public static String ADD_DISK_SPACE = "ADD";

    public static String SUBTRACT_DISK_SPACE = "SUBTRACT";
    private static String uniqueStr;

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
