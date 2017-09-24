package com.sky.android.news.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.TextView
import butterknife.BindView
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.base.VBaseFragment
import com.sky.android.news.contract.DetailsContract
import com.sky.android.news.data.model.ContentModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.source.NewsDataRepository
import com.sky.android.news.data.source.NewsSourceFactory
import com.sky.android.news.presenter.DetailsPresenter
import com.sky.android.news.ui.helper.VImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView

/**
 * Created by sky on 17-9-23.
 */
class DetailsFragment : VBaseFragment(), DetailsContract.View {

    @BindView(R2.id.tv_title)
    lateinit var tvTitle: TextView
    @BindView(R2.id.tv_body)
    lateinit var tvBody: HtmlTextView

    private lateinit var mContent: ContentModel
    private lateinit var mDetailsPresenter: DetailsContract.Presenter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun initView(view: View, args: Bundle?) {

        setHasOptionsMenu(true)

        val repository = NewsDataRepository(NewsSourceFactory(context))
        mDetailsPresenter = DetailsPresenter(context, repository, this)

        mDetailsPresenter.setCategoryItem(args!!.getSerializable("item") as LineItemModel)
        mDetailsPresenter.loadDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.activity_details, menu)
    }

    override fun onLoadDetails(model: DetailsModel) {

        mContent = model.models

        // 设置标题
        tvTitle.text = mContent.title
        tvBody.setHtml(mContent.body, VImageGetter(context, tvBody))
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }

    override fun showLoading() {
    }

    override fun cancelLoading() {
    }
}