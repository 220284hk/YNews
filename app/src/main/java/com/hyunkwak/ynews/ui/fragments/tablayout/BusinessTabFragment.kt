package com.hyunkwak.ynews.ui.fragments.tablayout

import android.os.Bundle
import android.view.View
import com.hyunkwak.ynews.ui.fragments.tablayout.common.TabFragment
import com.hyunkwak.ynews.utils.BUSINESS
import com.hyunkwak.ynews.utils.adapters.DatabaseArticleAdapter
import com.hyunkwak.ynews.viewmodels.TabLayoutViewModel

class BusinessTabFragment(
    tabLayoutViewModel: TabLayoutViewModel,
    adapterDatabase: DatabaseArticleAdapter
) : TabFragment(tabLayoutViewModel, adapterDatabase) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNews(BUSINESS)
        setSwipeRefreshListener(BUSINESS)
    }


}