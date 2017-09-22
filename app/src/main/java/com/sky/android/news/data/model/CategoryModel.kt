package com.sky.android.news.data.model

import java.io.Serializable

/**
 * Created by sky on 17-9-21.
 */
data class CategoryModel(val items: List<CategoryItemModel>) : Serializable

data class CategoryItemModel(val name: String, val tid: String) : Serializable