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

import android.content.Intent
import com.sky.android.news.R
import com.sky.android.news.base.VBaseActivity
import com.sky.android.news.util.ActivityUtil

/**
 * Created by sky on 17-9-21.
 */
class StartActivity : VBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_start
    }

    override fun initView(intent: Intent) {

        // 直接进入主界面
        ActivityUtil.startActivity(
                context, MainActivity::class.java)
        finish()
    }
}