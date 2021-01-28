package com.hyunkwak.ynews.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hyunkwak.ynews.domain.DatabaseArticle

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: List<DatabaseArticle>)

    // Favourite and Unfavourite Articles
    @Query("UPDATE article SET saved = \"true\", savedTime = :savedTime WHERE url = :articleUrl")
    suspend fun favourite(articleUrl: String, savedTime: Long)

    @Query("UPDATE article SET saved = \"false\" WHERE url = :articleUrl")
    suspend fun unfavouriteArticle(articleUrl: String)

    // favourites
    @Query("SELECT * FROM article WHERE saved = \"true\" ORDER BY savedTime DESC")
    fun getFavourites(): DataSource.Factory<Int, DatabaseArticle>

    // general
    @Query("SELECT * FROM article WHERE urlToImage IS NOT \"false\" AND `query` = :query AND author IS NOT NULL ORDER BY publishedAt DESC")
    fun getLatestNews(query: String): DataSource.Factory<Int, DatabaseArticle>
}