package com.hyunkwak.ynews.di

import com.hyunkwak.ynews.database.ArticleDao
import com.hyunkwak.ynews.network.NewsApiService
import com.hyunkwak.ynews.repository.NewsRepository
import com.hyunkwak.ynews.viewmodels.MainActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @ActivityRetainedScoped
    @Provides
    fun provideNewsRepository(dao: ArticleDao, apiService: NewsApiService)
            = NewsRepository(dao, apiService)

    @ActivityRetainedScoped
    @Provides
    fun provideMainActivityViewModel(newsRepo: NewsRepository) =
        MainActivityViewModel(newsRepo)
}