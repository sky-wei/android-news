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
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import butterknife.BindView
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.ui.base.VBaseActivity
import com.sky.android.news.ui.fragment.DetailsFragment

/**
 * Created by sky on 17-9-23.
 */
class DetailsActivity : VBaseActivity() {

    companion object {
        val F_NAME = "fName"
    }

    @BindView(R2.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun getLayoutId(): Int {
        return R.layout.app_bar_frame
    }

    override fun initView(intent: Intent) {

        // 设置ActionBar
        setSupportActionBar(toolbar, R.string.app_name, true)

        val fName = intent.getStringExtra(CommonActivity.F_NAME)

        val args = Bundle().apply {
            putSerializable("item", intent.getSerializableExtra("item"))
        }

        val fragmentManager = supportFragmentManager
        val fragment = Fragment.instantiate(this, fName, args)
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}