package com.example.instagramapp.home_screen.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.instagramapp.R
import com.example.instagramapp.app.utils.toPosts
import com.example.instagramapp.databinding.HomeFragmentBinding
import com.example.instagramapp.home_screen.presentation.adapters.ItemOnClickListener
import com.example.instagramapp.home_screen.presentation.adapters.PostsAdapter
import com.parse.ParseUser
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), ItemOnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private val binding: HomeFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        HomeFragmentBinding.inflate(layoutInflater)
    }
    private val adapter: PostsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PostsAdapter(this)
    }
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postRv.adapter = adapter
        viewModel.getPostList()

        binding.swiperefresh.setOnRefreshListener(this)
        viewVisibility(status = true)
        viewModel.postList.observe(viewLifecycleOwner) { it ->
            if (it.isSuccessful) {
                viewVisibility(status = false)
                adapter.postList = it.body()!!.results.map { it.toPosts() }
            } else {
                viewVisibility(status = false)
                Toast.makeText(requireContext(), it.message().toString(), Toast.LENGTH_SHORT).show()
            }

        }

        binding.swiperefresh.setColorSchemeResources(
            R.color.appColor,
            R.color.appColor,
            R.color.appColor,
            R.color.appColor)

    }


    private fun viewVisibility(status: Boolean) {
        if (status) {
            binding.progressDialog.visibility = View.VISIBLE
            binding.postRv.visibility = View.GONE
        } else {
            binding.progressDialog.visibility = View.GONE
            binding.postRv.visibility = View.VISIBLE
        }
    }

    override fun showMapApp(latitude: Double, longitude: Double) {
        val uri = Uri.parse("geo:${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)

    }

    override fun onRefresh() {
        viewModel.getPostList()
        binding.swiperefresh.isRefreshing = true
        binding.swiperefresh.postDelayed({ binding.swiperefresh.isRefreshing = false }, 500)
    }


}