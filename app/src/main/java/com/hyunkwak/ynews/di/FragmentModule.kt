package com.hyunkwak.ynews.di

import com.hyunkwak.ynews.viewmodels.MainActivityViewModel
import com.hyunkwak.ynews.viewmodels.TabLayoutViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @FragmentScoped
    @Provides
    fun provideTabLayoutViewModel(activityViewModel: MainActivityViewModel) =
        TabLayoutViewModel(activityViewModel)

}