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

package com.sky.android.news.ui.diglog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.sky.android.news.R
import com.sky.android.news.R2

/**
 * Created by sky on 17-9-24.
 */
class LoadingDialog @JvmOverloads constructor(context: Context, private val mCancelCallback: CancelCallback? = null) : Dialog(context, R.style.CustomProgressDialog) {

    @BindView(R2.id.tv_tip)
    lateinit var tvTip: TextView

    private var cancelable = true

    init {
        setContentView(R.layout.dialog_loading)
        window!!.setGravity(Gravity.CENTER)

        ButterKnife.bind(this)
    }

    override fun setCancelable(flag: Boolean) {
        super.setCancelable(flag)
        cancelable = flag
    }

    fun setTipText(text: Int) {
        tvTip.setText(text)
    }

    fun setTipText(text: String) {
        tvTip.text = text
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (KeyEvent.KEYCODE_BACK == keyCode && cancelable) {

            // 关闭
            dismiss()
            mCancelCallback?.cancel()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }


    interface CancelCallback {

        fun cancel()
    }
}