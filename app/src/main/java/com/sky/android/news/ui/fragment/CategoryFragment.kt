/*
 * Copyright (c) 2017 The sky Authors.
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

package com.sky.android.news.ui.fragment

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.sky.android.news.R
import com.sky.android.news.base.VBaseFragment
import com.sky.android.news.contract.CategoryContract
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.source.NewsDataRepository
import com.sky.android.news.data.source.NewsSourceFactory
import com.sky.android.news.presenter.CategoryPresenter

/**
 * Created by sky on 17-9-21.
 */
class CategoryFragment : VBaseFragment(), CategoryContract.View {

    @BindView(R.id.viewpagertab)
    lateinit var smartTabLayout: SmartTabLayout
    @BindView(R.id.viewpager)
    lateinit var viewPager: ViewPager

    lateinit var mCategoryPresenter: CategoryContract.Presenter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun initView(view: View, args: Bundle?) {

        val repository = NewsDataRepository(NewsSourceFactory(context))
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
            creator.add(it.name, NewsFragment::class.java, args)
        }

        val adapter = FragmentPagerItemAdapter(
                childFragmentManager, creator.create())

        viewPager.adapter = adapter
        smartTabLayout.setViewPager(viewPager)
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }
}