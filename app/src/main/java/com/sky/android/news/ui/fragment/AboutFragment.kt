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

package com.sky.android.news.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.sky.android.news.BuildConfig
import com.sky.android.news.R
import com.sky.android.news.ui.base.NewsFragment
import com.sky.android.news.util.ActivityUtil
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * Created by sky on 17-9-21.
 */
class AboutFragment : NewsFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_about

    override fun initView(view: View, args: Bundle?) {

        // 设置版本名称
        tv_version.text = getString(
                R.string.version_x, BuildConfig.VERSION_NAME)

        tv_source.setOnClickListener {

            val uri = Uri.parse("https://github.com/jingcai-wei/android-news")
            val intent = Intent(Intent.ACTION_VIEW, uri)

            ActivityUtil.startActivity(requireContext(), intent)
        }
    }
}