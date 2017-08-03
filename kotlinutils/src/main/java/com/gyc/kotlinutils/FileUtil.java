package com.gyc.kotlinutils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class FileUtil {

    public static String SDPATH = Environment.getExternalStorageDirectory() + "/temp/";

    public static void saveBitmap(Bitmap bm, String picName) {
        Log.e("", "保存图片");
        try {
            if (!isFileExist("")) {
                File tempf = createSDDir("");
            }
            File f = new File(SDPATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            Log.e("", "已经保存");
        } catch (Exception e) {
            Log.e("FileUtil", e.getMessage());
        }
    }

    /**
     * 删除该目录下的文件
     *
     * @param path
     */
    public static void delFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            dir.mkdirs();

            Log.i("FileUtil", dir.getAbsolutePath());
        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();

        return file.exists();
    }

    public static void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
        }
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static void deleteDir(String path) {
        File dir = new File(path);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(path); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 判断字符串是否为空或一串空白？
     *
     * @param str
     * @return 参数为null或空白串都将返回true，否则返回false。
     */
    private static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 写入文件内容
     *
     * @param filePath
     * @param content
     * @throws IOException
     */
    public static void write(String filePath, String content) throws IOException {
        if (isBlank(filePath) || isBlank(content)) {
            Log.e("FileUtil", "写文件失败！文件路径和文件内容不能为空。filePath = " + filePath + ", content = " + content);
            return;
        }

        write(new File(filePath), content);
    }

    /**
     * 写入文件内容
     *
     * @param targetFile
     * @param content
     * @throws IOException
     */
    public static void write(File targetFile, String content) throws IOException {
        Reader contentReader = null;
        Writer targetWriter = null;
        OutputStream os = null;
        try {
            if (targetFile.exists()) {
                targetFile.delete();
            } else {
                targetFile.getParentFile().mkdirs();
            }

            targetFile.createNewFile();

            contentReader = new java.io.StringReader(content);
            os = new FileOutputStream(targetFile);
            targetWriter = new java.io.OutputStreamWriter(os, "UTF-8");

            int bytesRead = 0;
            char[] buffer = new char[8192];
            while ((bytesRead = contentReader.read(buffer, 0, 8192)) != -1) {
                targetWriter.write(buffer, 0, bytesRead);
            }

            targetWriter.flush();
        } finally {
            if (contentReader != null) {
                try {
                    contentReader.close();
                } catch (IOException e) {
                }
            }
            if (targetWriter != null) {
                try {
                    targetWriter.close();
                } catch (IOException e) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return
     */
    public static boolean exist(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            return false;
        }

        return true;
    }

    /**
     * 读取文件内容
     *
     * @param file
     * @return
     */
    public static String read(File file) {
        InputStreamReader reader = null;
        BufferedReader bufferReader = null;

        StringBuffer sb = new StringBuffer();
        try {

            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            bufferReader = new BufferedReader(reader);

            String line;
            while ((line = bufferReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (Exception e) {
            Log.e("FileUtil", "读取文件内容失败！file=" + file.getAbsolutePath(), e);
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                }
            if (bufferReader != null)
                try {
                    bufferReader.close();
                } catch (IOException e) {
                }
        }

        return sb.toString();
    }

}
