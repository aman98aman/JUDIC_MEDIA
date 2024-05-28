package com.exmpale.mimi.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.Models.CommentModel
import com.exmpale.mimi.Models.NotificationModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.databinding.AllpostItemBinding
import com.exmpale.mimi.databinding.CommentViewBinding
import com.exmpale.mimi.databinding.ItemfornotificationBinding

class NotificationAdapter(private var list:List<NotificationModel>): RecyclerView.Adapter<NotificationAdapter.MyVewHolder>() {


    inner class MyVewHolder(val binding: ItemfornotificationBinding) : RecyclerView.ViewHolder(binding.root){
        val name = binding.likedUser
        val image = binding.likedUserProfile
        val message = binding.likedText
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVewHolder {
        val inflate = ItemfornotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyVewHolder(inflate)
    }



    override fun getItemCount(): Int {
        return list.size
    }



    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {

        val itemList = list[position]


        holder.name.text = itemList.likedWhoUser
        holder.message.text = itemList.message

        Glide
            .with(holder.itemView.context)
            .load(itemList.likedWhoProfile)
            .into(holder.image)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<NotificationModel>) {
        list = newList
        notifyDataSetChanged()
    }
}