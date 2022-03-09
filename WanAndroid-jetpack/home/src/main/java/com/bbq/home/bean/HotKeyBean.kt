package com.bbq.home.bean


/**
 * 热词数据实体
 * @property id
 * @property link
 * @property name 热词名称
 * @property order
 * @property visible
 */
data class HotKeyBean(
    val id: String = "",
    val link: String = "",
    val name: String = "",
    val order: String = "",
    val visible: String = ""
)