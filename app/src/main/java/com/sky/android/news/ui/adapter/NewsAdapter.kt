package com.sky.android.news.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sky.android.common.adapter.SimpleRecyclerAdapter
import com.sky.android.common.base.BaseRecyclerAdapter
import com.sky.android.common.base.BaseRecyclerHolder
import com.sky.android.news.R
import com.sky.android.news.data.model.LineItemModel

/**
 * Created by sky on 17-9-22.
 */
class NewsAdapter(context: Context) : SimpleRecyclerAdapter<LineItemModel>(context) {

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup, viewType: Int): View {
        return layoutInflater.inflate(R.layout.item_line_list, viewGroup, false)
    }

    override fun onCreateViewHolder(view: View, viewType: Int): BaseRecyclerHolder<LineItemModel> {
        return NewsHolder(view, this)
    }

    class NewsHolder(itemView: View, adapter: BaseRecyclerAdapter<LineItemModel>)
        : BaseRecyclerHolder<LineItemModel>(itemView, adapter) {

        override fun onInitialize() {

        }

        override fun onBind(position: Int, viewType: Int) {
        }
    }
}