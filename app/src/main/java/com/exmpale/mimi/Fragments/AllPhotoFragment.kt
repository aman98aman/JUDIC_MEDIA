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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exmpale.mimi.Adapters.AllPostAdapter
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.databinding.FragmentAllPhotoBinding
import com.exmpale.mimi.utils.User_Node
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class AllPhotoFragment : Fragment() {

    private val binding by lazy { FragmentAllPhotoBinding.inflate(layoutInflater) }

    private var allPostList = ArrayList<PostModel>()
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchUserPostData(userId)

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
                viewModel.fetchUserPostData(userId)

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
        val adapter = AllPostAdapter(allPostList, viewModel)
        binding.allRecyclerview.adapter = adapter
        binding.allRecyclerview.setHasFixedSize(false)
        binding.allRecyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        viewModel.userPostList.observe(viewLifecycleOwner, Observer { posts ->
            allPostList.clear()
            allPostList.addAll(posts)
            adapter.updateList(posts)
        })
    }


}