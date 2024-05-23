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
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Created by sky on 2020-11-27.
 */
public class Alog {

    @SuppressLint("StaticFieldLeak")
    private static volatile Alog singleton = null;

    private final String mPrefix;
    private final String mTag;
    private final boolean mDebug;
    private final Adapter mAdapter;

    private Alog(@NonNull Builder build) {
        mPrefix = build.mPrefix;
        mTag = build.mTag;
        mDebug = build.mDebug;
        mAdapter = build.mAdapter;
    }

    public Adapter getAdapter() {
        return mAdapter;
    }

    /**
     * 输出日志
     * @param priority
     * @param msg
     */
    public void println(int priority, String msg) {
        if (mDebug) mAdapter.println(priority, mPrefix + mTag, msg);
    }

    /**
     * 输出日志
     * @param priority
     * @param tag
     * @param msg
     */
    public void println(int priority, String tag, String msg) {
        if (mDebug) mAdapter.println(priority, mPrefix + tag, msg);
    }

    /**
     * 输出日志
     * @param priority
     * @param msg
     * @param tr
     */
    public void println(int priority, String msg, Throwable tr) {
        if (mDebug) mAdapter.println(priority, mPrefix + mTag, msg, tr);
    }

    /**
     * 输出日志
     * @param priority
     * @param tag
     * @param msg
     * @param tr
     */
    public void println(int priority, String tag, String msg, Throwable tr) {
        if (mDebug) mAdapter.println(priority, mPrefix + tag, msg, tr);
    }


    /**
     * 获取实例类
     * @return
     */
    public static @NonNull Alog getInstance() {
        if (singleton == null) {
            synchronized (Alog.class) {
                if (singleton == null) {
                    singleton = new Builder().build();
                }
            }
        }
        return singleton;
    }

    /**
     * 设置实例类
     * @param alog
     */
    public static void setSingletonInstance(@NonNull Alog alog) {
        synchronized (Alog.class) {
            if (singleton != null) {
                throw new IllegalStateException("Singleton instance already exists.");
            }
            singleton = alog;
        }
    }

    public static String getPrefix() {
        return getInstance().mPrefix;
    }

    public static String getTag() {
        return getInstance().mTag;
    }

    public static boolean isDebug() {
        return getInstance().mDebug;
    }

    public static void i(String msg) {
        getInstance().println(Log.INFO, msg);
    }

    public static void i(String tag, String msg) {
        getInstance().println(Log.INFO, tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        getInstance().println(Log.INFO, tag, msg, tr);
    }

    public static void d(String msg) {
        getInstance().println(Log.DEBUG, msg);
    }

    public static void d(String tag, String msg) {
        getInstance().println(Log.DEBUG, tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        getInstance().println(Log.DEBUG, tag, msg, tr);
    }

    public static void e(String msg) {
        getInstance().println(Log.ERROR, msg);
    }

    public static void e(String msg, Throwable tr) {
        getInstance().println(Log.ERROR, msg, tr);
    }

    public static void e(String tag, String msg) {
        getInstance().println(Log.ERROR, tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        getInstance().println(Log.ERROR, tag, msg, tr);
    }

    public static void v(String msg) {
        getInstance().println(Log.VERBOSE, msg);
    }

    public static void v(String tag, String msg) {
        getInstance().println(Log.VERBOSE, tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        getInstance().println(Log.VERBOSE, tag, msg, tr);
    }

    public static void w(String msg) {
        getInstance().println(Log.WARN, msg);
    }

    public static void w(String tag, String msg) {
        getInstance().println(Log.WARN, tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        getInstance().println(Log.WARN, tag, msg, tr);
    }


    public static class Builder {

        private String mPrefix = "ALog.";
        private String mTag = "Main";
        private boolean mDebug = false;
        private Adapter mAdapter;

        public Builder setPrefix(String prefix) {
            mPrefix = prefix;
            return this;
        }

        public Builder setTag(String tag) {
            mTag = tag;
            return this;
        }

        public Builder setDebug(boolean debug) {
            mDebug = debug;
            return this;
        }

        public Builder setAdapter(Adapter adapter) {
            mAdapter = adapter;
            return this;
        }

        public Alog build() {
            if (mAdapter == null) {
                mAdapter = new InternalAdapter();
            }
            return new Alog(this);
        }
    }

    /**
     * 日志适配类
     */
    private static final class InternalAdapter implements Adapter {

        @Override
        public void println(int priority, String tag, String msg) {
            Log.println(priority, tag, msg);
        }

        @Override
        public void println(int priority, String tag, String msg, Throwable tr) {
            Log.println(priority, tag, msg + '\n' + Log.getStackTraceString(tr));
        }
    }


    /**
     * 日志适配器
     */
    public interface Adapter {

        /**
         * 输出日志
         * @param priority
         * @param tag
         * @param msg
         */
        void println(int priority, String tag, String msg);

        /**
         * 输出日志
         * @param priority
         * @param tag
         * @param msg
         * @param tr
         */
        void println(int priority, String tag, String msg, Throwable tr);
    }
}
