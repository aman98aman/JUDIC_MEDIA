package com.exmpale.mimi.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exmpale.mimi.Adapters.AllPostAdapter
import com.exmpale.mimi.Adapters.ExploreAdapter
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.databinding.FragmentExploreBinding
import com.google.firebase.auth.FirebaseAuth


class ExploreFragment : Fragment() {

    private var allPostList = ArrayList<PostModel>()
    private val binding by lazy { FragmentExploreBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ExploreAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.approveRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = ExploreAdapter(allPostList, viewModel)
        refreshWork()
        setUPPostForAll()
        searchViewInPost()


        return binding.root
    }

    fun refreshWork(){


        binding.swipeRefreshLayout.setOnRefreshListener {
            // Perform actions to refresh data, like fetching new data or updating the UI
            // When complete, call setRefreshing(false) to stop the refreshing animation
            viewModel.fetchPostsPhoto()

        }


    }


    private fun searchViewInPost() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredList(newText)
                return true

            }

        })

    }

    private fun filteredList(newText: String?) {
        val filteredList = ArrayList<PostModel>()
        for (post in allPostList) {
            if (post.postProfileUser!!.contains(newText.orEmpty(), ignoreCase = true) ||
                post.postCaption!!.contains(newText.orEmpty(), ignoreCase = true))
                filteredList.add(post)

        }
        adapter.newList(filteredList)
        adapter.notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUPPostForAll() {

        allPostList = ArrayList()
        adapter = ExploreAdapter(allPostList, viewModel)
        binding.approveRecycler.adapter = adapter
        binding.approveRecycler.setHasFixedSize(false)




        viewModel.fetchPostsPhoto()

        viewModel.postPhoto.observe(viewLifecycleOwner, Observer { templist ->
            allPostList.clear()
            allPostList.addAll(templist)
            adapter.updateList(templist)
            binding.swipeRefreshLayout.isRefreshing = false


        })
    }
}