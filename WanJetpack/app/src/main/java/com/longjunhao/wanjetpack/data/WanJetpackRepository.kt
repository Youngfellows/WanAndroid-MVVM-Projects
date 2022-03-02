package com.longjunhao.wanjetpack.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.longjunhao.wanjetpack.api.WanJetpackApi
import com.longjunhao.wanjetpack.data.home.*
import com.longjunhao.wanjetpack.data.project.ProjectCategory
import com.longjunhao.wanjetpack.data.project.ProjectPagingSource
import com.longjunhao.wanjetpack.data.user.CollectionPagingSource
import com.longjunhao.wanjetpack.data.user.User
import com.longjunhao.wanjetpack.data.wechat.WechatArticlePagingSource
import com.longjunhao.wanjetpack.data.wechat.WechatCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * .WanJetpackRepository
 *
 * @author Admitor
 * @date 2021/05/24
 */
@Singleton
class WanJetpackRepository @Inject constructor(
    private val api: WanJetpackApi,
    private val db: AppDatabase
) {
    companion object {
        private const val HOME_ARTICLE_PAGE_SIZE = 20
        private const val WENDA_PAGE_SIZE = 23
        private const val PROJECT_PAGE_SIZE = 18
        private const val WECHAT_PAGE_SIZE = 20
        private const val COLLECTION_ARTICLE_PAGE_SIZE = 20
        private const val SEARCH_ARTICLE_PAGE_SIZE = 20
    }

    /**
     * todo 由于RemoteMediator还有很多问题，暂时先注释掉，等发布正式版再来适配
     */
    //@OptIn(ExperimentalPagingApi::class)
    fun getHomeArticle(): Flow<PagingData<ApiArticle>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false,
                pageSize = HOME_ARTICLE_PAGE_SIZE
            ),
            pagingSourceFactory = { HomeArticlePagingSource(api) }
            //remoteMediator = ArticleRemoteMediator(api, db)
        ) /*{
            db.articleDao().getLocalArticle()
        }*/.flow
    }

    fun getWenda(): Flow<PagingData<ApiArticle>> {
        return Pager(
            config = PagingConfig(
                prefetchDistance = 5,
                enablePlaceholders = false,
                pageSize = WENDA_PAGE_SIZE
            ),
            pagingSourceFactory = { WendaPagingSource(api)}
        ).flow
    }

    suspend fun getProjectCategory(): ApiResponse<List<ProjectCategory>> {
        return api.getProjectCategory()
    }

    fun getProjectArticle(categoryId: Int): Flow<PagingData<ApiArticle>> {
        return Pager(
            config = PagingConfig(
                prefetchDistance = 5,
                enablePlaceholders = false,
                pageSize = PROJECT_PAGE_SIZE
            ),
            pagingSourceFactory = { ProjectPagingSource(api, categoryId) }
        ).flow
    }

    suspend fun getWechatName(): ApiResponse<List<WechatCategory>> {
        return api.getWechatName()
    }

    fun getWechatArticle(wechatId: Int): Flow<PagingData<ApiArticle>> {
        return Pager(
            config = PagingConfig(
                prefetchDistance = 5,
                enablePlaceholders = false,
                pageSize = WECHAT_PAGE_SIZE
            ),
            pagingSourceFactory = { WechatArticlePagingSource(api, wechatId) }
        ).flow
    }

    suspend fun login(username: String, password: String): ApiResponse<User> {
        Log.d("WanJetpackRepository", "login: ljh name=$username  pass=$password")
        return api.login(username, password)
    }

    suspend fun register(username: String, password: String, repassword: String): ApiResponse<User> {
        return api.register(username, password, repassword)
    }

    suspend fun logout(): ApiResponse<User>{
        return api.logout()
    }

    fun getCollectionArticle(): Flow<PagingData<ApiArticle>> {
        return Pager(
            config = PagingConfig(
                prefetchDistance = 5,
                enablePlaceholders = false,
                pageSize = COLLECTION_ARTICLE_PAGE_SIZE
            ),
            pagingSourceFactory = { CollectionPagingSource(api) }
        ).flow
    }

    suspend fun collect(id: Int): ApiResponse<ApiArticle> {
        return api.collect(id)
    }

    suspend fun unCollect(id: Int): ApiResponse<ApiArticle> {
        return api.unCollect(id)
    }

    suspend fun unCollect(id: Int, originId: Int): ApiResponse<ApiArticle> {
        return api.unCollect(id, originId)
    }

    fun getSearchArticle(keyword: String): Flow<PagingData<ApiArticle>> {
        return Pager(
            config = PagingConfig(
                prefetchDistance = 5,
                enablePlaceholders = false,
                pageSize = SEARCH_ARTICLE_PAGE_SIZE
            ),
            pagingSourceFactory = { SearchPagingSource(api, keyword) }
        ).flow
    }

    suspend fun getBanner(): ApiResponse<List<ApiBanner>> {
        return api.getBanner()
    }

}