package com.hyunkwak.ynews.ui.fragments.tablayout

import android.os.Bundle
import android.view.View
import com.hyunkwak.ynews.ui.fragments.tablayout.common.TabFragment
import com.hyunkwak.ynews.utils.COUNTRY
import com.hyunkwak.ynews.utils.US
import com.hyunkwak.ynews.utils.adapters.DatabaseArticleAdapter
import com.hyunkwak.ynews.viewmodels.TabLayoutViewModel

class USTabFragment(
    tabLayoutViewModel: TabLayoutViewModel,
    adapterDatabase: DatabaseArticleAdapter
) : TabFragment(tabLayoutViewModel, adapterDatabase) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNews(US, COUNTRY)
        setSwipeRefreshListener(US, COUNTRY)
    }


}