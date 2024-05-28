package com.exmpale.mimi.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.databinding.AllpostItemBinding
import com.exmpale.mimi.databinding.TextItemBinding
import com.exmpale.mimi.utils.User_Node
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class AllTextAdapter(private var list:List<PostModel>, val context : Context, val viewModel: MainViewModel): RecyclerView.Adapter<AllTextAdapter.MyVewHolder>() {


    inner class MyVewHolder(val binding: TextItemBinding) : RecyclerView.ViewHolder(binding.root){

        val textHolder = binding.itemTipsText
        val shareBtn = binding.shareItem
        val delete = binding.delete

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVewHolder {
        val inflate = TextItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyVewHolder(inflate)
    }



    override fun getItemCount(): Int {
      return list.size
    }



    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {

        val itemList = list[position]


        var postCount = 0 // Initialize postCount

        Firebase.firestore.collection(User_Node)
            .document(Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val currentUser = documentSnapshot.toObject<UserModel>()
                postCount = currentUser?.posts ?: 0
            }

        holder.delete.setOnClickListener {
            viewModel.deleteUserPost(itemList)
        }

        holder.textHolder.text = itemList.postCaption
        holder.shareBtn.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, itemList.postCaption)
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<PostModel>) {
        list = newList
        notifyDataSetChanged()
    }
}