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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import androidx.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by sky on 2020-11-28.
 */
public class PackageUtil {

    private static final String TAG = "PackageUtil";

    private PackageUtil() {
    }

    public static String getSerialNumber(@NonNull Context context, @NonNull String packageName) {

        try {
            PackageInfo packageInfo = getPackageInfo(
                    context, packageName, PackageManager.GET_SIGNATURES);

            if (packageInfo == null) return null;

            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate)
                    certFactory.generateCertificate(new ByteArrayInputStream(sign.toByteArray()));

            return certificate.getSerialNumber().toString();
        } catch (Exception e) {
            Alog.e(TAG, "获取包签名异常", e);
        }
        return null;
    }

    /**
     * 获取指定包的版本名
     * @param context
     * @param packageName
     * @return
     */
    public static String getVersionName(@NonNull Context context, @NonNull String packageName) {

        PackageInfo packageInfo = getPackageInfo(context, packageName, 0);

        return packageInfo != null ? packageInfo.versionName : null;
    }

    /**
     * 获取指定包的版本号
     * @param context
     * @param packageName
     * @return
     */
    public static int getVersionCode(@NonNull Context context, @NonNull String packageName) {

        PackageInfo packageInfo = getPackageInfo(context, packageName, 0);

        return packageInfo != null ? packageInfo.versionCode : 0;
    }

    /**
     * 获取当前应用的版本名
     * @param context
     * @return
     */
    public static String getVersionName(@NonNull Context context) {
        return getVersionName(context, context.getPackageName());
    }

    /**
     * 获取当前应用的版本号
     * @param context
     * @return
     */
    public static int getVersionCode(@NonNull Context context) {
        return getVersionCode(context, context.getPackageName());
    }

    /**
     * 返回指定的包是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstall(@NonNull Context context, @NonNull String packageName) {
        return getPackageInfo(context, packageName, 0) != null;
    }

    /**
     * 获取简单的包信息
     * @param context
     * @param packageName
     * @return
     */
    public static SimplePackageInfo getSimplePackageInfo(@NonNull Context context, @NonNull String packageName) {

        PackageInfo packageInfo = getPackageInfo(context, packageName, 0);

        return packageInfo != null ? new SimplePackageInfo(
                packageName, packageInfo.versionName, packageInfo.versionCode) : null;
    }

    /**
     * 获取指定包信息
     * @param context
     * @param packageName
     * @param flag
     * @return
     */
    public static PackageInfo getPackageInfo(@NonNull Context context, @NonNull String packageName, int flag) {
        try {
            PackageManager manager = context.getPackageManager();
            return manager.getPackageInfo(packageName, flag);
        } catch (PackageManager.NameNotFoundException e) {
            Alog.e(TAG, "获取的包不存在", e);
        }
        return null;
    }

    public static class SimplePackageInfo {

        private String packageName;
        private String versionName;
        private int versionCode;

        SimplePackageInfo(String packageName, String versionName, int versionCode) {
            this.packageName = packageName;
            this.versionName = versionName;
            this.versionCode = versionCode;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }
    }
}
