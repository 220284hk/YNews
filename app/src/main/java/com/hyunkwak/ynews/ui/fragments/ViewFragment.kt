package com.hyunkwak.ynews.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.hyunkwak.ynews.R
import com.hyunkwak.ynews.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_view.*
import java.lang.System.currentTimeMillis
import javax.inject.Inject

@AndroidEntryPoint
class ViewFragment : Fragment(R.layout.fragment_view) {

    private val args: ViewFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: MainActivityViewModel
    private var favourited = MutableLiveData<Boolean>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.articleKey
        favourited.value = article.savedTime != -1L

        favourited.observe(viewLifecycleOwner, Observer { favourited ->
            setFab(favourited)
        })

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        favourite_btn.setOnClickListener {
            if (favourited.value!!) {
                Toast.makeText(
                    requireContext(),
                    "Article removed from favourites!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.unfavouriteArticle(article.url)
            } else {
                Toast.makeText(requireContext(), "Article saved!", Toast.LENGTH_SHORT).show()
                viewModel.favouriteArticle(article.url, currentTimeMillis())
            }
            favourited.value = !favourited.value!!
        }

        share_btn.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain";
            share.putExtra(Intent.EXTRA_TEXT, article.url)
            share.putExtra(Intent.EXTRA_TITLE, article.title)
            startActivity(share)
        }

    }

    private fun setFab(favourited: Boolean) {
        if (favourited) {
            favourite_btn.fabOptionIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_favourite_filled)
        } else {
            favourite_btn.fabOptionIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_favourite_empty)
        }

    }

    override fun onResume() {
        super.onResume()
        activity?.bottomNavigationMenu?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.bottomNavigationMenu?.visibility = View.VISIBLE
    }
}