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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.contract.StoryDetailsContract
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.model.StoryItemModel
import com.sky.android.news.data.source.ZhiHuDataRepository
import com.sky.android.news.data.source.ZhiHuSourceFactory
import com.sky.android.news.presenter.StoryDetailsPresenter
import com.sky.android.news.ui.base.VBaseFragment

/**
 * Created by sky on 17-9-28.
 */
class StoryDetailsFragment : VBaseFragment(), StoryDetailsContract.View {

    @BindView(R2.id.iv_image)
    lateinit var ivImage: ImageView
    @BindView(R2.id.web_view)
    lateinit var webView: WebView

    lateinit var mStoryDetailsPresenter: StoryDetailsContract.Presenter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_story_details, container, false)
    }

    override fun initView(view: View, args: Bundle?) {

        webView.settings.defaultTextEncodingName = "UTF -8"

        val item = args!!.getSerializable("item") as StoryItemModel

        val repository = ZhiHuDataRepository(ZhiHuSourceFactory(context))
        mStoryDetailsPresenter = StoryDetailsPresenter(repository, this)

        // 加载详情
        mStoryDetailsPresenter.loadDetails(item.id)
    }

    override fun onLoadDetails(model: StoryDetailsModel) {

        Glide.with(context)
                .load(model.image)
                .into(ivImage)

        // 使用WebView来处理
        webView.loadData(model.body, "text/html; charset=UTF-8", null)
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }
}