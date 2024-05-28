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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exmpale.mimi.Adapters.AllPostAdapter
import com.exmpale.mimi.Adapters.AllTextAdapter
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.FragmentBookmarkBinding
import com.exmpale.mimi.databinding.FragmentTextBinding
import com.exmpale.mimi.utils.User_Node
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class AllTextFragment : Fragment() {

    private val binding by lazy { FragmentTextBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel
    private var allPostList = ArrayList<PostModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // Inflate the layout for this fragment
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchUserPostText(userId)


        var postCount = 0 // Initialize postCount

        Firebase.firestore.collection(User_Node)
            .document(Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val currentUser = documentSnapshot.toObject<UserModel>()
                postCount = currentUser?.posts ?: 0
            }


        viewModel.deletePostList.observe(viewLifecycleOwner, Observer {isSuccess ->
            if (isSuccess) {

                Toast.makeText(requireContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show()
                postCount -= 1
                // Update the post count in Firestore
                val userDocRef = Firebase.firestore.collection(User_Node)
                    .document(Firebase.auth.currentUser!!.uid)

                userDocRef.update("posts", postCount)
                    .addOnSuccessListener {
                        // Post count updated successfully
                    }
                    .addOnFailureListener { exception ->
                        // Handle failure to update post count
                    }
                val userId = FirebaseAuth.getInstance().currentUser!!.uid
                viewModel.fetchUserPostText(userId)

            } else {
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
            }
        })

        setUPImageForUserPost()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUPImageForUserPost() {

        allPostList = ArrayList()
        val adapter = AllTextAdapter(allPostList, requireContext(), viewModel)
        binding.allRecyclerview.adapter = adapter
        binding.allRecyclerview.setHasFixedSize(false)
        binding.allRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        viewModel.userPostTextList.observe(viewLifecycleOwner, Observer { posts ->
            allPostList.clear()
            allPostList.addAll(posts)
            adapter.updateList(posts)
        })
    }

}