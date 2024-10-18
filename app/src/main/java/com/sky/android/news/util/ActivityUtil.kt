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

package com.sky.android.news.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.sky.android.common.util.Alog

/**
 * Created by sky on 17-9-21.
 */
object ActivityUtil {

    fun startActivity(context: Context, tClass: Class<*>): Boolean =
        startActivity(context, Intent(context, tClass))

    fun startActivity(
        context: Context, intent: Intent
    ): Boolean {
        return try {
            // 获取目标包名
            val packageName = intent.`package`

            // 设置启动参数
            if (!packageName.isNullOrEmpty()
                    && packageName != context.packageName) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            // 启动Activity
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            Alog.e("启动Activity异常", e)
            false
        }
    }

    fun startActivityForResult(
        activity: Activity, intent: Intent, requestCode: Int
    ): Boolean {
        return try {
            // 启动Activity
            activity.startActivityForResult(intent, requestCode)
            true
        } catch (e: Exception) {
            Alog.e("启动Activity异常", e)
            false
        }
    }
}