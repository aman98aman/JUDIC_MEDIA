package com.exmpale.mimi.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.Activitys.UserActivity
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ItemSearchBinding

class UserListAdapter(private val context: Context, private var userList: ArrayList<UserModel>) :
    RecyclerView.Adapter<UserListAdapter.UserVh>() {
    class UserVh(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVh {
        return UserVh(
            binding = ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun newList(filteredList: ArrayList<UserModel>) {
        this.userList = filteredList
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: UserVh, position: Int) {
        val model = userList[position]
        if (model.photo == "") {
            holder.binding.imageUser.setImageResource(R.drawable.avatar)
        } else {
            Glide
                .with(holder.itemView.context)
                .load(model.photo)
                .apply(RequestOptions().encodeQuality(5))
                .into(holder.binding.imageUser)
        }
        holder.binding.nameUser.text = model.name


        holder.itemView.setOnClickListener {
            val intent = Intent(context, UserActivity::class.java)
            val editor = context.getSharedPreferences("MyUid", AppCompatActivity.MODE_PRIVATE).edit()
            editor.putString("uid", model.id)
            editor.clear()
            editor.apply()
            context.startActivity(intent)
        }


    }


}




