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

package com.sky.android.news.ui.main.news

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.hi.dhl.binding.viewbind
import com.iflytek.cloud.*
import com.sky.android.common.util.ViewUtil
import com.sky.android.news.R
import com.sky.android.news.contract.DetailsContract
import com.sky.android.news.data.model.ContentModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.source.NewsDataRepository
import com.sky.android.news.data.source.NewsSourceFactory
import com.sky.android.news.databinding.FragmentDetailsBinding
import com.sky.android.news.presenter.DetailsPresenter
import com.sky.android.news.ui.base.NewsFragment
import com.sky.android.news.ui.helper.VImageGetter
import com.sky.android.news.util.ActivityUtil


/**
 * Created by sky on 17-9-23.
 */
class DetailsFragment : NewsFragment(), DetailsContract.View, InitListener, SynthesizerListener {

    private val binding: FragmentDetailsBinding by viewbind()

    // 语音合成对象
	private var mTts: SpeechSynthesizer? = null
    private lateinit var mContent: ContentModel
    private lateinit var mDetailsPresenter: DetailsContract.Presenter

    override val layoutId: Int
        get() = R.layout.fragment_details

    override fun initView(view: View, args: Bundle?) {

        setHasOptionsMenu(true)

//        webView.settings.defaultTextEncodingName = "UTF -8"

        val repository = NewsDataRepository(NewsSourceFactory(context))
        mDetailsPresenter = DetailsPresenter(context, repository, this)

        mDetailsPresenter.setCategoryItem(args!!.getSerializable("item") as LineItemModel)
        mDetailsPresenter.loadDetails()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (mTts != null) {
            mTts!!.stopSpeaking()
            mTts!!.destroy()
            mTts = null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.activity_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_broadcast -> {
                // 开始播报
                startBroadcast()
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

    /**
     * 分享这个新闻连接,使用系统分享功能
     */
    private fun shareNews() {

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, "link")
            putExtra(Intent.EXTRA_TEXT, mContent.shareLink)
        }

        ActivityUtil.startActivity(
                requireContext(), Intent.createChooser(intent, "分享到"))
    }

    @SuppressLint("SetTextI18n")
    override fun onLoadDetails(model: DetailsModel) {

        mContent = model.models

        // 设置标题
        binding.tvTitle.text = mContent.title
        binding.tvSummary.text = "${mContent.source}  ${mContent.pTime}"
        binding.tvBody.text = Html.fromHtml(mContent.body, VImageGetter(requireContext(), binding.tvBody), null)

        // 使用WebView来处理
//        webView.loadData(mContent.body, "text/html; charset=UTF-8", null)
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }

    override fun showLoading() {
        ViewUtil.setVisibility(binding.tvLoading, View.VISIBLE)
    }

    override fun cancelLoading() {
        ViewUtil.setVisibility(binding.tvLoading, View.INVISIBLE)
    }

    override fun onInit(code: Int) {
        if (code != ErrorCode.SUCCESS)
            showMessage("初始化失败,错误码：$code")
    }

    override fun onSpeakBegin() {
        showMessage("开始播放")
    }

    override fun onSpeakPaused() {
    }

    override fun onSpeakResumed() {
    }

    override fun onBufferProgress(percent: Int, beginPos: Int, endPos: Int, info: String?) {
        // 合成进度
    }

    override fun onSpeakProgress(percent: Int, beginPos: Int, endPos: Int) {
        // 播放进度
    }

    override fun onCompleted(error: SpeechError?) {
        if (error == null) {
            showMessage("播放完成")
        } else {
            showMessage(error.getPlainDescription(true))
        }
    }

    override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle?) {
    }

    private fun startBroadcast() {

        if (TextUtils.isEmpty(binding.tvBody.text)
                || (mTts != null && mTts!!.isSpeaking)) {
            return
        }

        if (mTts == null) {
            // 初始化合成对象
            mTts = SpeechSynthesizer.createSynthesizer(context, this)
        }

        val code = mTts!!.startSpeaking(binding.tvBody.text.toString(), this)

        if (code != ErrorCode.SUCCESS) {
            showMessage("语音合成失败,错误码: $code")
        }
    }
}