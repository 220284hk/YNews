package com.hyunkwak.ynews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.hyunkwak.ynews.domain.DatabaseArticle
import com.hyunkwak.ynews.utils.asDomainArticle
import kotlinx.coroutines.launch

class TabLayoutViewModel(private val activityViewModel: MainActivityViewModel) : ViewModel() {

    // Common DB
    fun getLatestNews(query: String): LiveData<PagedList<DatabaseArticle>> =
        activityViewModel.newsRepository.getLatestNews(query)

    fun refreshCountryNews(country: String) = viewModelScope.launch {
        val news = activityViewModel.newsRepository.getCountryNewsI(country)
        activityViewModel.insert(news.articles.asDomainArticle(query = country))
    }

    fun refreshCategoryNews(category: String) = viewModelScope.launch {
        val news = activityViewModel.newsRepository.getCategoryNewsI(category)
        activityViewModel.insert(news.articles.asDomainArticle(query = category))
    }
}

