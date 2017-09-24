package com.sky.android.news.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import butterknife.BindView
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.base.VBaseActivity
import com.sky.android.news.ui.fragment.DetailsFragment

/**
 * Created by sky on 17-9-23.
 */
class DetailsActivity : VBaseActivity() {

    @BindView(R2.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun getLayoutId(): Int {
        return R.layout.app_bar_frame
    }

    override fun initView(intent: Intent) {

        // 设置ActionBar
        setSupportActionBar(toolbar, "返回", true)

        val args = Bundle().apply {
            putSerializable("item", intent.getSerializableExtra("item"))
        }

        val fragmentManager = supportFragmentManager
        val fragment = Fragment.instantiate(this, DetailsFragment::class.java.name, args)
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}