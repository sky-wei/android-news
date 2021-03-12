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

package com.sky.android.news.ui.splash

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.activity.viewModels
import com.sky.android.news.R
import com.sky.android.news.ui.base.NewsActivity
import com.sky.android.news.ui.main.MainActivity
import com.sky.android.news.util.ActivityUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by sky on 17-9-21.
 */
@AndroidEntryPoint
class SplashActivity : NewsActivity() {

    private val mViewModel by viewModels<SplashViewModel>()

    override val layoutId: Int
        get() = R.layout.activity_start

    @SuppressLint("CheckResult")
    override fun initView(intent: Intent) {

        mViewModel.requestPermission(this)

        mViewModel.run {

            granted.observe(this@SplashActivity) {
                enterMainActivity()
            }

            denied.observe(this@SplashActivity) {
                showPermissionNotGranted()
            }
        }
    }

    private fun enterMainActivity() {

        // 直接进入主界面
        ActivityUtil.startActivity(
                context, MainActivity::class.java)
        finish()
    }

    private fun showPermissionNotGranted() {

        val dialog = AlertDialog.Builder(context)
                .setTitle("提示")
                .setCancelable(false)
                .setMessage("权限获取失败，无法启动程序")
                .setPositiveButton("确定") { _, _ -> finish() }
                .create()
        dialog.show()
    }
}