package com.sky.android.news.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.view.*
import android.widget.TextView
import butterknife.BindView
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.base.VBaseFragment
import com.sky.android.news.contract.DetailsContract
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.source.NewsDataRepository
import com.sky.android.news.data.source.NewsSourceFactory
import com.sky.android.news.presenter.DetailsPresenter
import org.xml.sax.XMLReader

/**
 * Created by sky on 17-9-23.
 */
class DetailsFragment : VBaseFragment(), DetailsContract.View {

    @BindView(R2.id.tv_title)
    lateinit var tvTitle: TextView
    @BindView(R2.id.tv_body)
    lateinit var tvBody: TextView

    lateinit var mDetailsPresenter: DetailsContract.Presenter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun initView(view: View, args: Bundle?) {

        val repository = NewsDataRepository(NewsSourceFactory(context))
        mDetailsPresenter = DetailsPresenter(repository, this)

        mDetailsPresenter.setCategoryItem(args!!.getSerializable("item") as LineItemModel)
        mDetailsPresenter.loadDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onLoadDetails(model: DetailsModel) {

        val content = model.models

        tvTitle.text = content.title

        tvBody.text = Html.fromHtml(content.body, null, BodyHandler())
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }

    class BodyHandler : Html.TagHandler {

//        override fun getDrawable(source: String?): Drawable {
//
//
//        }

        override fun handleTag(opening: Boolean, tag: String?, output: Editable?, xmlReader: XMLReader?) {

//            println(">>>>>>>>>>>>>>>>> $opening >>>>  $tag >> $output")
        }
    }
}