package com.exmpale.mimi.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.exmpale.mimi.Adapters.BookmarkAdapter
import com.exmpale.mimi.Adapters.PostAdapter
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.FragmentBookmarkBinding
import com.exmpale.mimi.databinding.FragmentUserBinding


class BookmarkFragment : Fragment() {

    private val binding by lazy { FragmentBookmarkBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel
    private var postList = ArrayList<PostModel>()
    lateinit var adapter: BookmarkAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setUPPostForAll()

        viewModel.deletePostBookmarkList.observe(viewLifecycleOwner, Observer {isSuccess ->
            if (isSuccess) {

                viewModel.fetchBookmarkedPosts()
                Toast.makeText(requireContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
            }
        })
        // Inflate the layout for this fragment
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUPPostForAll() {

        postList = ArrayList()
        adapter = BookmarkAdapter(postList, viewModel, requireContext())
        binding.PostRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.PostRecyclerview.setHasFixedSize(false)
        binding.PostRecyclerview.adapter = adapter

        viewModel.fetchBookmarkedPosts()




        viewModel.bookmarkedpostList.observe(viewLifecycleOwner, Observer { templist ->
            postList.clear()
            postList.addAll(templist)
            adapter.updateList(templist)

        })
    }

}