package com.bbq.webview.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.bbq.base.base.BaseViewModel
import com.bbq.base.utils.getDrawable
import com.bbq.base.view.FabViewModel
import com.bbq.base.view.TitleViewModel
import com.bbq.webview.R
import com.bbq.webview.repo.WebRepo

/**
 * 携带文章页被观察数据的ViewModel
 * @property repo 网络API接口
 * @constructor
 * TODO
 *
 * @param application
 */
class WebViewModel(application: Application, val repo: WebRepo) : BaseViewModel(application) {

    /**
     * 被观察数据,是否滚动到顶部
     */
    val mScrollToTop = MutableLiveData(false)

    /**
     * 被观察数据,是否结束
     */
    val mIsFinish = MutableLiveData(false)

    /**
     * 浮动按钮
     */
    val mFabVM = FabViewModel(
        onClick = {
            mScrollToTop.value = true
        }
    )

    /**
     * 标题栏被观察数据实体
     */
    val mTitleVM = TitleViewModel(
        leftAction = {
            mIsFinish.postValue(true)
        },
        title = "",
        rightAction = {

        }
    )

    /**
     * 设置收藏,取消收藏
     * @param collect 是否收藏
     */
    fun setCollectState(collect: Boolean) {
        mTitleVM.mRightDrawable.set(
            if (collect) R.drawable.collect_red.getDrawable()
            else R.drawable.sc_white_stroke_ico.getDrawable()
        )
    }

    /**
     * 设置文章标题
     * @param title
     */
    fun setTitle(title: String) {
        mTitleVM.mTitle.set(title)
    }

    /**
     * 收藏文章
     * @param id 文章ID
     * @return
     */
    suspend fun collect(id: Int?): Boolean {
        return repo.collect(id)
    }

    /**
     * 取消收藏文章
     * @param id 文章ID
     * @return
     */
    suspend fun unCollect(id: Int?): Boolean {
        return repo.unCollect(id)
    }
}