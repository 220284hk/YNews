package com.hyunkwak.ynews.utils.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hyunkwak.ynews.R
import com.hyunkwak.ynews.utils.formattedLength

@BindingAdapter("imageUrl")
fun test(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("setTitle")
fun setTitle(tv: TextView, title: String?) {
    title?.let {
        tv.text = title
    }
}

@BindingAdapter("setAuthor", "checkAuthor")
fun setAuthor(tv: TextView, author: String?, source: String?) {
    author?.let {
        if (author.isNotEmpty() && author != source) {
            tv.text = tv.resources.getString(R.string.author_tv_format, author.formattedLength(40))
        }
    }
}

@BindingAdapter("setSource")
fun setSource(tv: TextView, source: String?) {
    source?.let {
        tv.text = "Source: $source"
    }
}


