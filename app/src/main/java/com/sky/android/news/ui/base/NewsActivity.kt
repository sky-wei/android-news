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

package com.sky.android.news.ui.base

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.sky.android.common.util.ToastUtil
import com.sky.android.core.activity.BaseActivity
import com.sky.android.news.R
import com.sky.android.news.ui.diglog.LoadingDialog

/**
 * Created by sky on 17-9-21.
 */
abstract class NewsActivity : BaseActivity() {

    private var mLoadingDialog: LoadingDialog? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    open fun setSupportActionBar(toolbar: Toolbar, title: Int, homeAsUp: Boolean) {
        setSupportActionBar(toolbar, getString(title), homeAsUp)
    }

    open fun setSupportActionBar(toolbar: Toolbar?, title: String, homeAsUp: Boolean) {

        if (toolbar == null) return

        setSupportActionBar(toolbar)

        // 设置ActivonBar
        val actionBar = supportActionBar

        actionBar!!.title = title
        actionBar.setDisplayHomeAsUpEnabled(homeAsUp)
    }

    override fun showLoading() {

        if (mLoadingDialog != null) return

        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog!!.setTipText(R.string.loading)
        mLoadingDialog!!.setCancelable(false)
        mLoadingDialog!!.show()
    }

    override fun cancelLoading() {

        if (mLoadingDialog == null) return

        // 隐藏
        mLoadingDialog!!.dismiss()
        mLoadingDialog = null
    }

    override fun showMessage(msg: String) {
        ToastUtil.show(msg)
    }
}