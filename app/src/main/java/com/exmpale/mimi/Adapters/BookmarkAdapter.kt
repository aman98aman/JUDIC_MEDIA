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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.Activitys.CommentActivity
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ImagePostBookmarkItemBinding
import com.exmpale.mimi.databinding.ImagePostItemBinding
import com.exmpale.mimi.databinding.TextPostBookmarkItemBinding
import com.exmpale.mimi.databinding.TextPostItemBinding
import com.exmpale.mimi.utils.Approved_Post
import com.exmpale.mimi.utils.User_Node
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase


class BookmarkAdapter(private var list: List<PostModel>, val viewModel: MainViewModel, val context: Context) :
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


    class TextViewHolder(val binding1: TextPostBookmarkItemBinding, val viewModel: MainViewModel, val context: Context) :
        RecyclerView.ViewHolder(binding1.root) {
        fun bind(itemList: PostModel) {



            binding1.apply {

                delete.setOnClickListener {
                    viewModel.deleteUserBookmark(itemList)
                }



                userName.text = itemList.postProfileUser
                Glide
                    .with(itemView.context)
                    .load(itemList.postProfileImage)
                    .into(userProfileHolder)


                userTextPostHolder.text = itemList.postCaption

            }
        }
    }


    class ImageViewHolder(val binding2: ImagePostBookmarkItemBinding, val viewModel: MainViewModel, val context: Context) :
        RecyclerView.ViewHolder(binding2.root) {


        fun bind(itemList: PostModel) {



            binding2.apply {



                delete.setOnClickListener {
                    viewModel.deleteUserBookmark(itemList)
                }

                userName.text = itemList.postProfileUser
                Glide
                    .with(itemView.context)
                    .load(itemList.postProfileImage)
                    .into(userProfileHolder)

                Glide
                    .with(itemView.context)
                    .load(itemList.postImage)
                    .into(userPostHolder)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_TEXT -> {
                val view =
                    TextPostBookmarkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TextViewHolder(view, viewModel, context)
            }

            VIEW_TYPE_IMAGE -> {
                val view =
                    ImagePostBookmarkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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