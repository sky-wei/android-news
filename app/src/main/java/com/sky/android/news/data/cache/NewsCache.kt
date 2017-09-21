package com.sky.android.news.data.cache

import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.HeadLineModel
import rx.Observable

/**
 * Created by sky on 17-9-21.
 */
interface NewsCache {

    fun getCategory(): Observable<CategoryModel>

    fun saveCategory(model: CategoryModel)

    fun getHeadLine(path: String): Observable<HeadLineModel>

    fun saveHeadLine(path: String, model: HeadLineModel)
}