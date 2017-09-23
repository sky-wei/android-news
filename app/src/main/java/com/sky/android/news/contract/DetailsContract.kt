package com.sky.android.news.contract

import com.sky.android.news.base.BasePresenter
import com.sky.android.news.base.BaseView
import com.sky.android.news.data.model.CategoryItemModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.LineItemModel

/**
 * Created by sky on 17-9-23.
 */
interface DetailsContract {

    interface View : BaseView {

        fun onLoadDetails(model: DetailsModel)

        fun onLoadFailed(msg: String)
    }

    interface Presenter : BasePresenter {

        fun setCategoryItem(item: LineItemModel)

        fun loadDetails()
    }
}