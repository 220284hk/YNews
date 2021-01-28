package com.hyunkwak.ynews.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hyunkwak.ynews.domain.DatabaseArticle

@Database(entities = [DatabaseArticle::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}