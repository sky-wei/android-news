package com.sky.android.news.presenter

import com.sky.android.common.utils.Alog
import com.sky.android.news.contract.DetailsContract
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.source.NewsDataSource

/**
 * Created by sky on 17-9-23.
 */
class DetailsPresenter(val source: NewsDataSource,
                       val view: DetailsContract.View) : AbstractPresenter(), DetailsContract.Presenter {

    private val TAG = "HeadLinePresenter"

    private lateinit var mItem: LineItemModel

    override fun setCategoryItem(item: LineItemModel) {
        mItem = item
    }

    override fun loadDetails() {

        ioToMain(source.getDetails(mItem.docId))
                .subscribe(
                        {
                            // 加载成功
                            view.onLoadDetails(it)
                        },
                        {
                            // 加载失败
                            view.onLoadFailed("加载详情内容失败")
                            Alog.e(TAG, "加载详情内容异常", it)
                        }
                )
    }
}