package com.bbq.home.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bbq.base.base.BaseViewModel
import com.bbq.base.view.FabViewModel
import com.bbq.home.bean.ArticleBean
import com.bbq.home.bean.BannerBean
import com.bbq.home.bean.HotKeyBean
import com.bbq.home.db.HomeDatabase
import com.bbq.home.repo.HomePageDataResource
import com.bbq.home.repo.HomeRepo
import com.bbq.net.model.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * 首页的ViewModel
 * @property repo 网络接口
 * @property database 本地存储
 * @constructor
 * TODO
 *
 * @param app 上下文
 */
class HomeViewModel(app: Application, val repo: HomeRepo, val database: HomeDatabase) :
    BaseViewModel(app) {

    /**
     * 热词列表数据
     */
    val mHotKeyList = MutableLiveData<MutableList<HotKeyBean>>()

    /**
     * 获取热词列表数据
     */
    fun getHotKeys() {
        viewModelScope.launch(Dispatchers.IO) {
            val hotResult = repo.getHotKey()
            if (hotResult is ResultState.Success) {
                //更新被观察的热词列表数据
                mHotKeyList.postValue(hotResult.data!!)
            } else if (hotResult is ResultState.Error) {
                toast(hotResult.exception.msg)
            }
        }
    }

    /**
     * 轮播图列表数据
     */
    val mBannerList = MutableLiveData<MutableList<BannerBean>>()

    /**
     * 获取轮播图列表数据
     */
    fun getBannerList() {
        viewModelScope.launch(Dispatchers.IO) {
            val bannerResult = repo.getBanners()
            if (bannerResult is ResultState.Success) {
                mBannerList.postValue(bannerResult.data!!)
            } else if (bannerResult is ResultState.Error) {
                toast(bannerResult.exception.msg)
            }
        }
    }

    /**
     * 分页配置
     */
    val pageConfig = PagingConfig(
        pageSize = 15,//每页多少个条目；必填
//        prefetchDistance = 5,//预加载下一页的距离，滑动到倒数第几个条目就加载下一页，无缝加载（可选）默认值是pageSize
//        enablePlaceholders = false,//是否启用条目占位，当条目总数量确定的时候；列表一次性展示所有条目，但是没有数据；在adapter的onBindViewHolder里面绑定数据时候，是空数据，判断是空数据展示对应的占位item；可选，默认开启。
//        initialLoadSize = 10,//第一页加载条目数量 ，可选，默认值是 3*pageSize （有时候需要第一页多点数据可用）
//        maxSize = 50,//定义列表最大数量；可选，默认值是：Int.MAX_VALUE
    )

    /**
     * 获取文章列表数据
     * config ：分页配置
     * initialKey ： 初始页的页码 （可选）
     * remoteMediator ：远程数据解调员；网络请求数据后处理的类，可以做数据缓存
     * pagingSourceFactory：数据源工厂（每次刷新数据都会生产新的数据源）
     * @return
     */
    fun getArticles(): Flow<PagingData<ArticleBean>> {
        return Pager(pageConfig) {
            HomePageDataResource(repo, database)
        }.flow
            .cachedIn(viewModelScope)//cachedIn 绑定协程生命周期
    }

    /**
     * 悬浮按钮点击数据
     */
    var mFabClick = MutableLiveData(false)
    var mFabVM = FabViewModel(
        onClick = {
            mFabClick.value = true
        }
    )
    var mFabVisible = ObservableField(false)

    /**
     * 收藏文章
     * @param id
     * @return
     */
    suspend fun collect(id: Int?): Boolean {
        return repo.collect(id)
    }

    /**
     * 取消收藏文章
     * @param id
     * @return
     */
    suspend fun unCollect(id: Int?): Boolean {
        return repo.unCollect(id)
    }
}