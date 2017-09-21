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

package com.sky.android.news.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import butterknife.ButterKnife
import com.sky.android.common.base.BaseActivity

/**
 * Created by sky on 17-9-21.
 */
abstract class VBaseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置
        setContentView(getLayoutId())
        ButterKnife.bind(this)

        // 初始化
        initView(intent)
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView(intent: Intent)

    fun setSupportActionBar(toolbar: Toolbar, title: Int, homeAsUp: Boolean) {
        setSupportActionBar(toolbar, getString(title), homeAsUp)
    }

    fun setSupportActionBar(toolbar: Toolbar?, title: String, homeAsUp: Boolean) {

        if (toolbar == null) return

        setSupportActionBar(toolbar)

        // 设置ActivonBar
        val actionBar = supportActionBar

        actionBar!!.title = title
        actionBar.setDisplayHomeAsUpEnabled(homeAsUp)
    }
}