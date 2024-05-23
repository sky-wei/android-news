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
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * Created by sky on 2020-11-27.
 */
public class ToastUtil {

    @SuppressLint("StaticFieldLeak")
    private static volatile ToastUtil singleton = null;

    private final Context mContext;
    private final Factory mFactory;

    private ToastUtil(Builder builder) {
        mContext = builder.mContext;
        mFactory = builder.mFactory;
    }

    public void showMessage(CharSequence text) {
        Toast toast = mFactory.create(mContext);
        toast.setText(text);
        toast.show();
    }

    public void showMessage(int resId) {
        showMessage(mContext.getString(resId));
    }

    public void showMessage(CharSequence text, int duration) {
        Toast toast = mFactory.create(mContext);
        toast.setDuration(duration);
        toast.setText(text);
        toast.show();
    }

    public void showMessage(int resId, int duration) {
        showMessage(mContext.getString(resId), duration);
    }

    /**
     * 初始化
     * @param context
     */
    public static void initialize(@NonNull Context context) {
        initialize(new Builder(context));
    }

    /**
     * 初始化
     * @param builder
     */
    public static void initialize(@NonNull Builder builder) {
        if (singleton == null) {
            synchronized (ToastUtil.class) {
                if (singleton == null) {
                    singleton = builder.build();
                }
            }
        }
    }

    public static void show(CharSequence text) {
        singleton.showMessage(text);
    }

    public static void show(CharSequence text, int duration) {
        singleton.showMessage(text, duration);
    }

    public static void show(int resId) {
        singleton.showMessage(resId);
    }

    public static void show(int resId, int duration) {
        singleton.showMessage(resId, duration);
    }


    public static final class Builder {

        private final Context mContext;
        private Factory mFactory;

        public Builder(Context context) {
            mContext = context.getApplicationContext();
        }

        public Builder setFactory(Factory factory) {
            mFactory = factory;
            return this;
        }

        public ToastUtil build() {

            if (mFactory == null) {
                mFactory = new InternalFactory();
            }
            return new ToastUtil(this);
        }
    }


    /**
     * 内置工厂类
     */
    private static final class InternalFactory implements Factory {

        @Override
        public Toast create(Context context) {
            return Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
    }


    /**
     * 工厂接口
     */
    public interface Factory {

        Toast create(Context context);
    }
}
