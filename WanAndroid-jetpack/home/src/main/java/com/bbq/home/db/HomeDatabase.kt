package com.bbq.home.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bbq.home.bean.ArticleBean

/**
 * Room数据库
 */
@Database(
    entities = [ArticleBean::class],
    version = 3,
    exportSchema = false
)
abstract class HomeDatabase : RoomDatabase() {

    /**
     *  操作tab_article表的DAO
     * @return
     */
    abstract fun articleDao(): ArticleDao

    companion object {

        /**
         * 数据库名称
         */
        private const val DB_NAME = "app.db"

        /**
         * 单例
         */
        @Volatile
        private var instance: HomeDatabase? = null

        /**
         * 静态方法
         * @param context 上下文
         * @return
         */
        fun get(context: Context): HomeDatabase {
            return instance ?: Room.databaseBuilder(
                context, HomeDatabase::class.java,
                DB_NAME
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    instance = it
                }
        }
    }
}