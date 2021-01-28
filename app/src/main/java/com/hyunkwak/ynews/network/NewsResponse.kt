package com.hyunkwak.ynews.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class NewsResponse(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int?
)

@Parcelize
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String,
    val urlToImage: String?,
    val saved: Boolean = false,
    val savedTime: Long = -1
) : Parcelable


@Parcelize
data class Source(
    val id: String?,
    val name: String?
) : Parcelable