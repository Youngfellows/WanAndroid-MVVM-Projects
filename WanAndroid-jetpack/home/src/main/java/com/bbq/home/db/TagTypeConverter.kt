package com.bbq.home.db

import android.util.Log
import androidx.room.TypeConverter
import com.bbq.home.bean.ArticleTag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction： List<Tag>的类型转换
 */
class TagTypeConverter {

    companion object {
        private const val TAG: String = "TagTypeConverter"
    }

    /**
     * 字符串转化为 List<ArticleTag>
     * @param value json字符串
     * @return
     */
    @TypeConverter
    fun stringToObject(value: String): List<ArticleTag> {
        Log.d(TAG, "stringToObject: ")
        val listType = object : TypeToken<List<ArticleTag>>() {
        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<ArticleTag>): String {
        Log.d(TAG, "objectToString:: ")
        return Gson().toJson(list)
    }
}