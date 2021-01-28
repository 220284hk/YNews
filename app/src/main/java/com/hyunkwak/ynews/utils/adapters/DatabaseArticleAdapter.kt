package com.hyunkwak.ynews.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hyunkwak.ynews.databinding.ListSingleItemBinding
import com.hyunkwak.ynews.domain.DatabaseArticle


class DatabaseArticleAdapter(val clickListener: DatabaseArticleListener) :
    PagedListAdapter<DatabaseArticle, DatabaseArticleAdapter.ViewHolder>(
        ArticleDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, clickListener)
        }
    }

    class ViewHolder private constructor(val binding: ListSingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: DatabaseArticle, clickListener: DatabaseArticleListener) {
            binding.article = article
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListSingleItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<DatabaseArticle>() {
    override fun areItemsTheSame(oldItem: DatabaseArticle, newItem: DatabaseArticle): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DatabaseArticle, newItem: DatabaseArticle): Boolean {
        return oldItem == newItem
    }
}

class DatabaseArticleListener(val clickListener: (article: DatabaseArticle) -> Unit) {
    fun onClick(article: DatabaseArticle) = clickListener(article)
}