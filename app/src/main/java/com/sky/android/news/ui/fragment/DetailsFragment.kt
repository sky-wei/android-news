package com.sky.android.news.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import butterknife.BindView
import com.iflytek.cloud.*
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.ui.base.VBaseFragment
import com.sky.android.news.contract.DetailsContract
import com.sky.android.news.data.model.ContentModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.source.NewsDataRepository
import com.sky.android.news.data.source.NewsSourceFactory
import com.sky.android.news.presenter.DetailsPresenter
import com.sky.android.news.ui.helper.VImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView


/**
 * Created by sky on 17-9-23.
 */
class DetailsFragment : VBaseFragment(), DetailsContract.View, InitListener, SynthesizerListener {

    @BindView(R2.id.tv_title)
    lateinit var tvTitle: TextView
    @BindView(R2.id.tv_body)
    lateinit var tvBody: HtmlTextView

    	// 语音合成对象
	private var mTts: SpeechSynthesizer? = null
    private lateinit var mContent: ContentModel
    private lateinit var mDetailsPresenter: DetailsContract.Presenter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun initView(view: View, args: Bundle?) {

        setHasOptionsMenu(true)

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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLoadDetails(model: DetailsModel) {

        mContent = model.models

        // 设置标题
        tvTitle.text = mContent.title
        tvBody.setHtml(mContent.body, VImageGetter(context, tvBody))
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }

    override fun showLoading() {
    }

    override fun cancelLoading() {
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

        if (TextUtils.isEmpty(tvBody.text)
                || (mTts != null && mTts!!.isSpeaking)) {
            return
        }

        if (mTts == null) {
            // 初始化合成对象
            mTts = SpeechSynthesizer.createSynthesizer(context, this)
        }

        val code = mTts!!.startSpeaking(tvBody.text.toString(), this)

        if (code !== ErrorCode.SUCCESS) {
            showMessage("语音合成失败,错误码: $code")
        }
    }
}