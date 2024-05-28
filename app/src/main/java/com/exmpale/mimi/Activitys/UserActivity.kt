package com.exmpale.mimi.Activitys

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.exmpale.mimi.Adapters.UserAllPostAdapter
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.databinding.ActivityUserBinding
import com.exmpale.mimi.utils.Follow
import com.exmpale.mimi.utils.User_Node
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class UserActivity : AppCompatActivity() {

    val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel
    private var postList = ArrayList<PostModel>()
    lateinit var adapter: UserAllPostAdapter
    var isFollow = false
    var following: Int? = null
    var follower : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        binding.backId.setOnClickListener {
            finish()
        }


        val editor = getSharedPreferences("MyUid", MODE_PRIVATE)
        var uid = editor.getString("uid", null)

//        Toast.makeText(this, uid, Toast.LENGTH_LONG).show()

        if (uid != null) {
            viewModel.fetchUserData(uid)
        }
        if (uid != null) {
            viewModel.fetchSelectedUserPosts(uid)
        }
        setUPPostForAll()


        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Follow)
            .whereEqualTo("id", uid).get().addOnSuccessListener {

                if (it.documents.size == 0) {
                    isFollow = false
                } else {
                    isFollow = true
                    binding.followText.text = "UnFollow"
                }
            }


        // Determine isFollow variable...

// Fetching the current user's following count
        Firebase.firestore.collection(User_Node)
            .document(Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val currentUser = documentSnapshot.toObject<UserModel>()

                var following = currentUser?.following ?: 0

                // Fetching the other user's follower count
                Firebase.firestore.collection(User_Node)
                    .document(uid!!)
                    .get()
                    .addOnSuccessListener { otherUserDocument ->
                        val otherUser = otherUserDocument.toObject<UserModel>()

                        var follower = otherUser?.follower ?: 0

                        // Set the initial follow/unfollow text based on isFollow
                        binding.followText.text = if (isFollow) "Unfollow" else "Follow"

                        binding.followBtn.setOnClickListener {
                            if (isFollow) {
                                // Unfollow logic here...
                                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Follow)
                                    .whereEqualTo("id", uid)
                                    .get()
                                    .addOnSuccessListener { querySnapshot ->
                                        if (!querySnapshot.isEmpty) {
                                            val documentId = querySnapshot.documents[0].id
                                            Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Follow)
                                                .document(documentId)
                                                .delete()
                                                .addOnSuccessListener {
                                                    isFollow = false
                                                    binding.followText.text = "Follow"
                                                    following -= 1
                                                    viewModel.followingCount(following)
                                                    // Update the follower count for the other user
                                                    viewModel.followerCount(uid, follower - 1)
                                                }
                                        }
                                    }
                            } else {
                                // Follow logic here...
                                Firebase.firestore.collection(User_Node)
                                    .whereEqualTo("id", editor.getString("uid", null))
                                    .get()
                                    .addOnSuccessListener { querySnapshot ->
                                        if (!querySnapshot.isEmpty) {
                                            val documentSnapshot = querySnapshot.documents[0]
                                            val userData = documentSnapshot.data
                                            if (userData != null) {
                                                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Follow)
                                                    .document()
                                                    .set(userData)
                                                    .addOnSuccessListener {
                                                        Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show()
                                                        val userId = FirebaseAuth.getInstance().currentUser!!.uid
                                                        viewModel.fetchUserData(userId)

                                                        viewModel.userInfo.observe(this, Observer { user ->

                                                            viewModel.publishLikedUserInformation(uid,"You got another follower. His name ${user.username}")

                                                        })
                                                        isFollow = true
                                                        binding.followText.text = "Unfollow"
                                                        following += 1
                                                        viewModel.followingCount(following)
                                                        viewModel.followerCount(uid, follower + 1)
                                                    }
                                                    .addOnFailureListener { exception ->
                                                        // Handle failure to write data
                                                        Toast.makeText(this, "Failed: ${exception.message}", Toast.LENGTH_LONG).show()
                                                    }
                                            }
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        // Handle failure to fetch data
                                        Toast.makeText(this, "Fetch Failed: ${exception.message}", Toast.LENGTH_LONG).show()
                                    }
                            }
                        }
                    }
            }


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUPPostForAll() {

        postList = ArrayList()
        adapter = UserAllPostAdapter(postList, viewModel, this)
        binding.allPostRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.allPostRV.setHasFixedSize(false)
        binding.allPostRV.adapter = adapter

        viewModel.userselectedpostList.observe(this, Observer { templist ->
            postList.clear()
            postList.addAll(templist)
            adapter.updateList(templist)

        })
    }


    override fun onStart() {


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