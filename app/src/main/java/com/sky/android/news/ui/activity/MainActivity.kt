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

package com.sky.android.news.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import butterknife.BindView
import com.jaeger.library.StatusBarUtil
import com.sky.android.news.Constant
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.ui.base.VBaseActivity
import com.sky.android.news.ui.fragment.AboutFragment
import com.sky.android.news.ui.fragment.CategoryFragment
import com.sky.android.news.ui.fragment.StoryListFragment
import com.sky.android.news.util.ActivityUtil

class MainActivity : VBaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    @BindView(R2.id.layout_appbar)
    lateinit var layoutAppBar: AppBarLayout
    @BindView(R2.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R2.id.drawer_layout)
    lateinit var drawer: DrawerLayout
    @BindView(R2.id.nav_view)
    lateinit var navigationView: NavigationView

    private var mKeyTime: Long = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(intent: Intent) {

        setSupportActionBar(toolbar)
        StatusBarUtil.setColorForDrawerLayout(
                this, drawer, resources.getColor(R.color.transparent))

        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        switchFragment(CategoryFragment::class.java,
                buildDefaultArgs(Constant.Category.NEWS))
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        drawer.closeDrawer(GravityCompat.START)

        when(item.itemId) {
            R.id.nav_news -> {
                // 切换到网易新闻
                setAppBarLayoutElevation(0f)
                setAppBarScrollFlags(
                        AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                        or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP)
                switchFragment(CategoryFragment::class.java,
                        buildDefaultArgs(Constant.Category.NEWS))
            }
            R.id.nav_zhihu -> {
                // 切换到知乎日报
                setAppBarLayoutElevation(15.0f)
                setAppBarScrollFlags(0)
                switchFragment(StoryListFragment::class.java,
                        buildDefaultArgs(Constant.Category.ZHI_HU))
            }
            R.id.nav_settings -> {

            }
            R.id.nav_about -> {
                ActivityUtil.startCommonActivity(
                        context, R.string.about, AboutFragment::class.java.name)
            }
        }
        return true
    }

    private fun switchFragment(classes: Class<out Fragment>, args: Bundle) {

        val manager = supportFragmentManager
        val curFragment = manager.findFragmentById(R.id.fl_content)

        // ID
        val id = args.getInt("id", -1)

        if (curFragment != null
                && classes == curFragment.javaClass
                && id == curFragment.arguments.getInt("id", -1)) {
            // 相同的，不需要处理
            return
        }

        val fragment = Fragment.instantiate(context, classes.name, args)
        supportFragmentManager.beginTransaction().replace(R.id.fl_content, fragment).commit()
    }

    private fun buildDefaultArgs(id: Int): Bundle {

        val args = Bundle()
        args.putInt("id", id)

        return args
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && System.currentTimeMillis() - mKeyTime > 2000) {
            mKeyTime = System.currentTimeMillis()
            Toast.makeText(this, R.string.exit_app, Toast.LENGTH_LONG).show()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun setAppBarLayoutElevation(value: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            layoutAppBar.elevation = value
    }

    private fun setAppBarScrollFlags(scrollFlags: Int) {
        val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = scrollFlags
        toolbar.layoutParams = params
    }
}
