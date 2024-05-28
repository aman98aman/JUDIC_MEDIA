package com.exmpale.mimi.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.databinding.AllpostItemBinding
import com.exmpale.mimi.databinding.AllpostuserItemBinding

class ExploreAdapter(private var list:List<PostModel>, val viewModel: MainViewModel): RecyclerView.Adapter<ExploreAdapter.MyVewHolder>() {

    inner class MyVewHolder(val binding: AllpostItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun newList(filteredList: ArrayList<PostModel>) {
        this.list = filteredList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVewHolder {
        val inflate = AllpostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyVewHolder(inflate)
    }



    override fun getItemCount(): Int {
      return list.size
    }



    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {

        val itemList = list[position]


        Glide
            .with(holder.itemView.context)
            .load(itemList.postImage)
            .apply(RequestOptions().encodeQuality(5))
            .into(holder.binding.AllPostHolder)
    }




    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<PostModel>) {
        list = newList
        notifyDataSetChanged()
    }
}