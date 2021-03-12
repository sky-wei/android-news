/*
 * Copyright (c) 2017 The sky Authors.
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

package com.sky.android.news

import androidx.multidex.MultiDexApplication
import com.iflytek.cloud.SpeechUtility
import com.sky.android.common.util.Alog
import com.sky.android.common.util.ToastUtil
import com.sky.android.news.data.cache.CacheManager
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by sky on 17-9-21.
 */
@HiltAndroidApp
class App : MultiDexApplication() {

    override fun onCreate() {
        SpeechUtility.createUtility(this, "appid=59c8c734")
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Alog.setSingletonInstance(
                    Alog.Builder()
                            .setDebug(true)
                            .build()
            )
        }
        ToastUtil.initialize(this)

//        Setting.setShowLog(BuildConfig.DEBUG)

        // 初始化...
        CacheManager.getInstance(this)
    }

    override fun onTerminate() {
        super.onTerminate()

        // 关闭
        CacheManager.getInstance(this).close()
    }
}