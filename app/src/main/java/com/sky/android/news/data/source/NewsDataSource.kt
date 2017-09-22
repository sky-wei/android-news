package com.sky.android.news.data.source

import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.HeadLineModel
import rx.Observable

/**
 * Created by sky on 17-9-21.
 */

interface NewsDataSource {

    /**
     * 获取分类列表
     */
    fun getCategory(): Observable<CategoryModel>

    /**
     * 获取新闻列表
     */
    fun getHeadLine(tid: String,
                    start: Int, end: Int): Observable<HeadLineModel>

    /**
     * 获取详情信息
     */
    fun getDetails(docId: String): Observable<DetailsModel>
}
