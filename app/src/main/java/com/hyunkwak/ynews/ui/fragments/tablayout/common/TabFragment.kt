package com.hyunkwak.ynews.ui.fragments.tablayout.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hyunkwak.ynews.R
import com.hyunkwak.ynews.utils.CATEGORY
import com.hyunkwak.ynews.utils.COUNTRY
import com.hyunkwak.ynews.utils.adapters.DatabaseArticleAdapter
import com.hyunkwak.ynews.utils.getCurrentPosition
import com.hyunkwak.ynews.utils.returnToPosition
import com.hyunkwak.ynews.viewmodels.TabLayoutViewModel
import kotlinx.android.synthetic.main.fragment_tab_view_holder.*

open class TabFragment(
    var tabLayoutViewModel: TabLayoutViewModel,
    var adapterDatabase: DatabaseArticleAdapter
) : Fragment(R.layout.fragment_tab_view_holder) {

    private var position = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapterDatabase
    }

    override fun onPause() {
        super.onPause()
        position = recyclerView.getCurrentPosition()
    }

    override fun onResume() {
        super.onResume()
        if (position > 0) recyclerView.returnToPosition(position, lifecycleScope)
    }

    protected fun getNews(type: String, typeCode: Int = CATEGORY) {
        tabLayoutViewModel.getLatestNews(type).observe(viewLifecycleOwner, {
            it?.let { adapterDatabase.submitList(it) }

            if (it.isEmpty() && typeCode == CATEGORY) tabLayoutViewModel.refreshCategoryNews(type)
            if (it.isEmpty() && typeCode == COUNTRY) tabLayoutViewModel.refreshCountryNews(type)
        })
    }

    protected fun setSwipeRefreshListener(type: String, typeCode: Int = CATEGORY) {
        swipeRefresh.setOnRefreshListener {
            if (typeCode == COUNTRY) tabLayoutViewModel.refreshCountryNews(type)
            if (typeCode == CATEGORY) tabLayoutViewModel.refreshCategoryNews(type)
            swipeRefresh.isRefreshing = false
        }
    }
}