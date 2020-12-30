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

package com.sky.android.news.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.hi.dhl.binding.viewbind
import com.jaeger.library.StatusBarUtil
import com.sky.android.news.Constant
import com.sky.android.news.R
import com.sky.android.news.databinding.ActivityMainBinding
import com.sky.android.news.ui.base.NewsActivity
import com.sky.android.news.ui.about.AboutFragment
import com.sky.android.news.ui.main.news.CategoryFragment
import com.sky.android.news.ui.setting.SettingsFragment
import com.sky.android.news.ui.main.story.StoryListFragment
import com.sky.android.news.util.ActivityUtil

class MainActivity : NewsActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mKeyTime: Long = 0

    private val binding: ActivityMainBinding by viewbind()

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView(intent: Intent) {

        setSupportActionBar(binding.appBarMain.toolbar)
        StatusBarUtil.setColorForDrawerLayout(
                this, binding.drawerLayout, resources.getColor(R.color.transparent))

        val toggle = ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        switchFragment(CategoryFragment::class.java,
                buildDefaultArgs(Constant.Category.NEWS))
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        binding.drawerLayout.closeDrawer(GravityCompat.START)

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
                // 进入设置
                ActivityUtil.startCommonActivity(
                        context, R.string.setting, SettingsFragment::class.java.name)
            }
            R.id.nav_about -> {
                // 进入关于
                ActivityUtil.startCommonActivity(
                        context, R.string.about, AboutFragment::class.java.name)
            }
        }
        return true
    }

    private fun switchFragment(classes: Class<out androidx.fragment.app.Fragment>, args: Bundle) {

        val manager = supportFragmentManager
        val curFragment = manager.findFragmentById(R.id.fl_content)

        // ID
        val id = args.getInt("id", -1)

        if (curFragment != null
                && classes == curFragment.javaClass
                && id == curFragment.arguments?.getInt("id", -1)) {
            // 相同的，不需要处理
            return
        }

        val fragment = androidx.fragment.app.Fragment.instantiate(context, classes.name, args)
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
            binding.appBarMain.layoutAppbar.elevation = value
    }

    private fun setAppBarScrollFlags(scrollFlags: Int) {
        binding.appBarMain.toolbar.apply {
            val params = layoutParams as AppBarLayout.LayoutParams
            params.scrollFlags = scrollFlags
            layoutParams = params
        }
    }
}
