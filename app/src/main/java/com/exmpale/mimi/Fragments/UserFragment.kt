package com.exmpale.mimi.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.exmpale.mimi.Activitys.EditProfileActivity
import com.exmpale.mimi.Activitys.SignUpActivity
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.R
import com.exmpale.mimi.Utilities.MyPagerAdapter
import com.exmpale.mimi.databinding.FragmentNotificationBinding
import com.exmpale.mimi.databinding.FragmentUserBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth


class UserFragment : Fragment() {

    private val binding by lazy { FragmentUserBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        refreshWork()

        binding.apply {
           val adapter = MyPagerAdapter(requireActivity())
           viewPager.adapter = adapter

            editBtn.setOnClickListener {
                val intent = Intent(requireContext(), EditProfileActivity::class.java)
                startActivity(intent)
            }


            logoutBtn.setOnClickListener {
                val auth = FirebaseAuth.getInstance()
                if (auth.currentUser != null){
                    auth.signOut()
                    val intent = Intent(requireContext(), SignUpActivity::class.java)
                    startActivity(intent)
                }

            }


           TabLayoutMediator(tabLayout, viewPager) { tab, position ->
               when (position) {

                   0 -> tab.text = "All Photos"
                   1 -> tab.text = "All Text"
                   2 -> tab.text = "Bookmark"

               }
           }.attach()

       }

        return binding.root
    }


    fun refreshWork(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            // Perform actions to refresh data, like fetching new data or updating the UI
            // When complete, call setRefreshing(false) to stop the refreshing animation
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            viewModel.fetchUserData(userId)
            binding.swipeRefreshLayout.isRefreshing = false

        }
    }


    override fun onStart() {

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchUserData(userId)

        viewModel.userInfo.observe(this, Observer { user ->

            binding.name.text = user.name.toString()
            binding.userName.text = user.username.toString()
            binding.nameUsername.text = user.username.toString()
            binding.bio.text = user.bio.toString()
            Glide.with(this).load(user.photo).into(binding.storyImageHolder)
            binding.postCount.text = user.posts.toString()
            binding.followerCount.text = user.follower.toString()
            binding.followingCount.text = user.following.toString()

        })

        super.onStart()
    }



}