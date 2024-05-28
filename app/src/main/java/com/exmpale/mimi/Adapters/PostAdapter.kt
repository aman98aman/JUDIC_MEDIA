package com.exmpale.mimi.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.Activitys.CommentActivity
import com.exmpale.mimi.Activitys.UserActivity
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ImagePostItemBinding
import com.exmpale.mimi.databinding.TextPostItemBinding
import com.exmpale.mimi.utils.Approved_Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date


class PostAdapter(private var list: List<PostModel>, val viewModel: MainViewModel, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_TEXT = 1
    private val VIEW_TYPE_IMAGE = 2

    override fun getItemViewType(position: Int): Int {
        return if (list[position].postType == true) {
            VIEW_TYPE_TEXT
        } else {
            VIEW_TYPE_IMAGE
        }
    }


    class TextViewHolder(val binding1: TextPostItemBinding, val viewModel: MainViewModel, val context: Context) :
        RecyclerView.ViewHolder(binding1.root) {
        lateinit var docId: String
        fun bind(itemList: PostModel) {


            var isLiked = false
            var likeCount: Int? = null




            Firebase.firestore.collection(Approved_Post)
                .whereEqualTo("postCaption", itemList.postCaption).get().addOnSuccessListener {


                for (doc in it.documents) {
                    docId = doc.id
                }
                var item: List<PostModel> = it.toObjects<PostModel>()

                likeCount = item[0].postLike
                binding1.countLike.text = likeCount.toString()


            }


            binding1.apply {


                favoriteBtn.setOnClickListener {


                    if (isLiked) {
                        likeCount = likeCount?.minus(1)
                        viewModel.likeCount(likeCount!!, docId)
                        viewModel.publishLikedUserInformation(itemList.uid.toString(),"Your post was Unliked by ${itemList.postProfileUser}")
                        isLiked = false
                        favoriteBtn.setImageResource(R.drawable.like)
                    } else {
                        likeCount = likeCount?.plus(1)
                        viewModel.likeCount(likeCount!!, docId)
                        viewModel.publishLikedUserInformation(itemList.uid.toString(),"Congratulation, your post was liked by ${itemList.postProfileUser}")
                        isLiked = true
                        favoriteBtn.setImageResource(R.drawable.favorite)
                    }

                    binding1.countLike.text = likeCount.toString()


                }
                val bundle = Bundle()
                bundle.putString("name", itemList.postProfileUser)
                bundle.putString("profile", itemList.postProfileImage)
                bundle.putString("uid", itemList.uid)
                bundle.putString("caption", itemList.postCaption)
                bundle.putString("postImage", itemList.postImage)
                bundle.putString("postLike", itemList.postLike.toString())


                commentBtn.setOnClickListener {

                    val intent = Intent(itemView.context, CommentActivity::class.java)
                    intent.putExtras(bundle)
                    itemView.context.startActivity(intent)
                }

                bookmarkBtn.setOnClickListener {

                    viewModel.saveFavouritePosts(itemList)
                    bookmarkBtn.setImageResource(R.drawable.bookmarkicon)


                }

//                share.setOnClickListener {
//                    val shareIntent = Intent(Intent.ACTION_SEND)
//                    shareIntent.type = "text/plain"
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, itemList.postCaption)
//                    context.startActivity(Intent.createChooser(shareIntent, "Share Caption"))
//                }


                userName.text = itemList.postProfileUser


                userName.setOnClickListener {

                    val intent = Intent(itemView.context, UserActivity::class.java)
                    val editor = context.getSharedPreferences("MyUid", AppCompatActivity.MODE_PRIVATE).edit()
                    editor.putString("uid", itemList.uid)
                    editor.clear()
                    editor.apply()
                    itemView.context.startActivity(intent)

                }

                Glide
                    .with(itemView.context)
                    .load(itemList.postProfileImage)
                    .into(userProfileHolder)


                userTextPostHolder.text = itemList.postCaption
                val boldUserName = itemList.postProfileUser
                val userComment = itemList.postCaption

                val formattedText = SpannableString("$boldUserName $userComment")
                if (boldUserName != null) {
                    formattedText.setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        boldUserName.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                if (boldUserName != null) {
                    formattedText.setSpan(
                        ForegroundColorSpan(Color.BLACK),
                        0,
                        boldUserName.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                userCommentTxt.text = formattedText
            }
        }
    }


    class ImageViewHolder(val binding2: ImagePostItemBinding, val viewModel: MainViewModel, val context: Context) :
        RecyclerView.ViewHolder(binding2.root) {

        lateinit var docId: String

        fun bind(itemList: PostModel) {


            var isLiked = false
            var likeCount: Int? = null




            Firebase.firestore.collection(Approved_Post)
                .whereEqualTo("postCaption", itemList.postCaption).get().addOnSuccessListener {


                for (doc in it.documents) {
                    docId = doc.id
                }

                var item: List<PostModel> = it.toObjects<PostModel>()

                likeCount = item[0].postLike
                binding2.countLike.text = likeCount.toString()


            }




            binding2.apply {



                favoriteBtn.setOnClickListener {


                    if (isLiked) {
                        likeCount = likeCount?.minus(1)
                        viewModel.likeCount(likeCount!!, docId)
                        isLiked = false
                        viewModel.publishLikedUserInformation(itemList.uid.toString(),"Your post was Unliked by ${itemList.postProfileUser}")
                        favoriteBtn.setImageResource(R.drawable.like)
                    } else {
                        likeCount = likeCount?.plus(1)
                        viewModel.likeCount(likeCount!!, docId)
                        isLiked = true
                        viewModel.publishLikedUserInformation(itemList.uid.toString(),"Congratulation, your post is liked by ${itemList.postProfileUser}")
                        favoriteBtn.setImageResource(R.drawable.favorite)
                    }

                    binding2.countLike.text = likeCount.toString()


                }


                val bundle = Bundle()
                bundle.putString("name", itemList.postProfileUser)
                bundle.putString("uid", itemList.uid)
                bundle.putString("profile", itemList.postProfileImage)
                bundle.putString("caption", itemList.postCaption)
                bundle.putString("postImage", itemList.postImage)
                bundle.putString("postLike", itemList.postLike.toString())

                bookmarkBtn.setOnClickListener {

                    viewModel.saveFavouritePosts(itemList)
                    bookmarkBtn.setImageResource(R.drawable.bookmarkicon)


                }

//                share.setOnClickListener {
//                    val shareIntent = Intent(Intent.ACTION_SEND)
//                    shareIntent.type = "text/plain"
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, itemList.postImage)
//                    context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
//                }

                commentBtn.setOnClickListener {

                    val intent = Intent(itemView.context, CommentActivity::class.java)
                    intent.putExtras(bundle)
                    itemView.context.startActivity(intent)
                }
                userName.text = itemList.postProfileUser

                userName.setOnClickListener {

                    val intent = Intent(itemView.context, UserActivity::class.java)
                    val editor = context.getSharedPreferences("MyUid", AppCompatActivity.MODE_PRIVATE).edit()
                    editor.putString("uid", itemList.uid)
                    editor.clear()
                    editor.apply()

                    itemView.context.startActivity(intent)

                }

                Glide
                    .with(itemView.context)
                    .load(itemList.postProfileImage)
                    .into(userProfileHolder)

                Glide
                    .with(itemView.context)
                    .load(itemList.postImage)
                    .into(userPostHolder)


                val boldUserName = itemList.postProfileUser
                val userComment = itemList.postCaption

                val formattedText = SpannableString("$boldUserName $userComment")
                if (boldUserName != null) {
                    formattedText.setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        boldUserName.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                if (boldUserName != null) {
                    formattedText.setSpan(
                        ForegroundColorSpan(Color.BLACK),
                        0,
                        boldUserName.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                userCommentTxt.text = formattedText
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_TEXT -> {
                val view =
                    TextPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TextViewHolder(view, viewModel, context)
            }

            VIEW_TYPE_IMAGE -> {
                val view =
                    ImagePostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ImageViewHolder(view, viewModel, context)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            VIEW_TYPE_TEXT -> {
                val textViewHolder = holder as TextViewHolder
                textViewHolder.bind(list[position])
            }

            VIEW_TYPE_IMAGE -> {
                val imageViewHolder = holder as ImageViewHolder
                imageViewHolder.bind(list[position]) // Pass image resource or URL here
            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<PostModel>) {
        list = newList
        notifyDataSetChanged()
    }
}