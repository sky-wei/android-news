/*
 * Copyright (c) 2020 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.android.news.ui.main.news

import android.os.Bundle
import android.view.View
import com.hi.dhl.binding.viewbind
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter
import com.sky.android.news.R
import com.sky.android.news.contract.CategoryContract
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.source.NewsDataRepository
import com.sky.android.news.data.source.NewsSourceFactory
import com.sky.android.news.databinding.FragmentCategoryBinding
import com.sky.android.news.presenter.CategoryPresenter
import com.sky.android.news.ui.base.NewsFragment

/**
 * Created by sky on 17-9-21.
 */
class CategoryFragment : NewsFragment(), CategoryContract.View {

    private val binding: FragmentCategoryBinding by viewbind()

    private lateinit var mCategoryPresenter: CategoryContract.Presenter

    override val layoutId: Int
        get() = R.layout.fragment_category

    override fun initView(view: View, args: Bundle?) {

        val repository = NewsDataRepository(NewsSourceFactory(requireContext()))
        mCategoryPresenter = CategoryPresenter(repository, this)

        // 加载类别
        mCategoryPresenter.loadCategory()
    }

    override fun onLoadCategory(model: CategoryModel) {

        val creator = FragmentPagerItems.with(context)

        model.items.forEach {

            val args = Bundle().apply {
                putSerializable("item", it)
            }

            // 添加类型
            creator.add(it.name, NetNewsFragment::class.java, args)
        }

        val adapter = FragmentStatePagerItemAdapter(
                childFragmentManager, creator.create())

        binding.viewpager.adapter = adapter
        binding.viewpagertab.setViewPager(binding.viewpager)
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }
}