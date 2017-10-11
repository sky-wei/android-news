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
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.contract.StoryDetailsContract
import com.sky.android.news.data.model.BaseItemModel
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.source.StoryDataRepository
import com.sky.android.news.data.source.StorySourceFactory
import com.sky.android.news.presenter.StoryDetailsPresenter
import com.sky.android.news.ui.base.VBaseFragment
import com.sky.android.news.util.ActivityUtil

/**
 * Created by sky on 17-9-28.
 */
class StoryDetailsFragment : VBaseFragment(), StoryDetailsContract.View {

    @BindView(R2.id.iv_image)
    lateinit var ivImage: ImageView
    @BindView(R2.id.tv_title)
    lateinit var tvTitle: TextView
    @BindView(R2.id.web_view)
    lateinit var webView: WebView

    private lateinit var mModel: StoryDetailsModel
    private lateinit var mStoryDetailsPresenter: StoryDetailsContract.Presenter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_story_details, container, false)
    }

    override fun initView(view: View, args: Bundle?) {

        setHasOptionsMenu(true)

        webView.settings.defaultTextEncodingName = "UTF -8"

        val item = args!!.getSerializable("item") as BaseItemModel

        val repository = StoryDataRepository(StorySourceFactory(context))
        mStoryDetailsPresenter = StoryDetailsPresenter(repository, this)

        // 加载详情
        mStoryDetailsPresenter.loadDetails(item.id)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.activity_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_broadcast -> {
                // 开始播报
                showMessage("暂不支持")
                return true
            }
            R.id.menu_share -> {
                // 分享
                shareNews()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLoadDetails(model: StoryDetailsModel) {

        mModel = model

        Glide.with(context)
                .load(model.image)
                .into(ivImage)
        tvTitle.text = model.title

        // 使用WebView来处理
        webView.loadDataWithBaseURL("file:///android_asset/",
                stitching(model.body), "text/html", "UTF-8", null)
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }

    /**
     * 分享这个新闻连接,使用系统分享功能
     */
    private fun shareNews() {

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, "link")
            putExtra(Intent.EXTRA_TEXT, mModel.shareUrl)
        }

        ActivityUtil.startActivity(
                context, Intent.createChooser(intent, "分享到"))
    }

    private fun stitching(body: String): String {
        return "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\" />\n" +
                "<meta name=\"viewport\" content=\"width=device-width,user-scalable=no\" />\n" +
                "<link href=\"news_qa.min.css\" rel=\"stylesheet\" />\n" +
                "<script src=\"zepto.min.js\"></script>\n" +
                "<script src=\"img_replace.js\"></script>\n" +
                "<script src=\"video.js\"></script>\n" +
                "</head>\n" +
                "<body classname=\"\" onload=\"onLoaded()\">\n" +
                "$body" +
                "<script src=\"show_bottom_link.js\"></script>\n" +
                "<script>show('{\\\"theme_subscribed\\\":false}');</script>\n" +
                "</body>\n" +
                "</html>"
    }
}