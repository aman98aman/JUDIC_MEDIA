package com.exmpale.mimi.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.exmpale.mimi.Activitys.SearchActivity
import com.exmpale.mimi.Activitys.VideoActivity
import com.exmpale.mimi.Adapters.PostAdapter
import com.exmpale.mimi.Adapters.StoryAdapter
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.Models.StoryModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var storyList = ArrayList<UserModel>()
    private var postList = ArrayList<PostModel>()
    lateinit var adapter: PostAdapter
    lateinit var adapter2: StoryAdapter
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

         val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchUserData(userId)

        viewModel.userInfo.observe(viewLifecycleOwner, Observer { user ->


            Glide.with(this).load(user.photo).into(binding.imageView6)


        })

        binding.btSearch.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }

        setUPPostForAll()
        setUpFollowingData()

        refreshWork()
        viewModel.postSavedSuccess.observe(viewLifecycleOwner, Observer {isSuccess ->
            if (isSuccess) {

                Toast.makeText(requireContext(), "Successfully Bookmarked", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
            }
        })



        binding.imageView6.setOnClickListener {
            val intent = Intent(requireContext(), VideoActivity::class.java)
            viewModel.uidText(requireContext(), FirebaseAuth.getInstance().currentUser!!.uid)
            startActivity(intent)
        }

        return binding.root
    }

    fun refreshWork(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            // Perform actions to refresh data, like fetching new data or updating the UI
            // When complete, call setRefreshing(false) to stop the refreshing animation
            viewModel.fetchPosts()

            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            viewModel.fetchFollowingData(userId)
            viewModel.fetchUserData(userId)

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUPPostForAll() {

        postList = ArrayList()
        adapter = PostAdapter(postList, viewModel, requireContext())
        binding.PostRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.PostRecyclerview.setHasFixedSize(false)
        binding.PostRecyclerview.adapter = adapter

        viewModel.fetchPosts()




        viewModel.postList.observe(viewLifecycleOwner, Observer { templist ->
            postList.clear()
            postList.addAll(templist)
            adapter.updateList(templist)
            binding.swipeRefreshLayout.isRefreshing = false


        })
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUpFollowingData(){


        storyList = ArrayList()
        adapter2 = StoryAdapter(storyList, requireContext(), viewModel)
        binding.storyRecylerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.storyRecylerview.setHasFixedSize(false)
        binding.storyRecylerview.adapter = adapter2

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchFollowingData(userId)

        viewModel.followingList.observe(viewLifecycleOwner, Observer { following ->
            storyList.clear()
            storyList.addAll(following)
            adapter2.updateList(following)
            binding.swipeRefreshLayout.isRefreshing = false

        })

    }
}