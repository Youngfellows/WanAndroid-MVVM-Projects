package com.bbq.home.repo

import android.util.Log
import com.bbq.base.rository.BaseRepository
import com.bbq.base.rository.StateLiveData
import com.bbq.home.api.HomeApi
import com.bbq.home.bean.ArticleBean
import com.bbq.home.bean.ArticleTag
import com.bbq.home.bean.BannerBean
import com.bbq.home.bean.HotKeyBean
import com.bbq.net.model.BasePagingResult
import com.bbq.net.model.BaseResult
import com.bbq.net.model.ResultState

/**
 * 主页的Repository
 * @property homeApi
 */
class HomeRepo(private val homeApi: HomeApi) : BaseRepository() {

    /**
     * 获取热词列表数据
     * @return
     */
    suspend fun getHotKey(): ResultState<MutableList<HotKeyBean>> {
        return callRequest(call = { handleResponse(homeApi.getHotKey()) })
    }

    /**
     * 获取轮播图列表数据
     * @return
     */
    suspend fun getBanners(): ResultState<MutableList<BannerBean>> {
        return callRequest { handleResponse(homeApi.getBanner()) }
    }

    /**
     * 获取分页文章列表
     * @param page 当前页
     * @return
     */
    suspend fun getArticleList(page: Int): ResultState<BasePagingResult<MutableList<ArticleBean>>> {
        var ex: java.lang.Exception? = null
        //如果page==0的话，不仅要获取文章还有获取置顶的
        return if (page == 0) {
            Log.d(TAG, "getArticleList:: page:${page}")
            var topResult: BaseResult<MutableList<ArticleBean>>? = null
            try {
                topResult = homeApi.articleTop()
                Log.d(TAG, "getArticleList:: 1, page:${page}")
            } catch (e: Exception) {
                e.printStackTrace()
                ex = e
                Log.e(TAG, "getArticleList: ${e.message}")
            }
            topResult?.data?.forEach {
                it.tags?.add(ArticleTag("置顶"))
                it.page = -1
            }
            Log.d(TAG, "getArticleList:: 2, topResult:${topResult}")

            var articleResult: BaseResult<BasePagingResult<MutableList<ArticleBean>>>? = null
            try {
                articleResult = homeApi.getHomeList(page)
            } catch (e: Exception) {
                e.printStackTrace()
                ex = e
                Log.e(TAG, "getArticleList:: xx,${e.message}")
            }

            Log.d(TAG, "getArticleList:: 3, articleResult:${articleResult}")
            articleResult?.data?.datas?.forEach {
                it.page = page
            }
            articleResult?.data?.datas?.addAll(0, topResult?.data!!)
            Log.d(TAG, "getArticleList:: 4, articleResult:${articleResult}")
            /*if (articleResult != null) {
                callRequest { handleResponse(articleResult!!) }
            } else {
                callRequest { throw ex!! }
            }*/
            articleResult?.let {
                callRequest { handleResponse(articleResult!!) }
            } ?: let { callRequest { throw ex!! } }

        } else {
            var articleResult: BaseResult<BasePagingResult<MutableList<ArticleBean>>>? = null
            try {
                articleResult = homeApi.getHomeList(page)
            } catch (e: Exception) {
                ex = e
                Log.d(TAG, "getArticleList:: 5,${e.message}")
            }
            articleResult?.data?.datas?.forEach {
                it.page = page
            }

            articleResult?.let { callRequest { handleResponse(articleResult) } }
                ?: callRequest { throw ex!! }
        }
    }

    /**
     * 搜索
     * @param page
     * @param key
     * @return
     */
    suspend fun searchArticles(
        page: Int,
        key: String
    ): ResultState<BasePagingResult<MutableList<ArticleBean>>> {
        return callRequest { handleResponse(homeApi.search(page, key)) }
    }

    /**
     * 收藏文章
     * @param id 文章ID
     * @return
     */
    suspend fun collect(id: Int?): Boolean {
        val result = callRequest { handleResponse(homeApi.collect(id)) }
        return result is ResultState.Success
    }

    /**
     * 取消收藏文章
     * @param id 文章ID
     * @return
     */
    suspend fun unCollect(id: Int?): Boolean {
        val result = callRequest { handleResponse(homeApi.unCollect(id)) }
        return result is ResultState.Success
    }


    /**
     * 问答列表
     * @param position
     * @param data
     */
    suspend fun getFaqList(
        position: Int,
        data: StateLiveData<BasePagingResult<List<ArticleBean>>>
    ) {
        executeRequest({ homeApi.wendaList(position) }, data)
    }

    /**
     * 自己收藏文章列表
     * @param position
     * @param data
     */
    suspend fun getCollectionList(
        position: Int,
        data: StateLiveData<BasePagingResult<List<ArticleBean>>>
    ) {
        executeRequest({ homeApi.lgCollectList(position) }, data)
    }

}