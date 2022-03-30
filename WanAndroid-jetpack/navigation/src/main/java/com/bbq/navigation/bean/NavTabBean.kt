package com.bbq.navigation.bean

/**
 * 左边导航数据实体
 * @property articles 右边文章列表
 * @property cid
 * @property name
 * @property isSelected 是否选中
 */
data class NavTabBean(
    val articles: List<ArticleBean>? = null,
    val cid: String = "",
    val name: String = "",
    var isSelected: Boolean = false
)