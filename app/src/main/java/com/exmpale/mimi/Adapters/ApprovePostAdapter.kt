package com.exmpale.mimi.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.Models.ApprovePostModel
import com.exmpale.mimi.databinding.AllpostItemBinding
import com.exmpale.mimi.databinding.ApprovePostItemBinding


class ApprovePostAdapter(private val list:List<ApprovePostModel>): RecyclerView.Adapter<ApprovePostAdapter.MyVewHolder>() {

    inner class MyVewHolder(val binding: ApprovePostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVewHolder {
        val inflate = ApprovePostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyVewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {

        val itemList = list[position]

        holder.binding.approveBy.text = itemList.ApprovePostByName

        Glide
            .with(holder.itemView.context)
            .load(itemList.ApprovePost)
            .apply(RequestOptions().encodeQuality(5))
            .into(holder.binding.userProfileHolder)
    }
}