package com.hyunkwak.ynews.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.hyunkwak.ynews.database.ArticleDao
import com.hyunkwak.ynews.domain.DatabaseArticle
import com.hyunkwak.ynews.network.NewsApiService
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val dao: ArticleDao,
    private val apiService: NewsApiService
) {
    // db
    suspend fun insert(articles: List<DatabaseArticle>) = dao.insert(articles)
    suspend fun favouriteArticle(articleUrl: String, savedTime: Long) =
        dao.favourite(articleUrl, savedTime)

    suspend fun unfavouriteArticle(articleUrl: String) = dao.unfavouriteArticle(articleUrl)

    fun getLatestNews(query: String): LiveData<PagedList<DatabaseArticle>> =
        dao.getLatestNews(query).toLiveData(pageSize = 10)

    fun getFavourites() = dao.getFavourites().toLiveData(pageSize = 5)

    // internet
    suspend fun getQueriedNewsI(query: String) = apiService.getQuery(query)
    suspend fun getCountryNewsI(country: String) = apiService.getLatestCountryNews(country)
    suspend fun getCategoryNewsI(category: String) = apiService.getLatestCategoryNews(category)

}


