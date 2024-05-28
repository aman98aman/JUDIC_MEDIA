package com.exmpale.mimi.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.Activitys.CommentActivity
import com.exmpale.mimi.Activitys.VideoActivity
import com.exmpale.mimi.Activitys.ViewStoryActivity
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.StoryModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.StoryItemBinding

class StoryAdapter(private var items: List<UserModel>,
                   private val context: Context,
                   private val viewModel: MainViewModel
): RecyclerView.Adapter<StoryAdapter.MyViewHolder>() {


    inner class MyViewHolder(val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val itemList = items[position]





        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, VideoActivity::class.java)
            viewModel.uidText(context, itemList.id!!)
            context.startActivity(intent)

        }
        Glide
            .with(holder.itemView.context)
            .load(itemList.photo)
            .apply(RequestOptions().encodeQuality(5))
            .into(holder.binding.storyImageHolder);

        if (position % 3 == 0) {
            holder.binding.storyBg.setBackgroundResource(R.drawable.story_bg)
        } else if (position % 3 == 1) {
            holder.binding.storyBg.setBackgroundResource(R.drawable.story_bg1)
        } else if (position % 3 == 2) {
            holder.binding.storyBg.setBackgroundResource(R.drawable.story_bg2)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<UserModel>) {
        items = newList
        notifyDataSetChanged()
    }

}