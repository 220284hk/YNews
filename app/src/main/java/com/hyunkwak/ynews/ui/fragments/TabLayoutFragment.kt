package com.hyunkwak.ynews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hyunkwak.ynews.R
import com.hyunkwak.ynews.ui.MainActivity
import com.hyunkwak.ynews.utils.*
import com.hyunkwak.ynews.utils.adapters.ViewPagerAdapter
import com.hyunkwak.ynews.viewmodels.TabLayoutViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tablayout.*

class TabLayoutFragment : Fragment(R.layout.fragment_tablayout) {

    private val fragList = listOf(WORLD_NEWS, US, KOREA, BUSINESS, ENTERTAINMENT, TECH)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_pager.adapter =
            ViewPagerAdapter(
                this,
                fragList,
                TabLayoutViewModel((activity as MainActivity).viewModel)
            )

        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = fragList[position]
        }.attach()

        (activity as MainActivity).title_tv.text = ""
        (activity as MainActivity).logo.visibility = View.VISIBLE

    }
}