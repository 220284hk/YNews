package com.hyunkwak.ynews.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hyunkwak.ynews.R
import com.hyunkwak.ynews.ui.MainActivity
import com.hyunkwak.ynews.utils.adapters.DatabaseArticleAdapter
import com.hyunkwak.ynews.utils.adapters.DatabaseArticleListener
import com.hyunkwak.ynews.utils.getCurrentPosition
import com.hyunkwak.ynews.utils.returnToPosition
import com.hyunkwak.ynews.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favourite.*
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    @Inject
    lateinit var viewModel: MainActivityViewModel
    private var cursor = -1
    private val articleAdapter: DatabaseArticleAdapter by lazy {
        DatabaseArticleAdapter(DatabaseArticleListener { article ->
            val bundle = Bundle().apply {
                putParcelable("articleKey", article)
            }
            findNavController().navigate(
                R.id.action_favouriteFragment_to_viewFragment,
                bundle
            )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.getFavourites().observe(viewLifecycleOwner, Observer {
            it?.let { articleAdapter.submitList(it) }
        })

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    // Responsible for the call back and SnackBar notification
    private fun initialiseSwipeFeature() {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val databaseArticle = articleAdapter.currentList!![position]
                    if (databaseArticle != null) {
                        viewModel.unfavouriteArticle(databaseArticle.url)

                        Snackbar.make(recyclerView, "Deleted!", Snackbar.LENGTH_LONG).apply {
                            setAction("Undo") {
                                viewModel.favouriteArticle(
                                    databaseArticle.url,
                                    databaseArticle.savedTime
                                )
                                recyclerView.returnToPosition(position, lifecycleScope)
                            }
                            setActionTextColor(Color.WHITE)
                        }.show()
                    }

                    recyclerView.returnToPosition((position - 1).coerceAtLeast(0), lifecycleScope)
                }
            }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)

        (activity as MainActivity).title_tv.text = "YOUR SAVED NEWS"
        (activity as MainActivity).logo.visibility = View.INVISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (cursor != -1) recyclerView.returnToPosition(cursor, lifecycleScope)
        recyclerView.adapter = articleAdapter
        initialiseSwipeFeature()
    }

    override fun onPause() {
        super.onPause()
        cursor = recyclerView.getCurrentPosition() + 1
    }

}