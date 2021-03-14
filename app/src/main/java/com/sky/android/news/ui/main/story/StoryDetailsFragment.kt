/*
 * Copyright (c) 2020 The sky Authors.
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

package com.sky.android.news.ui.main.story

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.hi.dhl.binding.viewbind
import com.sky.android.news.R
import com.sky.android.news.data.model.BaseItemModel
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.databinding.FragmentStoryDetailsBinding
import com.sky.android.news.ui.base.NewsFragment
import com.sky.android.news.util.ActivityUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by sky on 17-9-28.
 */
@AndroidEntryPoint
class StoryDetailsFragment : NewsFragment() {

    private val binding: FragmentStoryDetailsBinding by viewbind()
    private val viewModel by viewModels<StoryDetailsViewModel>()

    private lateinit var mModel: StoryDetailsModel

    override val layoutId: Int
        get() = R.layout.fragment_story_details

    override fun initView(view: View, args: Bundle?) {

        setHasOptionsMenu(true)

        binding.webView.settings.defaultTextEncodingName = "UTF -8"

        val item = args!!.getSerializable("item") as BaseItemModel

        viewModel.apply {
            failure.observe(this@StoryDetailsFragment) {
                showMessage(it)
            }
            details.observe(this@StoryDetailsFragment) {
                onLoadDetails(it)
            }
        }

        // 加载详情
        viewModel.loadDetails(item.id)
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

    private fun onLoadDetails(model: StoryDetailsModel) {

        mModel = model

        Glide.with(context)
                .load(model.image)
                .into(binding.ivImage)
        binding.tvTitle.text = model.title

        // 使用WebView来处理
        binding.webView.loadDataWithBaseURL("file:///android_asset/",
                stitching(model.body), "text/html", "UTF-8", null)
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
                body +
                "<script src=\"show_bottom_link.js\"></script>\n" +
                "<script>show('{\\\"theme_subscribed\\\":false}');</script>\n" +
                "</body>\n" +
                "</html>"
    }
}