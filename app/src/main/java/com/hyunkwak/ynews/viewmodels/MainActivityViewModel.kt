package com.hyunkwak.ynews.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.hyunkwak.ynews.domain.DatabaseArticle

import com.hyunkwak.ynews.repository.NewsRepository
import com.hyunkwak.ynews.utils.KeyboardUtils
import com.hyunkwak.ynews.utils.asDomainArticle
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel(
    val newsRepository: NewsRepository
): ViewModel() {

    // common
    fun favouriteArticle(articleUrl: String, savedTime: Long) = viewModelScope.launch {
        newsRepository.favouriteArticle(articleUrl, savedTime)
    }
    fun insert(articles: List<DatabaseArticle>)  = viewModelScope.launch {
        newsRepository.insert(articles)
    }
    fun unfavouriteArticle(articleUrl: String) = viewModelScope.launch {
        newsRepository.unfavouriteArticle(articleUrl)
    }

    // FavouriteFragment
    fun getFavourites(): LiveData<PagedList<DatabaseArticle>> =
        newsRepository.getFavourites()

    // Search Fragment
    fun searchNews(query: String) = viewModelScope.async {
        val news = newsRepository.getQueriedNewsI(query)
        insert(news.articles.asDomainArticle(query))
        news
    }

    fun getLatestNews(query: String): LiveData<PagedList<DatabaseArticle>> = newsRepository.getLatestNews(query)

    fun hideKeyboard(activity: FragmentActivity) = viewModelScope.launch {
        delay(1500)
        KeyboardUtils.hideKeyboard(activity)
    }


}