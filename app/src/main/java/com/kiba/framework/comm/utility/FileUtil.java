package com.kiba.framework.comm.utility;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static File saveToFile(Bitmap bitmap, String outPath, String fileName) throws IOException {
        // create a directory ArcGIS to save the file
        File root;
        File file = null;
        try {
            File fileDir = new File(outPath);
            boolean isDirectoryCreated = fileDir.exists();
            if (!isDirectoryCreated) {
                isDirectoryCreated = fileDir.mkdirs();
            }
            if (isDirectoryCreated) {
                file = new File(fileDir, fileName);
                // write the bitmap to PNG file
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                // close the stream
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {

        }
        return file;
    }

    /**
     * 从sd卡获取图片文件列表===符合到场类型
     *
     * @return
     */
    public static List<File> getImageFilesFromSD(String YWH, String DCLX, String appFileName ) {
        List<File> picFiles = new ArrayList<File>();
        // 得到该路径文件夹下所有的文件
        String root = Environment.getExternalStorageDirectory().getPath();
        File fileAll = new File(root, String.format("%s/%s文件夹", appFileName, YWH));

        File[] files = fileAll.listFiles();
        //将地图图片截图放到集合中最后一个
        File mapFile = null;
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.getName().contains(DCLX) && checkIsImageFile(file.getPath())) {
                if (file.getName().contains("MAP")) {
                    mapFile = file;
                    continue;
                }
                picFiles.add(file);
            }
        }
        if (mapFile != null) {
            picFiles.add(mapFile);
        }
        // 返回得到的图片文件列表
        return picFiles;
    }


    /**
     * 从sd卡删除指定路径文件
     *
     * @return
     */
    public static boolean deleteFileFromSD(String fileName,String CurrentAppUserFileDir) {
        String filePath = CurrentAppUserFileDir + String.format("/%s", fileName);
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     *
     * @param fName 文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    static boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }
}
