package com.sky.android.news.ui.diglog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.sky.android.news.R
import com.sky.android.news.R2

/**
 * Created by sky on 17-9-24.
 */
class LoadingDialog @JvmOverloads constructor(context: Context, private val mCancelCallback: CancelCallback? = null) : Dialog(context, R.style.CustomProgressDialog) {

    @BindView(R2.id.tv_tip)
    lateinit var tvTip: TextView

    private var cancelable = true

    init {
        setContentView(R.layout.dialog_loading)
        window!!.setGravity(Gravity.CENTER)

        ButterKnife.bind(this)
    }

    override fun setCancelable(flag: Boolean) {
        super.setCancelable(flag)
        cancelable = flag
    }

    fun setTipText(text: Int) {
        tvTip.setText(text)
    }

    fun setTipText(text: String) {
        tvTip.text = text
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (KeyEvent.KEYCODE_BACK == keyCode && cancelable) {

            // 关闭
            dismiss()
            mCancelCallback?.cancel()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }


    interface CancelCallback {

        fun cancel()
    }
}