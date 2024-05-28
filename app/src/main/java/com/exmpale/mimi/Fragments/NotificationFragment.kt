package com.exmpale.mimi.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.exmpale.mimi.Adapters.NotificationAdapter
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.NotificationModel
import com.exmpale.mimi.databinding.FragmentNotificationBinding
import com.google.firebase.auth.FirebaseAuth


class NotificationFragment : Fragment() {

    private val binding by lazy { FragmentNotificationBinding.inflate(layoutInflater) }

    private var notificationList = ArrayList<NotificationModel>()
    //    lateinit var adapter: MainPostAllAdapter
    lateinit var adapter: NotificationAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        refreshWork()
        setUPPostForAll()
        return binding.root

    }

    fun refreshWork(){


        binding.swipeRefreshLayout.setOnRefreshListener {
            // Perform actions to refresh data, like fetching new data or updating the UI
            // When complete, call setRefreshing(false) to stop the refreshing animation
            viewModel.fetchLikedUserInformation(FirebaseAuth.getInstance().currentUser!!.uid)

        }


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUPPostForAll() {

        notificationList = ArrayList()
        adapter = NotificationAdapter(notificationList)
        binding.notificationRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.notificationRecycler.setHasFixedSize(false)
        binding.notificationRecycler.adapter = adapter

        viewModel.fetchLikedUserInformation(FirebaseAuth.getInstance().currentUser!!.uid)

        viewModel.fetchLikedUserInfo.observe(viewLifecycleOwner, Observer { templist ->
            notificationList.clear()
            notificationList.addAll(templist)
            adapter.updateList(templist)
            binding.swipeRefreshLayout.isRefreshing = false

        })
    }

}