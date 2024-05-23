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

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * Created by sky on 2020-11-28.
 */
public class MD5Util {

    private static final String TAG = "MD5Util";

    private MD5Util() {

    }

    /**
     * Convert byte[] to hex string
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src){

        if (src == null || src.length <= 0) return null;

        StringBuilder builder = new StringBuilder();

        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {

        if (TextUtils.isEmpty(hexString)) return null;

        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            bytes[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return bytes;
    }

    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String md5sum(String value) {
        return md5sum(value, "UTF-8");
    }

    public static String md5sum(String value, String charsetName) {

        if (TextUtils.isEmpty(value)) return null;

        try {
            return md5sum(value.getBytes(charsetName));
        } catch (Exception e) {
            Log.e(TAG, "字符串编码异常", e);
        }
        return null;
    }

    public static String md5sum(byte[] value) {

        if (value == null) return null;

        try {
            // 计算MD5值信息
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(value);

            byte[] bytes = messageDigest.digest();

            return bytesToHexString(bytes);
        } catch (Exception e) {
            Log.e(TAG, "处理MD5异常", e);
        }
        return null;
    }

    public static String md5sum(@NonNull File file) {

        if (!file.isFile()) {
            return null;
        }

        FileInputStream input = null;

        try {
            // 计算MD5值信息
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();

            input = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = input.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, length);
            }

            return bytesToHexString(digest.digest());
        } catch (Exception e) {
            Alog.e(TAG, "处理MD5异常", e);
        } finally {
            // 关闭
            FileUtil.closeQuietly(input);
        }
        return null;
    }
}
