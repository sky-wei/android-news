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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.ButterKnife
import com.sky.android.common.base.BaseFragment
import com.sky.android.news.R
import com.sky.android.news.ui.diglog.LoadingDialog

/**
 * Created by sky on 17-9-21.
 */
abstract class VBaseFragment : BaseFragment(), BaseView {

    private var mLoadingDialog: LoadingDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = createView(inflater, container)
        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化View
        initView(view, arguments)
    }

    protected abstract fun createView(inflater: LayoutInflater, container: ViewGroup?): View

    protected abstract fun initView(view: View, args: Bundle?)

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
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}