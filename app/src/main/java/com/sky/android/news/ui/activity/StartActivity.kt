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

package com.sky.android.news.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import com.sky.android.news.R
import com.sky.android.news.ui.base.VBaseActivity
import com.sky.android.news.util.ActivityUtil
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * Created by sky on 17-9-21.
 */
class StartActivity : VBaseActivity() {

    private lateinit var rxPermissions: RxPermissions

    override fun getLayoutId(): Int = R.layout.activity_start

    @SuppressLint("CheckResult")
    override fun initView(intent: Intent) {

        rxPermissions = RxPermissions(this)
        rxPermissions
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                )
                .subscribe { granted ->
                    if (granted!!) {
                        enterMainActivity()
                    } else {
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