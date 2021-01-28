package com.hyunkwak.ynews.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyunkwak.ynews.domain.DatabaseArticle
import com.hyunkwak.ynews.network.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KeyboardUtils {
    companion object {
        fun hideKeyboard(activity: Activity) {
            val imm: InputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view: View? = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

fun List<Article>.asDomainArticle(query: String): List<DatabaseArticle> {
    return this.map { article ->
        DatabaseArticle(
            article.author,
            article.content,
            article.description,
            article.publishedAt,
            article.source,
            article.title,
            article.url,
            article.urlToImage,
            article.saved,
            article.savedTime,
            query = query
        )
    }
}

fun String.formattedLength(length: Int): String {
    if (this.length <= length) return this
    var lastIndex = length
    while (!this[lastIndex].isLetter()) {
        lastIndex--
    }
    return "${this.substring(0, lastIndex + 1)}..."
}

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

fun String.smartTruncate(length: Int): String {
    val words = split(" ")
    var added = 0
    var hasMore = false
    val builder = StringBuilder()
    for (word in words) {
        if (builder.length > length) {
            hasMore = true
            break
        }
        builder.append(word)
        builder.append(" ")
        added += 1
    }

    PUNCTUATION.map {
        if (builder.endsWith(it)) {
            builder.replace(builder.length - it.length, builder.length, "")
        }
    }

    if (hasMore) {
        builder.append("...")
    }
    return builder.toString()
}

fun RecyclerView?.getCurrentPosition(): Int {
    if (this == null) return 0
    return (this.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
}

fun RecyclerView?.returnToPosition(position: Int, lifecycleScope: CoroutineScope) {
    if (this == null) return
    lifecycleScope.launch {
        delay(200)                      // won't work without this delay!
        scrollToPosition(position + 1)
    }
}