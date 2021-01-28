package com.hyunkwak.ynews.utils.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hyunkwak.ynews.R
import com.hyunkwak.ynews.ui.fragments.TabLayoutFragment
import com.hyunkwak.ynews.ui.fragments.tablayout.*
import com.hyunkwak.ynews.viewmodels.TabLayoutViewModel

class ViewPagerAdapter(
    private var tabLayoutFragment: TabLayoutFragment,
    private var fragList: List<String>,
    private var tabLayoutViewModel: TabLayoutViewModel
) : FragmentStateAdapter(tabLayoutFragment) {

    // caching this leads to undesirable results.
    private val adapterDatabase: DatabaseArticleAdapter
        get() =
            DatabaseArticleAdapter(DatabaseArticleListener { article ->
                val bundle = Bundle().apply {
                    putParcelable("articleKey", article)
                }
                findNavController(tabLayoutFragment).navigate(
                    R.id.action_tabLayoutFragment_to_viewFragment,
                    bundle
                )
            })

    override fun getItemCount(): Int {
        return fragList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WorldTabFragment(tabLayoutViewModel, adapterDatabase)
            1 -> USTabFragment(tabLayoutViewModel, adapterDatabase)
            2 -> KRTabFragment(tabLayoutViewModel, adapterDatabase)
            3 -> BusinessTabFragment(tabLayoutViewModel, adapterDatabase)
            4 -> EntertainmentTabFragment(tabLayoutViewModel, adapterDatabase)
            5 -> TechTabFragment(tabLayoutViewModel, adapterDatabase)
            else -> throw Exception()
        }
    }

}
