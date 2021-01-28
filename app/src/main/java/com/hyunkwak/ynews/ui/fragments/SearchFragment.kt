package com.hyunkwak.ynews.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hyunkwak.ynews.R
import com.hyunkwak.ynews.ui.MainActivity
import com.hyunkwak.ynews.ui.fragments.SearchFragment.VIEW.*
import com.hyunkwak.ynews.utils.adapters.DatabaseArticleAdapter
import com.hyunkwak.ynews.utils.adapters.DatabaseArticleListener
import com.hyunkwak.ynews.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    enum class VIEW { DEFAULT, NEWS, NO_RESULT, LOADING }

    @Inject
    lateinit var viewModel: MainActivityViewModel
    private var search: Job? = null
    private var hideKeyboard: Job? = null
    private val articleAdapter: DatabaseArticleAdapter by lazy {
        DatabaseArticleAdapter(DatabaseArticleListener { article ->
            val bundle = Bundle().apply {
                putParcelable("articleKey", article)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_viewFragment,
                bundle
            )
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = articleAdapter
        show(DEFAULT)


        val searchListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(query: String?): Boolean {
                search?.cancel()
                search = MainScope().launch {
                    // waits 750 before launching query
                    delay(750)
                    query?.let {
                        if (query.toString().isNotEmpty()) {
                            show(LOADING)
                            // from internet
                            var result = viewModel.searchNews(query.toString())

                            viewModel.getLatestNews(query.toString())
                                .observe(viewLifecycleOwner, Observer {
                                    it?.let { articleAdapter.submitList(it) }
                                })

                            try {
                                val noResult = result.await().articles.isEmpty()
                                if (noResult) show(NO_RESULT) else show(NEWS)
                            } catch (e: Exception) {
                                Timber.e("There was an exception: $e")
                                Toast.makeText(
                                    requireContext(),
                                    "Sorry, there was an error with your request, please try again",
                                    Toast.LENGTH_SHORT
                                ).show()
                                show(DEFAULT)
                            }
                            // hides keyboard
                            hideKeyboard?.cancel()
                            hideKeyboard = viewModel.hideKeyboard(requireActivity())
                        } else show(DEFAULT)

                    }
                }

                return true
            }

        }
        searchView.setOnQueryTextListener(searchListener)

        (activity as MainActivity).title_tv.text = "GET THE LATEST"
        (activity as MainActivity).logo.visibility = View.INVISIBLE
    }

    private fun show(e: VIEW) {
        recyclerView?.visibility = View.INVISIBLE
        lottie_404?.visibility = View.INVISIBLE
        lottie_news?.visibility = View.INVISIBLE
        recyclerView?.hideShimmer()
        when (e) {
            NEWS -> recyclerView?.visibility = View.VISIBLE
            DEFAULT -> lottie_news?.visibility = View.VISIBLE
            NO_RESULT -> lottie_404?.visibility = View.VISIBLE
            LOADING -> {
                recyclerView?.visibility = View.VISIBLE
                recyclerView?.showShimmer()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        search?.cancel()
        hideKeyboard?.cancel()
    }
}