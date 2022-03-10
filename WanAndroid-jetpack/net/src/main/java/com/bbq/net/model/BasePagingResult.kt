package com.bbq.net.model

/**
 * 分页的文章实体
 * @param T
 * @property curPage 当前页
 * @property offset
 * @property over
 * @property pageCount 总页数
 * @property size
 * @property total
 * @property datas 文章列表实体
 */
data class BasePagingResult<T>(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: T
)