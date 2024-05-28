package com.exmpale.mimi.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.Models.CommentModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.databinding.AllpostItemBinding
import com.exmpale.mimi.databinding.CommentViewBinding

class CommentAdapter(private var list:List<CommentModel>): RecyclerView.Adapter<CommentAdapter.MyVewHolder>() {


    inner class MyVewHolder(val binding: CommentViewBinding) : RecyclerView.ViewHolder(binding.root){
        val name = binding.tvName
        val image = binding.profilePhoto
        val commentText = binding.tvCommentText
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVewHolder {
        val inflate = CommentViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyVewHolder(inflate)
    }



    override fun getItemCount(): Int {
        return list.size
    }



    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {

        val itemList = list[position]


    holder.name.text = itemList.userName
        holder.commentText.text = itemList.commentText

        Glide
            .with(holder.itemView.context)
            .load(itemList.userProfile)
            .into(holder.image)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<CommentModel>) {
        list = newList
        notifyDataSetChanged()
    }
}