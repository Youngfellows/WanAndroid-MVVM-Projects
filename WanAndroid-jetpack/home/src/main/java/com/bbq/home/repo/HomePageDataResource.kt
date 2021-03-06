package com.bbq.home.repo

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.bbq.home.bean.ArticleBean
import com.bbq.home.db.HomeDatabase
import com.bbq.net.model.ResultState
import kotlin.jvm.*

/**
 * 分页加载的数据源工厂
 * @property repo 网络请求
 * @property database 数据库存储
 */
class HomePageDataResource(val repo: HomeRepo, val database: HomeDatabase) :
    PagingSource<Int, ArticleBean>() {

    companion object {
        private const val TAG: String = "HomePageDataResource"
    }


    /**
     * @param params ：请求列表需要的参数
     * @return LoadResult ：列表数据请求结果，包含下一页要请求的key
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleBean> {
        //从start开始
        val currentPage = params.key ?: 0
        Log.d(TAG, "load:: currentPage:${currentPage}")
        //从repo中获取数据，repo不管是本地的还是网络的
        val response = repo.getArticleList(currentPage)
        Log.d(TAG, "load:: response=$response")
        when (response) {
            is ResultState.Success -> {
                Log.d(TAG, "load:: Success")
                //当前页码 小于 总页码 页面加1
                var nextPage = if (currentPage < response.data!!.pageCount) {
                    currentPage + 1
                } else {
                    //没有更多数据
                    null
                }
                //将数据保存到数据库中
                database.articleDao().clearArticleByPage(currentPage)
                database.articleDao().insertArticle(response.data!!.datas)
//                data ：返回的数据列表
//                prevKey ：上一页的key （传 null 表示没有上一页）
//                nextKey ：下一页的key （传 null 表示没有下一页）
                return LoadResult.Page(
                    data = response.data!!.datas,
                    prevKey = null,
                    nextKey = nextPage
                )
            }
            is ResultState.Error -> {
                Log.d(TAG, "load:: Error")
                //从数据库中取
//                return LoadResult.Error(response.exception)
                //异步获取
                val dbData = database.withTransaction {
                    if (currentPage == 0) {
                        //先读取置顶文章
                        val topData = database.articleDao().queryLocalArticle(-1)
                        val article = database.articleDao().queryLocalArticle(currentPage)
                        val tmpData = mutableListOf<ArticleBean>()
                        tmpData.addAll(topData)
                        tmpData.addAll(article)
                        tmpData
                    } else {
                        database.articleDao().queryLocalArticle(currentPage)
                    }
                }
                return if (dbData.isEmpty()) {
                    LoadResult.Error(response.exception)
                } else {
                    LoadResult.Page(
                        data = dbData,
                        prevKey = null,
                        nextKey = null
                    )
                }
            }
            else -> {
                Log.d(TAG, "load:: Error2")
                //理论上不会走到这里的
                return LoadResult.Error(Exception())
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleBean>): Int? {
        return 0
    }
}