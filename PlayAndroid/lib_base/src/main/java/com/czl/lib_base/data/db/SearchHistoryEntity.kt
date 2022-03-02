package com.czl.lib_base.data.db

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport
import java.sql.Date

/**
 * @author Alwyn
 * @Date 2020/11/9
 * @Description
 */
data class SearchHistoryEntity(
    val history: String,
    val searchDate: Int,
    var userEntity: UserEntity
) : LitePalSupport()