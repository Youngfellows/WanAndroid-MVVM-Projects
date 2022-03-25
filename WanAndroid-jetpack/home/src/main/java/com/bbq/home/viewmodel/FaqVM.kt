package com.bbq.home.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bbq.base.R
import com.bbq.base.base.BaseViewModel
import com.bbq.base.rository.StateLiveData
import com.bbq.base.utils.getDrawable
import com.bbq.base.view.TitleViewModel
import com.bbq.home.bean.ArticleBean
import com.bbq.home.repo.HomeRepo
import com.bbq.net.model.BasePagingResult
import kotlinx.coroutines.launch

/**
 * 问答页携带被观察数据的ViewModel
 * @property homeRepo 网络API接口
 * @constructor
 * TODO
 *
 * @param application
 */
class FaqVM(application: Application, val homeRepo: HomeRepo) : BaseViewModel(application) {

    /**
     * 标题栏 ViewModel
     */
    val mTitleVM = TitleViewModel(
        leftDrawable = null
    )

    /**
     * 设置标题
     * @param title 标题
     */
    fun setTitle(title: String) {
        mTitleVM.mTitle.set(title)
    }

    /**
     * 被观察数据-是否结束
     */
    val mFinish = MutableLiveData(false)

    /**
     * 被观察数据-问答列表
     */
    val mFaqList = StateLiveData<BasePagingResult<List<ArticleBean>>>()

    /**
     * 被观察数据-收藏列表
     */
    val mCollectionList = StateLiveData<BasePagingResult<List<ArticleBean>>>()

    /**
     * 设置左边图标
     */
    fun setLeftIcon() {
        mTitleVM.leftDrawable = R.drawable.abc_vector_test.getDrawable()
        mTitleVM.leftAction = {
            mFinish.postValue(true)
        }
    }

    /**
     * 不设置左边图标
     */
    fun setLeftIconNull() {
        mTitleVM.leftDrawable = null
    }

    /**
     * 获取问答列表
     * @param position
     */
    fun getFaqList(position: Int) {
        viewModelScope.launch {
            homeRepo.getFaqList(position, mFaqList)
        }
    }

    /**
     * 获取收藏列表
     * @param position
     */
    fun getCollectList(position: Int) {
        viewModelScope.launch {
            homeRepo.getCollectionList(position, mCollectionList)
        }
    }

    /**
     * 收藏文章
     * @param id 文章ID
     * @return
     */
    suspend fun collect(id: Int?): Boolean {
        return homeRepo.collect(id)
    }

    /**
     * 取消收藏文章
     * @param id 文章ID
     * @return
     */
    suspend fun unCollect(id: Int?): Boolean {
        return homeRepo.unCollect(id)
    }


}