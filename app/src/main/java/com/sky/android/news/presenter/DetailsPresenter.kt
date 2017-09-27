package com.sky.android.news.presenter

import android.content.Context
import com.sky.android.common.utils.Alog
import com.sky.android.news.base.BaseSubscriber
import com.sky.android.news.contract.DetailsContract
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.source.NewsDataSource
import com.sky.android.news.ui.helper.DetailsHelper

/**
 * Created by sky on 17-9-23.
 */
class DetailsPresenter(val context: Context,
                       val source: NewsDataSource,
                       val view: DetailsContract.View) : AbstractPresenter(), DetailsContract.Presenter {

    private lateinit var mItem: LineItemModel
    private val mDetailsHelper = DetailsHelper(context)

    override fun setCategoryItem(item: LineItemModel) {
        mItem = item
    }

    override fun loadDetails() {

        view.showLoading()

        val observable = source.getDetails(mItem.docId).doOnNext {

            val content = it.models

            // 转换结果
            content.body = mDetailsHelper.replaceImage(content.body, content.img)
            content.body = mDetailsHelper.replaceVideo(content.body, content.video)
        }

        ioToMain(observable)
                .subscribe(DetailsSubscriber())
    }

    private inner class DetailsSubscriber : BaseSubscriber<DetailsModel>() {

        override fun onError(msg: String, tr: Throwable): Boolean {
            // 加载失败
            Alog.e(msg, tr)
            view.cancelLoading()
            view.onLoadFailed("加载详情内容失败")
            return true
        }

        override fun onNext(model: DetailsModel?) {

            if (model == null) {
                // 返回数据为空
                view.onLoadFailed("服务返回数据为空")
                return
            }

            // 加载成功
            view.cancelLoading()
            view.onLoadDetails(model)
        }
    }
}