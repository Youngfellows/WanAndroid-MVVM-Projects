package com.bbq.base.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.bbq.base.R

/**
 * 加载对话框
 *
 * @constructor
 * TODO
 *
 * @param context
 */
class LoadingDialog(context: Context) : Dialog(context, R.style.LoadingDialog) {

    private var loadingDialog: LoadingDialog? = null

    init {
        setContentView(R.layout.layout_loading_view)
        val imageView: ImageView = findViewById(R.id.iv_image)

        //旋转动画
        val animation: Animation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 2000
        animation.repeatCount = 10
        animation.fillAfter = true
        imageView.startAnimation(animation)
    }

    /**
     * 显示对话框
     * @param context
     */
    fun showDialog(context: Context) {
        if (context is Activity) {
            if (context.isFinishing) {
                return
            }
        }

        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(context)
        }
        loadingDialog?.show()
    }

    /**
     * 对话框消失
     */
    fun dismissDialog() {
        loadingDialog?.dismiss()
    }

}