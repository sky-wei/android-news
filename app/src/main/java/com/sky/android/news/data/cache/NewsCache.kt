package com.sky.android.news.data.cache

import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.HeadLineModel

/**
 * Created by sky on 17-9-21.
 */
interface NewsCache {

    /**
     * 获取分类列表
     */
    fun getCategory(): CategoryModel?

    /**
     * 保存分类列表
     */
    fun saveCategory(model: CategoryModel)

    /**
     * 获取新闻列表信息
     */
    fun getHeadLine(tid: String,
                    start: Int, end: Int): HeadLineModel?

    /**
     * 保存新闻列表信息
     */
    fun saveHeadLine(tid: String,
                     start: Int, end: Int, model: HeadLineModel)

    /**
     * 获取详情信息
     */
    fun getDetails(docId: String): DetailsModel?

    /**
     * 保存详情信息
     */
    fun saveDetails(docId: String, model: DetailsModel)
}