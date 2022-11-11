package com.ruijian.disk.util;

import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.pojo.CloudFolder;
import com.ruijian.disk.service.CloudFileService;
import com.ruijian.disk.service.CloudFolderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipUtil {
    @Autowired
    private CloudFolderService folderService;

    @Autowired
    private CloudFileService cloudFileService;

    @Autowired
    private static ZipUtil zipUtil;

    @Autowired
    private HdfsUtil hdfsUtil;


    private static Logger log = Logger.getLogger(ZipUtil.class);

    @PostConstruct
    public void init() {
        zipUtil = this;
        zipUtil.folderService = this.folderService;
        zipUtil.cloudFileService = this.cloudFileService;
        zipUtil.hdfsUtil = this.hdfsUtil;
    }

    public static Map<String, String> putFileToZipEntry(HashMap<String, List<Long>> downloadTypeMap, String localAbsPath) throws Exception {
        if (downloadTypeMap == null || downloadTypeMap.isEmpty() || StringUtil.isBlank(localAbsPath)) {
            return null;
        }
        final HashMap<String, String> map = new HashMap<>();
        ZipOutputStream zipOutputStream = null;
        try {

            final List<Long> fileList = downloadTypeMap.get("file");
            final List<Long> folderList = downloadTypeMap.get("folder");
            //单文件夹下载
            if (fileList == null && folderList.size() == 1) {
                final CloudFolder folder = zipUtil.folderService.getFolderObjByFolderId(folderList.get(0));
                if (folder == null) {
                    throw new Exception("单文件夹下载发现folder为空");
                }
                final String currentPath = zipUtil.folderService.getCurrentPathByFolderId(folder.getFolderId());
                //下载文件夹
                zipUtil.hdfsUtil.downloadFile(currentPath, localAbsPath + folder.getHdfsFolderName());
                //创建一个压缩流
                final String localPath = localAbsPath + folder.getHdfsFolderName() + ".zip";
                zipOutputStream = new ZipOutputStream(new FileOutputStream(localPath));

                //集合映射hdfs名字和真实名字
                final HashMap<String, String> fileNameMap = new HashMap<>();
                getAllNameByFolderId(folder.getFolderId(), fileNameMap);

                final List<Map<String, String>> files = zipUtil.hdfsUtil.listFile(currentPath);

                for (Map<String, String> file : files) {
                    final String tempFilePath = file.get("filePath");
                    String filePath = handlerFilePath(tempFilePath);
                    zipUtil.putFileIntoZipStream(zipOutputStream, filePath, fileNameMap, folder.getFileFolderName(), localAbsPath);
                }

                map.put("localPath", localPath);
                map.put("name", folder.getFileFolderName() + ".zip");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (zipOutputStream != null) {
                zipOutputStream.flush();
                zipOutputStream.flush();
            }
        }
        return map;
    }


    private static String handlerFilePath(String tempFilePath) {
        final String[] arr = tempFilePath.split("/");
        if (arr.length >= 4) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 4; i < arr.length; i++) {
                sb.append(arr[i]).append(File.separator);
            }
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

    /**
     * @param zipOutputStream
     * @param hdfsName
     * @param fileNameMap
     */
    private void putFileIntoZipStream(ZipOutputStream zipOutputStream, String hdfsName, HashMap<String, String> fileNameMap, String currentFolderName, String localAbsPath) throws Exception {
        if (hdfsName.contains("\\")) {
            final String[] splitStr = hdfsName.split("\\\\");
            final List<String> collect = Arrays.stream(splitStr).collect(Collectors.toList());
            final Iterator<String> iterator = collect.iterator();
            while (iterator.hasNext()) {
                final String next = iterator.next();
                if (!next.contains(".")) {
                    iterator.remove();
                    break;
                }
            }

            //hdfs路径转为真实路径
            for (int i = 0; i < collect.size(); i++) {
                String s = collect.get(i);
                if (StringUtil.isBlank(fileNameMap.get(s))) {
                    throw new Exception("hdfs名转真实名时，找不到");
                }
                collect.set(i, fileNameMap.get(s));
            }
            //拼接真实路径
            StringBuilder realPath = new StringBuilder("/");
            for (String s : collect) {
                realPath.append(s).append("/");
            }
            final String zipPathItem = currentFolderName + realPath.substring(0, realPath.length() - 1);
            zipOutputStream.putNextEntry(new ZipEntry(zipPathItem));

            addToZOS(new FileInputStream(localAbsPath + hdfsName), zipOutputStream);
        } else {
            hdfsName = fileNameMap.get(hdfsName);
            zipOutputStream.putNextEntry(new ZipEntry(hdfsName));
        }
    }

    /**
     * 将远程路径 转为数据库中 记录了的文件真实路径
     *
     * @param hdfsName
     * @param fileNameMap
     * @param folderName
     * @return
     */
    // TODO: 2022/10/31
    private String hdfsPathToRealPath(String hdfsName, HashMap<String, String> fileNameMap, String folderName) {

        return null;
    }


    public static void getAllNameByFolderId(Long folderId, Map<String, String> fileNameMap) {
        //文件
        final List<CloudFile> fileObjs = zipUtil.cloudFileService.getFileObjsByFolderId(folderId);
        for (CloudFile fileObj : fileObjs) {
            fileNameMap.put(fileObj.getHdfsFileName(), fileObj.getFileName());
        }
        //文件夹
        final List<CloudFolder> folderObjs = zipUtil.folderService.getFolderObjsByParentFolderId(folderId);
        for (CloudFolder folderObj : folderObjs) {
            fileNameMap.put(folderObj.getHdfsFolderName(), folderObj.getFileFolderName());
            getAllNameByFolderId(folderObj.getFolderId(), fileNameMap);
        }
    }


    /**
     * 添加文件到压缩流
     *
     * @param in  输入流（一般就直接是从FTP中获取的）
     * @param zos 压缩输出流
     * @throws IOException
     */
    public static void addToZOS(InputStream in, ZipOutputStream zos) throws IOException {

        byte[] buf = new byte[1024];
        //BufferedOutputStream bzos = new BufferedOutputStream(zos);
        try {
            for (int len; (len = (in.read(buf))) != -1; ) {
                zos.write(buf, 0, len);
            }
        } catch (IOException e) {
            System.out.println("流转换异常");
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("关流异常");
                    log.error(e.getMessage());
                }
            }
        }
    }
}
