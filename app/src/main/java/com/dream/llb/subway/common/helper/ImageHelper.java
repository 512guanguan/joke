package com.dream.llb.subway.common.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Derrick on 2017/11/27.
 */

public class ImageHelper {
    /**
     * 读取一个bitmap文件
     * @param mContext
     * @param filePath
     * @return
     */
    public static Bitmap getBitmap(Context mContext, String filePath){
        try {
//            Log.i("llb","filePath = "+ filePath);
            FileInputStream fin = new FileInputStream(filePath);
            return BitmapFactory.decodeStream(fin);
        } catch (IOException e) {
//            Log.i("llb", "读取Bitmap:失败");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 保存bitmap到sd卡，格式为jpg
     * @param mContext
     * @param bitmap
     * @param quality 保存的图片之类
     * @return path 文件成功存储路径
     */
    public static String saveImage(Context mContext, Bitmap bitmap, int quality) {
        String path = FileHelper.getSDCardCacheDir(mContext) + "/captcha"+".jpg";
        File file = new File(path);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
            return path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();//TODO 待测试
        }
        return "";
    }
}
