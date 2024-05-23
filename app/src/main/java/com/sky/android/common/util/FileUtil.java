/*
 * Copyright (c) 2020 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.android.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2020-11-28.
 */
public class FileUtil {

    public static final String TAG = "FileUtil";

    /**
     * 读取指定文件中的内容并以byte数组形式的返回文件内容
     * @param source 读取的文件路径
     * @return 读取的文件内容
     */
    public static byte[] readFileToByte(@NonNull File source) {

        FileInputStream input = null;
        ByteArrayOutputStream out = null;

        try {
            input = new FileInputStream(source);
            out = new ByteArrayOutputStream((int) source.length());

            int len;
            byte[] buffer = new byte[4096];

            while ((len = input.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

            return out.toByteArray();
        } catch (Exception e) {
            Alog.e(TAG, "获取文件信息异常", e);
        } finally {
            closeQuietly(out);
            closeQuietly(input);
        }
        return null;
    }

    /**
     * 把指定的byte数组内容保存到指定路径的文件中
     * @param target 保存的文件路径
     * @param content 需要保存的内容
     * @throws IOException
     */
    public static boolean writeFile(
            @NonNull File target,
            @NonNull byte[] content
    ) {
        FileOutputStream out = null;

        createParentDir(target);

        try {
            out = new FileOutputStream(target);
            out.write(content);
            out.flush();

            return true;
        }  catch (Exception e) {
            Alog.e(TAG, "写文件信息异常", e);
        } finally {
            closeQuietly(out);
        }
        return false;
    }

    /**
     * 把指定的内容保存到指定路径的文件中
     * @param target 保存的文件路径
     * @param content 需要保存的内容
     */
    public static boolean writeFile(
            @NonNull File target,
            String content,
            @NonNull String charsetName
    ) {

        if (TextUtils.isEmpty(content)) {
            return false;
        }

        if (TextUtils.isEmpty(charsetName)) {
            charsetName = "UTF-8";
        }

        try {
            // 保存信息到本地
            return writeFile(target, content.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            Alog.e(TAG, "保存文件异常", e);
        }
        return false;
    }

    /**
     * 复制文件
     * @param source
     * @param target
     * @return
     */
    public static boolean copyFile(@NonNull File source, @NonNull File target) {

        FileInputStream input = null;
        FileOutputStream out = null;

        if (!source.exists()) {
            return false;
        }

        createParentDir(target);

        try {
            int length;
            byte[] buffer = new byte[2048];

            input = new FileInputStream(source);
            out = new FileOutputStream(target);

            while ((length = input.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            out.flush();
            return true;
        }  catch (Exception e) {
            Alog.e(TAG, "复制文件信息异常", e);
        } finally {
            closeQuietly(out);
            closeQuietly(input);
        }
        return false;
    }

    /**
     * 读取文件内容(默认的编码方式UTF-8)
     * @param file
     * @return
     */
    public static String readFileToString(@NonNull File file) {
        return readFileToString(file, "UTF-8");
    }

    public static String readFileToString(@NonNull File file, @NonNull String charsetName) {

        if (!file.isFile()) {
            return null;
        }

        if (TextUtils.isEmpty(charsetName)) {
            charsetName = "UTF-8";
        }

        try {
            return new String(readFileToByte(file), charsetName);
        } catch (Exception e) {
            Alog.e(TAG, "读取文件数据异常!", e);
        }
        return null;
    }

    public static List<String> readFileToList(@Nullable File source) {

        if (!source.isFile()) {
            return null;
        }

        FileInputStream input = null;
        BufferedReader reader = null;

        List<String> lines = new ArrayList<>();

        try {
            input = new FileInputStream(source);
            reader = new BufferedReader(new InputStreamReader(input));
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (Exception e) {
            Alog.e(TAG, "获取文件信息异常", e);
        } finally {
            closeQuietly(reader);
            closeQuietly(input);
        }
        return null;
    }

    /**
     * 读取内容
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] readByStream(@Nullable InputStream in) throws IOException {

        ByteArrayOutputStream out = null;

        try {
            int len;
            byte[] buffer = new byte[2048];

            out = new ByteArrayOutputStream(2048);

            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } finally {
            closeQuietly(out);
        }
    }

    public static void deleteDir(@NonNull File dir) {

        if (!dir.exists()) return ;

        File[] files = dir.listFiles();

        if (files == null) return ;

        for (File file : files) {
            // 删除文件
            deleteFile(file);
        }

        // 删除目录
        deleteFile(dir);
    }

    /**
     * 创建文件
     * @param file
     * @return
     */
    public static boolean createFile(@NonNull File file) {

        if (file.isFile()) return true;

        // 创建目录
        createDir(file.getParentFile());

        try {
            return file.createNewFile();
        } catch (IOException e) {
            Alog.e("CreateNewFile Exception", e);
        }
        return false;
    }

    /**
     * 删除文件
     * @param file
     * @return
     */
    public static boolean deleteFile(@NonNull File file) {
        return file.isFile() && file.delete();
    }

    /**
     * 创建文件目录
     * @param file
     * @return
     */
    public static boolean createDir(@NonNull File file) {
        return file.exists() || file.mkdirs();
    }

    public static boolean exists(@NonNull File file) {
        return file.exists();
    }

    public static boolean exists(String dir, String name) {
        return exists(new File(dir, name));
    }

    /**
     * 创建文件的父目录
     * @param file
     * @return
     */
    public static boolean createParentDir(@NonNull File file) {
        File parentFile = file.getParentFile();
        return parentFile != null && createDir(parentFile);
    }

    /**
     * 读取Assets文件信息
     * @param context
     * @param name
     * @return
     */
    public static String readToString(@NonNull Context context, @NonNull String name) {

        byte[] bytes = readToByte(context, name);

        return bytes == null ? "" : new String(bytes);
    }

    /**
     * 读取Assets文件信息
     * @param context
     * @param name
     * @return
     */
    public static byte[] readToByte(@NonNull Context context, @NonNull String name) {

        InputStream input = null;
        ByteArrayOutputStream out = null;

        try {
            input = context.getAssets().open(name);
            out = new ByteArrayOutputStream(4096);

            int len;
            byte[] buffer = new byte[4096];

            while ((len = input.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

            return out.toByteArray();
        } catch (Exception e) {
            Alog.e(TAG, "获取文件信息异常", e);
        } finally {
            closeQuietly(out);
            closeQuietly(input);
        }
        return null;
    }

    /**
     * 扫描文件
     * @param context
     * @param file
     */
    public static void scanFile(@NonNull Context context, @NonNull File file) {

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));

        BroadcastUtil.sendBroadcast(context, intent);
    }

    public static void closeQuietly(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ioe) {
                // ignore
            }
        }
    }
}
