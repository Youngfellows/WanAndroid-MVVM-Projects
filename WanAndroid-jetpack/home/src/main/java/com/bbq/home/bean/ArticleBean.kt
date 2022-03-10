package com.bbq.home.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bbq.home.db.TagTypeConverter

/**
 * 文章实体，对应tab_article数据库表
 * @property page
 * @property articleType
 * @property apkLink
 * @property audit
 * @property author
 * @property canEdit
 * @property chapterId
 * @property chapterName
 * @property collect
 * @property courseId
 * @property desc
 * @property descMd
 * @property envelopePic
 * @property fresh
 * @property host
 * @property id
 * @property link
 * @property niceDate
 * @property niceShareDate
 * @property origin
 * @property prefix
 * @property projectLink
 * @property publishTime
 * @property realSuperChapterId
 * @property selfVisible
 * @property shareDate
 * @property shareUser
 * @property superChapterId
 * @property superChapterName
 * @property tags
 * @property title
 * @property type
 * @property userId
 * @property visible
 * @property zan
 */
@Entity(tableName = "tab_article")
@TypeConverters(TagTypeConverter::class)
data class ArticleBean(
    var page: Int,
    var articleType: Int,
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    var collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    @PrimaryKey
    val id: Int,
    val link: String?,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String?,
    val superChapterId: Int?,
    val superChapterName: String?,
    var tags: MutableList<ArticleTag>?,
    val title: String,
    val type: Int?,
    val userId: Int?,
    val visible: Int?,
    val zan: Int?
)








