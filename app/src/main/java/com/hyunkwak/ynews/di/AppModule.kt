package com.hyunkwak.ynews.di

import android.content.Context
import androidx.room.Room
import com.hyunkwak.ynews.database.ArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(@ApplicationContext app: Context): ArticlesDatabase
            = Room.databaseBuilder(app, ArticlesDatabase::class.java, "articles_db.db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideArticleDao(db: ArticlesDatabase) = db.getArticleDao()

}