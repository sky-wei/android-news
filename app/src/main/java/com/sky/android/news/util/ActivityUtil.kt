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
import android.text.TextUtils
import com.sky.android.common.utils.Alog
import com.sky.android.news.ui.activity.CommonActivity

/**
 * Created by sky on 17-9-21.
 */
object ActivityUtil {


    fun startCommonActivity(context: Context, title: Int, fName: String): Boolean {
        return startCommonActivity(context, title, fName, true)
    }

    fun startCommonActivity(context: Context, title: Int, fName: String, supportFragment: Boolean): Boolean {

        val intent = Intent(context, CommonActivity::class.java).apply {
            putExtra(CommonActivity.TITLE, title)
            putExtra(CommonActivity.F_NAME, fName)
            putExtra(CommonActivity.SUPPORT_FRAGMENT, supportFragment)
        }

        return startActivity(context, intent)
    }

    fun startActivity(context: Context, tClass: Class<*>): Boolean {
        return startActivity(context, Intent(context, tClass))
    }

    fun startActivity(context: Context, intent: Intent): Boolean {

        try {
            // 获取目标包名
            val packageName = intent.`package`

            // 设置启动参数
            if (!TextUtils.isEmpty(packageName)
                    && !TextUtils.equals(packageName, context.packageName)) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            // 启动Activity
            context.startActivity(intent)
            return true
        } catch (e: Exception) {
            Alog.e("启动Activity异常", e)
        }
        return false
    }

    fun startActivityForResult(activity: Activity, intent: Intent, requestCode: Int): Boolean {

        try {
            // 添加参数
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            // 启动Activity
            activity.startActivityForResult(intent, requestCode)
            return true
        } catch (e: Exception) {
            Alog.e("启动Activity异常", e)
        }

        return false
    }
}