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

package com.sky.android.common.crash;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;

import com.sky.android.common.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by sky on 2020-11-28.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final int MAX_LENGTH = 1024 * 1024;

    private final Thread.UncaughtExceptionHandler mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    private final Context mContext;
    private final File mLogFile;
    private final CrashListener mCrashListener;

    public CrashHandler(@NonNull Context context) {
        this(context, new File(context.getFilesDir(), "crash.log"), null);
    }

    public CrashHandler(@NonNull Context context, @NonNull File logFile) {
        this(context, logFile, null);
    }

    public CrashHandler(@NonNull Context context, CrashListener crashListener) {
        this(context, new File(context.getFilesDir(), "crash.log"), crashListener);
    }

    public CrashHandler(@NonNull Context context, @NonNull File logFile, CrashListener crashListener) {
        mContext = context.getApplicationContext();
        mLogFile = logFile;
        mCrashListener = crashListener;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        // 保存错误信息到日志文件中
        writeLog(mLogFile, "CrashHandler", ex.getMessage(), ex);

        // 回调监听接口方法
        if (mCrashListener != null) {
            mCrashListener.afterSaveCrash(mLogFile);
        }

        // 回调默认的异常处理类
        mDefaultHandler.uncaughtException(thread, ex);
    }

    /**
     * 将日志写入文件。
     * @param tag
     * @param message
     * @param tr
     */
    public synchronized void writeLog(File logFile, String tag, String message, Throwable tr) {

        if (!checkLogFile(logFile)) {
            // 检查文件异常
            return ;
        }

        synchronized (CrashHandler.class) {

            FileWriter fileWriter = null;
            BufferedWriter bufdWriter = null;
            PrintWriter printWriter = null;

            try {
                fileWriter = new FileWriter(logFile, true);
                bufdWriter = new BufferedWriter(fileWriter);
                printWriter = new PrintWriter(fileWriter);

                // 添加头部信息
                bufdWriter.append(buildHead(mContext, tag, message));
                bufdWriter.flush();

                tr.printStackTrace(printWriter);
                printWriter.append("\n\n");
                printWriter.flush();
                fileWriter.flush();
            } catch (IOException e) {
                FileUtil.closeQuietly(fileWriter);
                FileUtil.closeQuietly(bufdWriter);
                FileUtil.closeQuietly(printWriter);
            }
        }
    }

    /**
     * 检查日志文件
     * @param file
     * @return
     */
    private boolean checkLogFile(@NonNull File file) {

        if (file.length() > MAX_LENGTH) {
            // 删除文件
            FileUtil.deleteFile(file);
        }
        return FileUtil.createFile(file);
    }

    /**
     * 生成手机设备信息
     * @param context
     * @param tag
     * @param message
     * @return
     */
    private String buildHead(Context context, String tag, String message) {

        final SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
        final String time = timeFormat.format(Calendar.getInstance().getTime());

        StringBuilder builder = new StringBuilder();

        builder.append("APPLICATION INFORMATION").append('\n');
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai = context.getApplicationInfo();
        builder.append("Application : ").append(pm.getApplicationLabel(ai)).append('\n');

        try {
            PackageInfo pi = pm.getPackageInfo(ai.packageName, 0);
            builder.append("Version Code: ").append(pi.versionCode).append('\n');
            builder.append("Version Name: ").append(pi.versionName).append('\n');
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        builder.append('\n').append("DEVICE INFORMATION").append('\n');
        builder.append("Board: ").append(Build.BOARD).append('\n');
        builder.append("BOOTLOADER: ").append(Build.BOOTLOADER).append('\n');
        builder.append("BRAND: ").append(Build.BRAND).append('\n');
        builder.append("CPU_ABI: ").append(Build.CPU_ABI).append('\n');
        builder.append("CPU_ABI2: ").append(Build.CPU_ABI2).append('\n');
        builder.append("DEVICE: ").append(Build.DEVICE).append('\n');
        builder.append("DISPLAY: ").append(Build.DISPLAY).append('\n');
        builder.append("FINGERPRINT: ").append(Build.FINGERPRINT).append('\n');
        builder.append("HARDWARE: ").append(Build.HARDWARE).append('\n');
        builder.append("HOST: ").append(Build.HOST).append('\n');
        builder.append("ID: ").append(Build.ID).append('\n');
        builder.append("MANUFACTURER: ").append(Build.MANUFACTURER).append('\n');
        builder.append("PRODUCT: ").append(Build.PRODUCT).append('\n');
        builder.append("TAGS: ").append(Build.TAGS).append('\n');
        builder.append("TYPE: ").append(Build.TYPE).append('\n');
        builder.append("USER: ").append(Build.USER).append('\n');

        builder.append('\n').append("EXCEPTION INFORMATION").append('\n');
        builder.append(time).append(" E/").append(tag).append(" ")
                .append(message).append('\n');

        return builder.toString();
    }
}
