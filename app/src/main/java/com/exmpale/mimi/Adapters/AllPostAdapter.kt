package com.exmpale.mimi.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.databinding.AllpostItemBinding
import com.exmpale.mimi.databinding.AllpostuserItemBinding
import com.exmpale.mimi.utils.User_Node
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class AllPostAdapter(private var list:List<PostModel>, val viewModel: MainViewModel): RecyclerView.Adapter<AllPostAdapter.MyVewHolder>() {


    inner class MyVewHolder(val binding: AllpostuserItemBinding) : RecyclerView.ViewHolder(binding.root){
        val delete = binding.delete
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVewHolder {
        val inflate = AllpostuserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyVewHolder(inflate)
    }



    override fun getItemCount(): Int {
      return list.size
    }



    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {

        val itemList = list[position]



        holder.delete.setOnClickListener {
            viewModel.deleteUserPost(itemList)
        }


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