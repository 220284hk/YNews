package com.hyunkwak.ynews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hyunkwak.ynews.R
import com.hyunkwak.ynews.repository.NewsRepository
import com.hyunkwak.ynews.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var newsRepository: NewsRepository

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyNews)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationMenu.setupWithNavController(myNavHostFragment.findNavController())
    }
}