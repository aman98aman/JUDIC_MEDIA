package com.exmpale.mimi.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.Models.SearchModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ItemSearchBinding
import com.exmpale.mimi.databinding.SearchPostBinding


class SearchAdapter(private val list:List<UserModel>): RecyclerView.Adapter<SearchAdapter.MyVewHolder>() {

    inner class MyVewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVewHolder {
        val inflate = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyVewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {

        val itemList = list[position]
        if(itemList.photo == "") {
            holder.binding.imageUser.setImageResource(R.drawable.avatar)
        } else {
            Glide
                .with(holder.itemView.context)
                .load(itemList.photo)
                .apply(RequestOptions().encodeQuality(5))
                .into(holder.binding.imageUser)
        }
    }
}