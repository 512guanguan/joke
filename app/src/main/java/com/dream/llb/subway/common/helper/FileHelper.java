package com.dream.llb.subway.common.helper;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * 文件处理相关的方法
 *
 */
public class FileHelper {
    public static String cacheDirectory = "";
    /**
     * 检查单个文件大小
     *
     * @param path 文件路径
     * @return 不是文件时返回-1
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file.length();
        } else {
            return -1;
        }
    }

    /**初始化临时缓存文件夹路径
     * @return
     */
    public static String getSDCardCacheDir(Context mContext){
        if(!TextUtils.isEmpty(cacheDirectory)){
            return cacheDirectory;
        }
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            cacheDirectory = mContext.getExternalCacheDir().getPath();// SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
        }else {
            cacheDirectory = mContext.getCacheDir().getPath();//获取/data/data/<application package>/cache目录
        }
        return cacheDirectory;
    }

}
