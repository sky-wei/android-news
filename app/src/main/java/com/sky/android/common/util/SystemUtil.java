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

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by sky on 2020-11-28.
 */
public class SystemUtil {

    private static final String TAG = "SystemUtil";

    private static final String DEFAULT = "su";

    /**
     * Kill当前进程
     */
    public static void killMyProcess() {
        killProcess(android.os.Process.myPid());
    }

    /**
     * Kill指定的进程
     * @param pid
     */
    public static void killProcess(int pid){
        android.os.Process.killProcess(pid);
    }

    /**
     * Kill当前App
     */
    public static void killMyApp() {
        // 关闭
        SystemUtil.killMyProcess();
        System.exit(0);
    }

    /**
     * 获取指定名称的系统属性信息
     * @param key
     * @return
     */
    public static String getSystemProp(String key) {
        return getSystemProp(key, "");
    }

    public static String getSystemProp(String key, String defaultValue) {
        return (String) ReflectUtils.invokeQuietly(
                ReflectUtils.classForName("android.os.SystemProperties"), "get",
                new Class[]{String.class, String.class}, new Object[]{key, defaultValue});
    }

    /**
     * 判断SDCard是否挂载
     * @return
     */
    public static boolean isSDCardMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取进程名称获取进程id
     * @param context
     * @param processName
     * @return
     */
    public static int getProcessPid(Context context, String processName) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> listOfProcesses = manager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo process : listOfProcesses) {
            if (process.processName.startsWith(processName)) {
                return process.pid;
            }
        }
        return -1;
    }

    /**
     * 使用am命令强行停止相应的包
     * @param packageName
     */
    public static void forceStop(String packageName) {

        if (!TextUtils.isEmpty(packageName)) {
            // 关闭应用
            exec("am force-stop " + packageName);
        }
    }

    /**
     * 获取当前设备id
     * @param context
     * @return
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getDeviceId(@NonNull Context context) {

        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return telephonyManager.getDeviceId();
    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param context 上下文
     * @param pid 进程的id
     * @return 返回进程的名字
     */
    public static String getProcessName(Context context, int pid) {

        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager == null) return null;

        List<ActivityManager.RunningAppProcessInfo> process = activityManager.getRunningAppProcesses();

        if (process != null) {

            for (ActivityManager.RunningAppProcessInfo info : process) {

                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    return info.processName;
                }
            }
        }
        return null;
    }

    /**
     * 移除当前Context的任务
     * @param context
     * @return
     */
    public static boolean removeTask(Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }

        ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.AppTask> appTasks =  am.getAppTasks();

        if (appTasks == null || appTasks.isEmpty()) return false;

        for (ActivityManager.AppTask task : appTasks) {
            // 移除任务
            task.finishAndRemoveTask();
        }
        return true;
    }

    /**
     * 执行Root权限的命令
     * @param cmd
     */
    public static Execute.Result execRoot(String cmd, String... commands) {

        if (TextUtils.isEmpty(cmd)) {
            throw new NullPointerException("命令不能为空!");
        }

        if (commands == null || commands.length <= 0) {
            // 单行命令
            return exec(getRootPath() + " -c " + cmd);
        }

        // 多条需要在命令中添加退出
        String[] newCommands = new String[commands.length + 2];
        newCommands[0] = cmd;
        System.arraycopy(commands, 0, newCommands, 1, commands.length);
        newCommands[newCommands.length - 1] = "exit";

        return exec(getRootPath(), newCommands);
    }

    /**
     * 执行指定的命令
     * @param cmd
     */
    public static Execute.Result exec(String cmd, String... commands) {
        return new Execute().exec(cmd, commands);
    }

    /**
     * 获取Root路径
     * @return
     */
    public static String getRootPath() {
        return DEFAULT;
    }
}
