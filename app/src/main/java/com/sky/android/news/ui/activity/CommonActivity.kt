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
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import butterknife.BindView
import com.sky.android.news.R
import com.sky.android.news.base.VBaseActivity

/**
 * Created by sky on 17-9-21.
 */
class CommonActivity : VBaseActivity() {

    companion object {
        val TITLE = "title"
        val F_NAME = "fName"
        val SUPPORT_FRAGMENT = "supportFragment"
    }

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun getLayoutId(): Int {
        return R.layout.app_bar_frame
    }

    override fun initView(intent: Intent) {

        val title = intent.getIntExtra(TITLE, R.string.app_name)
        val fName = intent.getStringExtra(F_NAME)
        val mSupportFragment = intent.getBooleanExtra(SUPPORT_FRAGMENT, true)

        // 设置ActionBar
        setSupportActionBar(toolbar, title, true)

        if (mSupportFragment) {
            // SupportFragment
            val fragmentManager = supportFragmentManager
            val fragment = Fragment.instantiate(this, fName)
            fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
            return
        }

        // Fragment
        val fragmentManager = fragmentManager
        val fragment = android.app.Fragment.instantiate(this, fName)
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}