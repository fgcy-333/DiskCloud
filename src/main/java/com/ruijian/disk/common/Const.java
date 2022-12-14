package com.ruijian.disk.common;

import java.io.File;

public class Const {

    //磁盘用户状态
    public static final Integer DISK_DISABLE = 0;//禁用
    public static final Integer DISK_ENABLE = 1; //正常

    //根文件夹
    public static final long ROOT_PARENT_ID = 0L;

    //文件夹类型
    public static final Integer PERSONAL_FOLDER = 1;//个人
    public static final Integer GROUP_FOLDER = 2;//群组

    //文件或文件夹状态
    public static final Integer FORMAL = 0;
    public static final Integer DELETE = 1;


    public static final Long NO_GROUP_ID = -1L;


    //文件类型
    //图片
    private static final String FILE_TYPE_IMG = "img";
    //文本
    private static final String FILE_TYPE_TXT = "txt";
    private static final String FILE_TYPE_WROD = "word";
    private static final String FILE_TYPE_EXCEL = "excel";
    private static final String FILE_TYPE_PDF = "pdf";
    private static final String FILE_TYPE_PPT = "ppt";

    private static final String FILE_TYPE_VIDEO = "vedio";//视频

    private static final String FILE_TYPE_VOICE = "music";//音频

    private static final String FILE_TYPE_OTHER = "unknown";//其他

    private static final String FILE_TYPE_ZIP = "zip";//压缩文件

    //头像文件夹名称
    public static final String AVATAR = "avatar";
    //存储文件夹的路径
    public static final String AVATAR_PATH = System.getProperty("user.dir") + File.separator + AVATAR + File.separator;

    //操作选项
    public static String ADD_DISK_SPACE = "ADD";//加
    public static String SUBTRACT_DISK_SPACE = "SUBTRACT";//减


    public static String getFileType(String postfix) {
        postfix = postfix.toLowerCase();
        String type = FILE_TYPE_OTHER;
        switch (postfix) {

            //----------------------图片
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
                type = FILE_TYPE_WROD;
                break;

            case "pdf":
                type = FILE_TYPE_PDF;
                break;

            case "xls":
            case "xlsx":
            case "csv":
                type = FILE_TYPE_EXCEL;
                break;

            case "txt":
                type = FILE_TYPE_TXT;
                break;

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
                type = FILE_TYPE_OTHER;
                break;

            case "ppt":
            case "pptx":
                type = FILE_TYPE_PPT;
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

            case "zip":
            case "rar":
                type = FILE_TYPE_ZIP;
                break;

            default:
                type = FILE_TYPE_OTHER;
        }
        return type;
    }


}
