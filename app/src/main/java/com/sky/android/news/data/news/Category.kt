package com.sky.android.news.data.news

import java.io.Serializable

/**
 * Created by sky on 17-9-21.
 */
data class Category(val items: List<CategoryItem>) : Serializable

data class CategoryItem(val name: String, val tid: String) : Serializable