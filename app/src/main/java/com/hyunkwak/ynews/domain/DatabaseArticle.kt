package com.hyunkwak.ynews.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hyunkwak.ynews.network.Source
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "article")
data class DatabaseArticle(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    @PrimaryKey val url: String,
    val urlToImage: String?,
    val saved: Boolean = false,
    val savedTime: Long = -1,
    val query: String
) : Parcelable
