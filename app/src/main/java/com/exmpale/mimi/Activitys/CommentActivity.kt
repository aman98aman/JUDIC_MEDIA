package com.exmpale.mimi.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.exmpale.mimi.Adapters.AllPostAdapter
import com.exmpale.mimi.Adapters.CommentAdapter
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.CommentModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.Models.StoryModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ActivityCommentBinding
import com.exmpale.mimi.databinding.FragmentHomeBinding
import com.exmpale.mimi.utils.Approved_Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase

class CommentActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCommentBinding.inflate(layoutInflater) }
    private var commentList = ArrayList<CommentModel>()
    private lateinit var viewModel: MainViewModel
    lateinit var caption: String
    lateinit var username: String
    lateinit var userProfile: String
    lateinit var docId: String
    lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]




        binding.back.setOnClickListener {
            finish()
        }




        val bundle = intent.extras

        if (bundle != null) {

            caption = bundle.getString("caption").toString()
            username = bundle.getString("name").toString()
            userProfile = bundle.getString("profile").toString()
            uid = bundle.getString("uid").toString()

            binding.userName.text = username
            binding.userPostCaption.text = caption
            binding.countLike.text = bundle.getString("postLike")

            val postImage = bundle.getString("postImage")
            if (postImage == "") {
                binding.userPostHolder.visibility = View.GONE
            }


            Glide
                .with(this)
                .load(userProfile)
                .into(binding.userProfileHolder)

            Glide
                .with(this)
                .load(bundle.getString("postImage"))
                .into(binding.userPostHolder)

            Firebase.firestore.collection(Approved_Post)
                .whereEqualTo("postCaption", caption).get().addOnSuccessListener {


                    for (doc in it.documents) {
                        docId = doc.id
                        viewModel.fetchPostComment(doc.id)

                    }

                }




            setUpCommentRecyclerView()

            binding.send.setOnClickListener {
                addCommentToFirebase()
            }





            viewModel.updateCommentResult.observe(this) { isSuccess ->
                if (isSuccess) {
                    // Registration successful
                    val text = binding.commentText.text
                    text.clear()
                    Toast.makeText(this, "Comment Added", Toast.LENGTH_LONG).show()
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                    viewModel.fetchUserData(userId)

                    viewModel.userInfo.observe(this, Observer { user ->

                        viewModel.publishLikedUserInformation(uid,"A message was dropped by ${user.username}")

                    })

//                finish()
                } else {
                    // Registration failed
                    Toast.makeText(this, "Comment Failed", Toast.LENGTH_LONG).show()
                }
            }


        }
    }


    fun addCommentToFirebase() {


                viewModel.addCommentForPost(
                    docId,
                    binding.commentText.text.toString()
                )

    }

    fun setUpCommentRecyclerView() {

        commentList = ArrayList()
        val adapter = CommentAdapter(commentList)
        binding.comRecyclerView.adapter = adapter
        binding.comRecyclerView.setHasFixedSize(false)
        binding.comRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        viewModel.userPostCommentInfo.observe(this, Observer { list ->
            commentList.clear()
            commentList.addAll(list)
            adapter.updateList(list)
        })
    }

}
